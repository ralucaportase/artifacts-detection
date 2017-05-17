package edu.utcn.eeg.artifactdetection.classifier.svm;

import java.io.File;
import java.util.List;

import edu.utcn.eeg.artifactdetection.classifier.Classifier;
import edu.utcn.eeg.artifactdetection.helpers.FileReader;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.ResultType;

public class SvmClassifier implements Classifier {

	private String outputFilePath = "D:/DiplomaCode/artifacts-detection/svm/svm_TestCompare.csv";

	public SvmClassifier(String outputFilePath) {
		this.outputFilePath = outputFilePath;
	}

	public List<AbstractSegment> classifySegments(List<AbstractSegment> segments) {
		FileReader fileReader = new FileReader();
		List<Double> classificationResults = fileReader.parseTxtFile(new File(
				outputFilePath));
		for (int i = 0; i < classificationResults.size(); i++) {
			if (classificationResults.get(i) < 0) {
				segments.get(i).setCorrectType(ResultType.BRAIN_SIGNAL);
			}
			else {
				segments.get(i).setCorrectType(ResultType.MUSCLE);
			}
		}
		return null;
	}

}
