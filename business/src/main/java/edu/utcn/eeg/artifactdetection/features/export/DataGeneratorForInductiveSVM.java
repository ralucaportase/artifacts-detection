package edu.utcn.eeg.artifactdetection.features.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.utcn.eeg.artifactdetection.features.FeatureExtractor;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.FeatureType;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;
import edu.utcn.eeg.artifactdetection.output.processing.ComputingOutputParameters;
import edu.utcn.eeg.artifactdetection.output.processing.SVMOutput;

/**
 * @author: RalucaPortase
 * 
 *          Class for generating data in the format used by SVM light
 */
public class DataGeneratorForInductiveSVM {
	private static final String LEARNING_FILENAME = Configuration.PROJECT_PATH
			+ "/svm/svmTest72.dat";

	private static List<Double> getFeaturesForSegment(Segment segment) {
		List<Double> outputFeatures = new ArrayList<Double>();

		outputFeatures.add(FeatureExtractor.getFeatureValue(
				FeatureType.STANDARD_DEVIATION, segment.getValues()));
		outputFeatures.add(FeatureExtractor.getFeatureValue(
				FeatureType.ALPHA_SPECTRUM, segment.getValues()));
		outputFeatures.add(FeatureExtractor.getFeatureValue(
				FeatureType.BETHA_LOW_SPECTRUM, segment.getValues()));
		outputFeatures.add(FeatureExtractor.getFeatureValue(
				FeatureType.BETHA_HIGH_SPECTRUM, segment.getValues()));
		outputFeatures.add(FeatureExtractor.getFeatureValue(
				FeatureType.DELTA_SPECTRUM, segment.getValues()));
		outputFeatures.add(FeatureExtractor.getFeatureValue(
				FeatureType.GAMMA_LOW_SPECTRUM, segment.getValues()));
		outputFeatures.add(FeatureExtractor.getFeatureValue(
				FeatureType.GAMMA_HIGH_SPECTRUM, segment.getValues()));
		outputFeatures.add(FeatureExtractor.getFeatureValue(
				FeatureType.THETA_SPECTRUM, segment.getValues()));

		return outputFeatures;
	}

	/*
	 * format used for learning file is <line> .=. <target> <feature>:<value>
	 * ... <feature>:<value> # <info> <target> .=. +1 | -1 | 0 | <float>
	 * <feature> .=. <integer> | "qid" <value> .=. <float> <info> .=. <string>
	 */
	public static String getContentOfLearningFile(List<Segment> segments) {
		List<Double> outputFeatures;
		String lineContent = "";
		boolean ok = true;
		int index, // index represents the feature
		noOfArtifacts = 0, noOfBrainSignals = 0;

		for (Segment segment : segments) {
			outputFeatures = getFeaturesForSegment(segment);
			// we use a binary classification when 1 represent
			// brain signal and -1 artifact
			if (segment.getCorrectType() == ResultType.BRAIN_SIGNAL) {
				// make sure that we have equal no of learning samples
				if (noOfBrainSignals <= noOfArtifacts) {
					lineContent += "\n1 ";
					// noOfBrainSignals++;
					ok = true;
				} else
					ok = false;
			} else {
				lineContent += "\n-1 ";
				noOfArtifacts++;
				ok = true;
				// System.out.println("Processing " + segment.getCorrectType());
			}
			index = 1;
			if (ok == true) {
				for (Double value : outputFeatures) {
					if (value != null) {
						lineContent += index + ":" + value + " ";
					}
					index++;
				}
			}
		}
		return lineContent;
	}

	private static void writeToFile(List<Segment> segments) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			String content = getContentOfLearningFile(segments);
			fw = new FileWriter(LEARNING_FILENAME);
			bw = new BufferedWriter(fw);
			bw.write(content);
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	public void createSvmInputFiles() {
		SegmentDeserializer deserializer = new SegmentDeserializer();
		List<Segment> segments = new ArrayList<Segment>();

		SegmentRepository repository = deserializer
				.deserializeSegmentsFromFile("D:/DiplomaCode/artifacts-detection/results/Muscle72.ser");
		segments.addAll(repository.getSegments());
		repository = deserializer
				.deserializeSegmentsFromFile("D:/DiplomaCode/artifacts-detection/results/Occular72.ser");
		segments.addAll(repository.getSegments());
		repository = deserializer
				.deserializeSegmentsFromFile("D:/DiplomaCode/artifacts-detection/results/Clean72.ser");
		segments.addAll(repository.getSegments());

		writeToFile(segments);
		System.out.println("done writing file!");
	}

	public static void computeOutputSVMParameters() {
		SVMOutput svmOutput = new SVMOutput();
		List<Double> inputClassification = svmOutput.parseOutputFile(new File(
				"D:/DiplomaCode/artifacts-detection/svm/svmTest74.csv"));
		List<Double> outputClassification = svmOutput.parseOutputFile(new File(
				"D:/DiplomaCode/artifacts-detection/svm/predictionTest16.dat"));

		int falseNegative = svmOutput.computeFalseNegativeRate(
				inputClassification, outputClassification);
		int falsePositive = svmOutput.computeFalsePositiveRate(
				inputClassification, outputClassification);
		int trueNegative = svmOutput.computeTrueNegativeRate(
				inputClassification, outputClassification);
		int truePositive = svmOutput.computeTruePositiveRate(
				inputClassification, outputClassification);

		ComputingOutputParameters realOutput = new ComputingOutputParameters();

		System.out.println("False Negative: " + falseNegative);
		System.out.println("True Positive: " + truePositive);
		System.out.println("False Positive: " + falsePositive);
		System.out.println("True Negative: " + trueNegative);
		System.out.println("Precision: "
				+ realOutput.computePrecision(truePositive, falsePositive));
		System.out.println("Recall: "
				+ realOutput.computeRecall(truePositive, falseNegative));
		System.out.println("True Negative Rate: "
				+ realOutput.computeTrueNegativeRate(trueNegative,
						falsePositive));
		System.out.println("Accuracy: "
				+ realOutput.computeAccuracy(truePositive, trueNegative,
						falsePositive, falseNegative));
		System.out.println("F-Measure: "
				+ realOutput.computeFMeasure(truePositive, falsePositive,
						falseNegative));
	}

	public static void main(String[] args) {
		computeOutputSVMParameters();
	}
}
