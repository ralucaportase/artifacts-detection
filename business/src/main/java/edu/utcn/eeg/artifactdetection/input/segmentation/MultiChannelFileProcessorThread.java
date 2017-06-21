package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.log4j.Logger;
import com.google.common.collect.Lists;
import edu.utcn.eeg.artifactdetection.builders.StructureBuilder;
import edu.utcn.eeg.artifactdetection.features.export.SegmentDeserializer;
import edu.utcn.eeg.artifactdetection.features.export.SegmentSerializer;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.MultiRegionSegmentKey;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class MultiChannelFileProcessorThread implements Runnable
{

    private final Logger logger = LoggerUtil.logger(MultiChannelFileProcessorThread.class);
    private final File             file;
    private final int              channelNr;
    private final StructureBuilder structureBuilder;
    private static final Lock lock = new ReentrantReadWriteLock().writeLock();

    public MultiChannelFileProcessorThread(File file, int channelNr, StructureBuilder structureBuilder)
    {
        this.file = file;
        this.channelNr = channelNr;
        this.structureBuilder = structureBuilder;
    }

    private List<Segment> createSegments(double[] data)
    {
        this.logger.warn("Started creating segments for file  " + this.file);
        List<Segment> segments = new ArrayList<>();
        for (int i = 0; i < Configuration.WINDOW_SIZE; i += Configuration.STEP)
        {
            for (int j = i; j < data.length; j += Configuration.WINDOW_SIZE)
            {
                this.logger.warn("File " + this.channelNr + " Iteratie " + i + " Init " + j);
                double[] values = Arrays.copyOfRange(data, j, j + Configuration.WINDOW_SIZE);
                Segment segment = new Segment(values);
                segment.setInitIdx(j);
                segment.setIterIdx(i);
                segment.setChannelNr(this.channelNr);

                Feature[] features = this.structureBuilder.computeMultiRegionFeaturesForSegment(segment.getValues());
                segment.setFeatures(features);
                segment.setCorrectType(this.structureBuilder.computeCorrectType(segment.getInitIdx(), segment.getChannelNr()));
                this.logger.warn("Processing segment: " + segment + " Nr elemente " + segment.getValues().length + " Features " + Arrays.toString(segment.getFeatures()));
                segments.add(segment);
            }
        }
        this.logger.warn("Finished creating segments for file  " + this.file);
        return segments;
    }

	@Override
    public void run()
    {
        List<Double> data = new ArrayList<>();
        this.logger.warn("Started scan for file  " + this.file + " thread is " + Thread.currentThread()
                                                                                       .getId());
        int count = 0;
        try (Scanner scan = new Scanner(this.file))
        {
            while (scan.hasNextDouble())
            {
                count++;
                if (count <= Configuration.TRAIN_MAX_INDEX)
                {
                    data.add(scan.nextDouble());
                }
                else
                {
                    break;
                }
            }

            List<Segment> segments = createSegments(data.stream()
                                                        .mapToDouble(i -> i)
                                                        .toArray());
            for (Segment segment : segments)
            {
                MultiRegionSegmentKey key = segment.getMultiRegionSegmentKey();
                lock.lock();
                try
                {
                    String fileName = Configuration.MULTI_SEGMENTS_PATH + "/" + key.toString() + ".ser";
                    File file = new File(fileName);
                    if (!file.exists())
                    {
                        SegmentSerializer.serializeList(Lists.newArrayList(segment), file);
                    }
                    else
                    {
                        List<Segment> deserializedSegments = SegmentDeserializer.deserializeList(file);
                        deserializedSegments.add(segment);
                        SegmentSerializer.serializeList(deserializedSegments, file);
                    }
                }
                finally
                {
                    lock.unlock();
                }
            }
        }
        catch (FileNotFoundException e)
        {
            this.logger.error("A problem occured while reading file " + this.file.getName(), e);
        }
        this.logger.warn("Ended scan for file  " + this.file);

    }

}
