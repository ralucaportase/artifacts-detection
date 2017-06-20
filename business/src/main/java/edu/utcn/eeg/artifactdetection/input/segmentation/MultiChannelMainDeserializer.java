package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.File;
import java.util.List;
import edu.utcn.eeg.artifactdetection.features.export.SegmentDeserializer;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class MultiChannelMainDeserializer
{
    public static void main(String[] args)
    {
        List<Segment> segments = SegmentDeserializer.deserializeList(new File(Configuration.MULTI_SEGMENTS_PATH + "/Multikey_0_5120.ser"));
        System.out.println(segments);
    }
}
