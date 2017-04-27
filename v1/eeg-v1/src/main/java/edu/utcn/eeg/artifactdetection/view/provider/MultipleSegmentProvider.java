package edu.utcn.eeg.artifactdetection.view.provider;

import java.util.List;

import java.util.stream.Collectors;

import edu.utcn.eeg.artifactdetection.helpers.SegmentDeserializer;
import edu.utcn.eeg.artifactdetection.model.*;

public class MultipleSegmentProvider {

	private static final String PATH = "E:\\Scoala\\licenta\\#0IMPLEMENTATION\\Data\\newDataSet\\elena-doua-canale-13-04\\Occular_Multi_Eval.ser";

	private MultiChannelSegmentSelector selector;

	public MultipleSegmentProvider(MultiChannelSegmentSelector selector) {
		this.selector = selector;
	}

	public List<MultiChannelSegment> provideSegments() {
		SegmentDeserializer segmentDeserializer = new SegmentDeserializer();
		SegmentRepository repo = segmentDeserializer.deserializeSegmentsFromFile(PATH);
		List<AbstractSegment> segmentsAbstract = repo.getSegments();
		List<MultiChannelSegment> multiChannelSegments = segmentsAbstract.stream().map(s -> (MultiChannelSegment) s)
				.collect(Collectors.toList());
		for (MultiChannelSegment segm : multiChannelSegments) {
			System.out.println(segm.getInitIdx());
		}
		return multiChannelSegments;
	}

}
