package edu.utcn.eeg.artifactdetection.view.provider;

import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

import edu.utcn.eeg.artifactdetection.helpers.SegmentDeserializer;
import edu.utcn.eeg.artifactdetection.model.*;
import edu.utcn.eeg.artifactdetection.preprocessing.SegmentsGenerator;
import edu.utcn.eeg.artifactdetection.preprocessing.SegmentsGeneratorInterface;
import edu.utcn.eeg.artifactdetection.preprocessing.exception.FileReadingException;

public class SimpleChannelSegmentProvider {

	private static final String PATH = "E:\\Scoala\\licenta\\#0IMPLEMENTATION\\Data\\newDataSet\\elena-doua-canale-13-04\\Occular_Eval.ser";

	private int nrChannel;

	public SimpleChannelSegmentProvider(int nrChannel) {
		this.nrChannel = nrChannel;
	}

	/**
	 * Bring the channels from the channel nrChannel
	 */
	public List<Segment> provideSegments(int channel) {
//		logger.info("Start deserializing" + PATH);
//		SegmentDeserializer segmentDeserializer = new SegmentDeserializer();
//		SegmentRepository repo = segmentDeserializer.deserializeSegmentsFromFile(PATH);
//		List<AbstractSegment> segmentsAbstract = repo.getSegments();
//		List<Segment> segments = segmentsAbstract.stream().map(s -> (Segment) s).collect(Collectors.toList());
//		logger.info("Deserialized all");
//		for (Segment s : segments) {
//			logger.info(s.getInitIdx());
//		}
		
		SegmentsGeneratorInterface gen=new SegmentsGenerator();
		Map<Integer, List<Segment>> map;
		try {
			map = gen.generateSegments();
			return map.get(new Integer(channel));
		} catch (FileReadingException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
