package edu.utcn.eeg.artifactdetection.preprocessing;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.utcn.eeg.artifactdetection.classifier.decisiontree.DecisionTreeClassifier;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.preprocessing.exception.FileReadingException;

public class Main {

	public static void main(String[] args) throws FileReadingException {
		SegmentsGenerator segmentsGenerator = new SegmentsGenerator();
		Map<Integer, List<Segment>> segments = segmentsGenerator.generateSegments();
		DecisionTreeClassifier decisionTreeClassifier = new DecisionTreeClassifier();
		List<AbstractSegment>abstractSegments = segments.get(78).stream().map(seg->(AbstractSegment)seg).collect(Collectors.toList());
		//decisionTreeClassifier.classifySegments(abstractSegments);
	}
}
