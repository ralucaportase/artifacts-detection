package edu.utcn.eeg.artifactdetection.view.provider;

import java.util.List;

import java.util.stream.Collectors;

import edu.utcn.eeg.artifactdetection.helpers.SegmentDeserializer;
import edu.utcn.eeg.artifactdetection.model.*;

public class SimpleChannelSegmentProvider {

	private static final String PATH = "E:\\Scoala\\licenta\\#0IMPLEMENTATION\\Data\\newDataSet\\elena-doua-canale-13-04\\Occular_Eval.ser";

	public List<Segment> provideSegments() {
		System.out.println("Start deserializing" + PATH);
		SegmentDeserializer segmentDeserializer = new SegmentDeserializer();
		SegmentRepository repo = segmentDeserializer.deserializeSegmentsFromFile(PATH);
		List<AbstractSegment> segmentsAbstract = repo.getSegments();
		List<Segment> segments = segmentsAbstract.stream().map(s -> (Segment) s).collect(Collectors.toList());
		System.out.println("Deserialized all");
		for (Segment s : segments) {
			System.out.println(s.getInitIdx());
		}
		return segments;
	}
}
