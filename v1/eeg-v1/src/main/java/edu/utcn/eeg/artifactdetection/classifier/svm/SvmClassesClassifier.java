package edu.utcn.eeg.artifactdetection.classifier.svm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.utcn.eeg.artifactdetection.helpers.FileReader;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class SvmClassesClassifier extends SvmBinaryClassifier {

	protected String outputClassesFilename = Configuration.PROJECT_PATH
			+ "/svm/svm_classesOutput.csv";
	protected String inputClassesTestFilename = Configuration.PROJECT_PATH
			+ "/svm/svmClassesExperiment.dat";
	protected String classesModelFilename = Configuration.PROJECT_PATH
			+ "/svm/artifactsModel.dat";

	public SvmClassesClassifier() {

	}

	public SvmClassesClassifier(String outputFilename, String inputTestFilename) {
		super(outputFilename, inputTestFilename);
	}

	public List<Segment> classifySegments(List<Segment> segments) {
		int j = 0;
		classify(segments);
		List<Segment> artifactsSegments = new ArrayList<Segment>();
		List<Double> classificationResults = FileReader.getInstance()
				.parseTxtFile(new File(outputBinaryFilename));
		for (int i = 0; i < classificationResults.size(); i++) {
			if (classificationResults.get(i) > 0)
				artifactsSegments.add(segments.get(i));
		}

		// call classifier again for artifacts
		classifyArtifacts(artifactsSegments);
		List<Double> artyfactsClassificationResults = FileReader.getInstance()
				.parseTxtFile(new File(outputClassesFilename));
		for (int i = 0; i < classificationResults.size(); i++) {
			if (classificationResults.get(i) < 0) {
				segments.get(i).setCorrectType(ResultType.BRAIN_SIGNAL);
			} else {
				if (artyfactsClassificationResults.get(j) < 0) {
					segments.get(i).setCorrectType(ResultType.MUSCLE);
				} else {
					segments.get(i).setCorrectType(ResultType.OCCULAR);
				}
				j++;
			}
		}

		return segments;
	}

	public void classifyArtifacts(List<Segment> segments) {
		exportSegments(segments, ResultType.MUSCLE, inputClassesTestFilename); 
		File testFile = new File(inputClassesTestFilename);
		File modelFile = new File(classesModelFilename);
		File predictionFile = new File(outputClassesFilename);
		callSvmClassify(testFile, modelFile, predictionFile);
	}

}
