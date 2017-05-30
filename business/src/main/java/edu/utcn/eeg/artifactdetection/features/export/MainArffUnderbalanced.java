package edu.utcn.eeg.artifactdetection.features.export;

import java.io.IOException;

import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class MainArffUnderbalanced {

	public static void main(String[] args) throws IOException {
		SegmentRepository underbalanced1 = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/UndersampledForTrain.ser");
		SegmentRepository underbalanced2= SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/UndersampledSmoteForTrain.ser");
	
		ArffGenerator arffGenerator = new ArffGenerator(Configuration.RESULTS_PATH+"/Undersampled.arff");
		
		for (AbstractSegment segment : underbalanced1.getSegments()) {
			arffGenerator.writeSegmentFeatures(segment.getFeatures(), segment.getCorrectType());
		}
		
		ArffGenerator arffGenerator2 = new ArffGenerator(Configuration.RESULTS_PATH+"/UndersampledSmote.arff");
		for (AbstractSegment segment : underbalanced2.getSegments()) {
			arffGenerator2.writeSegmentFeatures(segment.getFeatures(), segment.getCorrectType());
		}
	}
}
