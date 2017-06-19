package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;
import edu.utcn.eeg.artifactdetection.builders.StructureBuilder;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class MultiChannelFileProcessorThread implements Callable<List<Segment>>{

    private final Logger logger = LoggerUtil.logger(MultiChannelFileProcessorThread.class);
    private final File             file;
    private final int              channelNr;
    private final StructureBuilder structureBuilder;

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
                segment.setValues(null);
                segments.add(segment);
            }
        }
        this.logger.warn("Finished creating segments for file  " + this.file);
        return segments;
    }

	@Override
	public List<Segment> call() throws Exception {
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
        }
        catch (FileNotFoundException e)
        {
            this.logger.error("A problem occured while reading file " + this.file.getName(), e);
        }
        this.logger.warn("Ended scan for file  " + this.file);
        return createSegments(data.stream()
                                  .mapToDouble(i -> i)
                                  .toArray());
    }
}
