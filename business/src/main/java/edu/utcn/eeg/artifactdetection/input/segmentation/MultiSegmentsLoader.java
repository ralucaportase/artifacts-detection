package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.File;
import java.util.List;
import edu.utcn.eeg.artifactdetection.builders.StructureBuilder;
import edu.utcn.eeg.artifactdetection.features.export.ArffGenerator;
import edu.utcn.eeg.artifactdetection.features.export.SegmentDeserializer;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.MultiChannelSegment;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class MultiSegmentsLoader
{
    public static void main(String[] args) throws Exception
    {
        ArffGenerator arffGenerator2 = new ArffGenerator(Configuration.MULTI_SEGMENTS_PATH + "/MultiSegments.arff", true);
        StructureBuilder structureBuilder = new StructureBuilder();
        MultiChannelFeaturesExtractor multiChannelFeaturesExtractor = new MultiChannelFeaturesExtractor();
        File folder = new File(Configuration.MULTI_SEGMENTS_PATH);
        for (final File fileEntry : folder.listFiles())
        {
            if (!fileEntry.isDirectory())
            {
                List<Segment> segments = SegmentDeserializer.deserializeList(fileEntry);
                MultiChannelSegment multiChannelSegment = new MultiChannelSegment(segments);
                multiChannelFeaturesExtractor.setMultiChannelFeatureForRegion(multiChannelSegment);
                multiChannelSegment.setFeatures(structureBuilder.computeMultiRegionFeaturesForMultiChannelSegment(segments));
                arffGenerator2.writeSegmentFeatures(multiChannelSegment.getFeatures(), multiChannelSegment.getCorrectType());

            }
        }

    }
}
