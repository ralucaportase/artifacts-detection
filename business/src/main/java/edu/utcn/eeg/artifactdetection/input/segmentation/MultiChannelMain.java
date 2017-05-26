package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import edu.utcn.eeg.artifactdetection.builders.StructureBuilder;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.MultiChannelSegment;
import edu.utcn.eeg.artifactdetection.model.MultiRegionSegmentKey;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class MultiChannelMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(128);
        Map<Integer,Future<List<Segment>>> results = new HashMap<>();
		File folder = new File(Configuration.INPUT_FILES);
		for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	int channelNr = getChannelFromFile(fileEntry.getName());
	            results.put(new Integer(channelNr), executor.submit(new MultiChannelFileProcessorThread(fileEntry,channelNr)));
	        }
	    }
		Multimap<MultiRegionSegmentKey, Segment> multimap = ArrayListMultimap.create();
		StructureBuilder structureBuilder = new StructureBuilder();
		for (Entry<Integer, Future<List<Segment>>> result: results.entrySet()) {
			for (Segment segment : result.getValue().get()) {
				Feature[] features = structureBuilder.computeMultiRegionFeaturesForSegment(segment.getValues());
				segment.setFeatures(features);
				segment.setCorrectType(structureBuilder.computeCorrectType(segment.getInitIdx(), segment.getChannelNr()));
				MultiRegionSegmentKey key = segment.getMultiRegionSegmentKey();
				multimap.put(key, segment);
			}
		}
		List<MultiChannelSegment> multiChannelSegments = new ArrayList<>();
		for (MultiRegionSegmentKey key: multimap.keySet()) {
			Collection<Segment> segments = multimap.get(key);
			if(segments.size()>=120){
				ArrayList<Segment> segmentsArray = new ArrayList<>(segments);
				MultiChannelSegment multiChannelSegment = new MultiChannelSegment(segmentsArray);
				multiChannelSegment.setFeatures(structureBuilder.computeMultiRegionFeaturesForMultiChannelSegment(segmentsArray));
				multiChannelSegments.add(multiChannelSegment);
			}
		}
		
		executor.awaitTermination(1, TimeUnit.DAYS);
	}
	

	
	private static int getChannelFromFile(String file){
		String delims = "[._]+";
		String[] tokens = file.split(delims);
		for(String token:tokens){
			try{
				return Integer.parseInt(token);
			}catch(NumberFormatException e){
				continue;
			}
		}
		return -1;
	}

}
