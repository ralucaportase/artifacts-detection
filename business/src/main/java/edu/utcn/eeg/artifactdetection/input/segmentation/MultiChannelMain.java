package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import edu.utcn.eeg.artifactdetection.builders.StructureBuilder;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.MultiChannelSegment;
import edu.utcn.eeg.artifactdetection.model.MultiRegionSegmentKey;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class MultiChannelMain {

    public static void main(String[] args) throws Exception
    {
        MultiChannelFeaturesExtractor multiChannelFeaturesExtractor = new MultiChannelFeaturesExtractor();
        LoggerUtil.configure();
        Logger logger = LoggerUtil.logger(MultiChannelMain.class);

        ExecutorService executor = Executors.newFixedThreadPool(8);
        Map<Integer,Future<List<Segment>>> results = new HashMap<>();
		File folder = new File(Configuration.INPUT_FILES);
        StructureBuilder structureBuilder = new StructureBuilder();
        Arrays.stream(folder.listFiles())
              .filter(fileEntry -> !fileEntry.isDirectory())
              .limit(2)
              .forEach(fileEntry ->
              {
                  int channelNr = getChannelFromFile(fileEntry.getName());
                  results.put(new Integer(channelNr), executor.submit(new MultiChannelFileProcessorThread(fileEntry, channelNr, structureBuilder)));
              });

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);

		Multimap<MultiRegionSegmentKey, Segment> multimap = ArrayListMultimap.create();

		for (Entry<Integer, Future<List<Segment>>> result: results.entrySet()) {
			for (Segment segment : result.getValue().get()) {
                logger.warn("Processing segment: " + segment + " Features " + segment.getFeatures());
                MultiRegionSegmentKey key = segment.getMultiRegionSegmentKey();
                multimap.put(key, segment);
            }
        }

        List<MultiChannelSegment> multiChannelSegments = new ArrayList<>();
        for (MultiRegionSegmentKey key : multimap.keySet())
        {
            Collection<Segment> segments = multimap.get(key);
            if (segments.size() >= 1)
            {
                ArrayList<Segment> segmentsArray = new ArrayList<>(segments);
                MultiChannelSegment multiChannelSegment = new MultiChannelSegment(segmentsArray);
                multiChannelFeaturesExtractor.setMultiChannelFeatureForAllSegments(multiChannelSegment);
                multiChannelSegment.setFeatures(structureBuilder.computeMultiRegionFeaturesForMultiChannelSegment(segmentsArray));
                multiChannelSegments.add(multiChannelSegment);
                logger.warn(multiChannelSegment);
            }
        }
    }

    private static int getChannelFromFile(String file)
    {
        String delims = "[._]+";
        String[] tokens = file.split(delims);
        for (String token : tokens)
        {
            try
            {
                return Integer.parseInt(token);
            }
            catch (NumberFormatException e)
            {
                continue;
            }
        }
        return -1;
    }

}
