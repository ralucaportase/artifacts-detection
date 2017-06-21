package edu.utcn.eeg.artifactdetection.preprocessing;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.MultiChannelSegment;
import edu.utcn.eeg.artifactdetection.model.MultiChannelSegmentSelector;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;
import edu.utcn.eeg.artifactdetection.preprocessing.exception.FileReadingException;
import edu.utcn.eeg.artifactdetection.utils.SegmentDeserializer;

public class SegmentsGenerator implements SegmentsGeneratorInterface{

	@Override
	public Map<Integer, List<Segment>> generateSegments() throws FileReadingException {
		SegmentRepository allTest = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.INPUT_SEGMENTS_FILENAME);		
		List<AbstractSegment> testSegm=allTest.getSegments();		
		List<Segment> testSegments = testSegm
			    .stream()
			    .map(e -> (Segment) e)
			    .collect(Collectors.toList());
		
		Map<Integer, List<Segment>> resultSegments =
					testSegments.stream().collect(Collectors.groupingBy(Segment::getChannelNr));
		
		for(Integer key:resultSegments.keySet()){
			List<Segment> segms = resultSegments.get(key);
			Collections.sort(segms);
			resultSegments.put(key, segms);		
		}
		
		return resultSegments;
	}

	@Override
	public List<MultiChannelSegment> generateMultisegments(String path, MultiChannelSegmentSelector selector)
			throws FileReadingException {
		// TODO Auto-generated method stub
		return null;
	}

}
