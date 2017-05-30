package edu.utcn.eeg.artifactdetection.preprocessing;

import edu.utcn.eeg.artifactdetection.preprocessing.exception.FileReadingException;

public class Main {

	public static void main(String[] args) throws FileReadingException {
		SegmentsGenerator segmentsGenerator = new SegmentsGenerator();
		segmentsGenerator.generateSegments();
	}
}
