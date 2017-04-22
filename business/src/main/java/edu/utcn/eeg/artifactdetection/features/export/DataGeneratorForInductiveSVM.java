package edu.utcn.eeg.artifactdetection.features.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.utcn.eeg.artifactdetection.features.FeatureExtractor;
import edu.utcn.eeg.artifactdetection.input.segmentation.LoggerUtil;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.Feature;
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

	private static Logger logger = LoggerUtil
			.logger(DataGeneratorForInductiveSVM.class);

	private static final String LEARNING_FILENAME = Configuration.PROJECT_PATH
			+ "/svm/svm_Train66-102All.dat";

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

	private static String getLineContent(AbstractSegment segment) {
		String lineContent = "";
		if (segment.getCorrectType() == ResultType.BRAIN_SIGNAL) {
			lineContent += "-1";
		} else
			lineContent += "1";
		Feature[] features = segment.getFeatures();
		for (int i = 0; i < features.length; i++) {
			if (features[i].getFeature().toString() == "MEAN") {
				lineContent += " 1:" + features[i].getValue();
			} else if (features[i].getFeature().toString() == "MEDIAN") {
				lineContent += " 2:" + features[i].getValue();
			} else if (features[i].getFeature().toString() == "RMS") {
				lineContent += " 3:" + features[i].getValue();
			} else if (features[i].getFeature().toString() == "STANDARD_DEVIATION") {
				lineContent += " 4:" + features[i].getValue();
			} else if (features[i].getFeature().toString() == "DELTA_SPECTRUM") {
				lineContent += " 5:" + features[i].getValue();
			} else if (features[i].getFeature().toString() == "ALPHA_SPECTRUM") {
				lineContent += " 6:" + features[i].getValue();
			} else if (features[i].getFeature().toString() == "BETHA_LOW_SPECTRUM") {
				lineContent += " 7:" + features[i].getValue();
			} else if (features[i].getFeature().toString() == "THETA_SPECTRUM") {
				lineContent += " 8:" + features[i].getValue();
			} else if (features[i].getFeature().toString() == "GAMMA_LOW_SPECTRUM") {
				lineContent += " 9:" + features[i].getValue();
			} else if (features[i].getFeature().toString() == "BETHA_HIGH_SPECTRUM") {
				lineContent += " 10:" + features[i].getValue();
			} else if (features[i].getFeature().toString() == "GAMMA_HIGH_SPECTRUM") {
				lineContent += " 11:" + features[i].getValue();
			} else if (features[i].getFeature().toString() == "SKEWNESS") {
				lineContent += " 12:" + features[i].getValue();
			} else if (features[i].getFeature().toString() == "KURTOSIS") {
				lineContent += " 13:" + features[i].getValue();
			} else if (features[i].getFeature().toString() == "ENTROPY") {
				lineContent += " 14:" + features[i].getValue();
			}

		}

		return lineContent;
	}

	/*
	 * format used for learning file is <line> .=. <target> <feature>:<value>
	 * ... <feature>:<value> # <info> <target> .=. +1 | -1 | 0 | <float>
	 * <feature> .=. <integer> | "qid" <value> .=. <float> <info> .=. <string>
	 */
	public static String getContentOfLearningFile(List<AbstractSegment> segments) {
		List<Double> outputFeatures;
		String lineContent = "";
		boolean ok = true;
		int index, // index represents the feature
		noOfArtifacts = 0, noOfBrainSignals = 0;

		for (AbstractSegment segment : segments) {
			if (!(segment instanceof AbstractSegment)) {
				logger.error("AbstractSegment not instance of Segment! DataGeneratorForInductiveSVM[getContentOfLearningFile]");
				return null;
			}
			// outputFeatures = getFeaturesForSegment((AbstractSegment)
			// segment);
			// we use a binary classification when 1 represent
			// brain signal and -1 artifact
			/*
			 * if (segment.getCorrectType() == ResultType.BRAIN_SIGNAL) { //
			 * make sure that we have equal no of learning samples if
			 * (noOfBrainSignals <= noOfArtifacts) { lineContent += "\n1 ";
			 * noOfBrainSignals++; ok = true; } else ok = false; } else {
			 * lineContent += "\n-1 "; noOfArtifacts++; ok = true; //
			 * System.out.println("Processing " + segment.getCorrectType()); }
			 * index = 1; if (ok == true) { for (Double value : outputFeatures)
			 * { if (value != null) { lineContent += index + ":" + value + " ";
			 * } index++; } }
			 */
			lineContent += getLineContent(segment) + "\n";
		}

		return lineContent;
	}

	private static void writeToFile(List<AbstractSegment> segments) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			String content = getContentOfLearningFile(segments);
			if (content == null) {
				System.out.println("Null string content");
			}
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

	public static void createSvmInputFiles() {
		SegmentDeserializer deserializer = new SegmentDeserializer();
		List<AbstractSegment> segments = new ArrayList<AbstractSegment>();

		SegmentRepository repository = deserializer
				.deserializeSegmentsFromFile("D:/DiplomaCode/artifacts-detection/results/66-102Splited/Muscle_Train.ser");
		segments.addAll(repository.getSegments());
		repository = deserializer
				.deserializeSegmentsFromFile("D:/DiplomaCode/artifacts-detection/results/66-102Splited/Occular_Train.ser");
		segments.addAll(repository.getSegments());
		repository = deserializer
				.deserializeSegmentsFromFile("D:/DiplomaCode/artifacts-detection/results/66-102Splited/Clean_Train.ser");
		segments.addAll(repository.getSegments());

		writeToFile(segments);
		System.out.println("done writing file!");
	}

	public static void computeOutputSVMParameters() {
		SVMOutput svmOutput = new SVMOutput();
		List<Double> inputClassification = svmOutput.parseOutputFile(new File(
				"D:/DiplomaCode/artifacts-detection/svm/svm_Test66-102.csv"));
		List<Double> outputClassification = svmOutput
				.parseOutputFile(new File(
						"D:/DiplomaCode/artifacts-detection/svm/svm_prediction66-102.dat"));

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
		 //createSvmInputFiles();
	}
}
