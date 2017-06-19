package edu.utcn.eeg.artifactdetection.classifier.svm;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import edu.utcn.eeg.artifactdetection.classifier.Classifier;
import edu.utcn.eeg.artifactdetection.helpers.FileReader;
import edu.utcn.eeg.artifactdetection.helpers.OutputFileWriter;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.ResultType;

public class SvmClassifier implements Classifier {

	private static String outputFilename = "D:/DiplomaCode/artifacts-detection/svm/svm_TestCompare.csv";
	private static String inputTestFilename = "D:/DiplomaCode/artifacts-detection"
			+ "/svm/svmExperiment.dat";

	public SvmClassifier(String outputFilename, String inputTestFilename) {
		this.outputFilename = outputFilename;
		this.inputTestFilename = inputTestFilename;
	}

	public List<AbstractSegment> classifySegments(List<AbstractSegment> segments) {
		List<Double> classificationResults = FileReader.getInstance()
				.parseTxtFile(new File(outputFilename));
		for (int i = 0; i < classificationResults.size(); i++) {
			if (classificationResults.get(i) < 0) {
				segments.get(i).setCorrectType(ResultType.BRAIN_SIGNAL);
			} else {
				segments.get(i).setCorrectType(ResultType.MUSCLE);
			}
		}
		return null;
	}

	/*
	 * format used for learning file is <line> .=. <target> <feature>:<value>
	 * ... <feature>:<value> # <info> <target> .=. +1 | -1 | 0 | <float>
	 * <feature> .=. <integer> | "qid" <value> .=. <float> <info> .=. <string>
	 */
	private static String createLineContent(AbstractSegment segment) {
		String lineContent = "";
		if (segment.getCorrectType() == ResultType.BRAIN_SIGNAL) {
			lineContent += "-1";
		} else
			lineContent += "1";
		Feature[] features = segment.getFeatures();
		for (int i = 0; i < features.length; i++) {
			switch (features[i].getFeature().toString()) {
			case "RMS":
				lineContent += " 3:" + features[i].getValue();
				break;
			case "STANDARD_DEVIATION":
				lineContent += " 4:" + features[i].getValue();
				break;
			case "DELTA_SPECTRUM":
				lineContent += " 5:" + features[i].getValue();
				break;
			case "THETA_SPECTRUM":
				lineContent += " 8:" + features[i].getValue();
				break;
			case "GAMMA_LOW_SPECTRUM":
				lineContent += " 9:" + features[i].getValue();
				break;
			case "GAMMA_HIGH_SPECTRUM":
				lineContent += " 11:" + features[i].getValue();
				break;
			case "ENTROPY":
				lineContent += " 14:" + features[i].getValue();
				break;
			case "PEARSON":
				lineContent += " 15:" + features[i].getValue();
				break;
			case "MAX_CORRELATION":
				lineContent += " 16:" + features[i].getValue();
				break;
			default:
				lineContent += "";
				break;
			}
		}

		return lineContent;
	}

	public void exportSegments(List<AbstractSegment> segments) {
		String lineContent = "";

		for (AbstractSegment segment : segments) {
			lineContent += createLineContent(segment) + "\n";
		}

		OutputFileWriter.getInstance().writeToFile(lineContent,
				inputTestFilename);
	}

	public static void callSvmClassify(File testingFile, File modelFile,
			File resultFile) {
		System.out.println("Start classifying ...");

		String cmd = "D:/DiplomaCode/artifacts-detection/svm/svm_classify.exe";
		try {
			cmd += " " + testingFile.getAbsolutePath() + " "
					+ modelFile.getAbsolutePath() + " "
					+ resultFile.getAbsolutePath();
			System.out.println("Classify cmd: " + cmd);

			Process proc = Runtime.getRuntime().exec(cmd);

			InputStream istr = proc.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(istr));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Exception while classifying SVM-Light");
		}
	}

	public static void classify(List<AbstractSegment> segments) {
		// exportSegments(segments);
		// File testFile = new File(SVM_TEST_FILENAME);
		File testFile = new File(
				"D:/DiplomaCode/artifacts-detection/svm/AllForTest.dat");
		File modelFile = new File(
				"D:/DiplomaCode/artifacts-detection/svm/svm_model9feat.dat");
		File predictionFile = new File(outputFilename);
		callSvmClassify(testFile, modelFile, predictionFile);
	}

	public static void main(String[] args) {
		classify(null);
	}

}
