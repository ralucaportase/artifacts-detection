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
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class SvmBinaryClassifier implements Classifier {

	protected String outputBinaryFilename = Configuration.PROJECT_PATH
			+ "/svm/svm_TestCompare.csv";
	protected String inputBinaryTestFilename = Configuration.PROJECT_PATH
			+ "/svm/svmExperiment.dat";
	protected String binaryModelFilename = Configuration.PROJECT_PATH
			+ "/svm/AllForTrain_model9feat.dat";

	public SvmBinaryClassifier() {

	}

	public SvmBinaryClassifier(String outputFilename, String inputTestFilename) {
		this.outputBinaryFilename = outputFilename;
		this.inputBinaryTestFilename = inputTestFilename;
	}

	public List<Segment> classifySegments(List<Segment> segments) {
		classify(segments);
		List<Double> classificationResults = FileReader.getInstance()
				.parseTxtFile(new File(outputBinaryFilename));
		for (int i = 0; i < classificationResults.size(); i++) {
			if (classificationResults.get(i) < 0) {
				segments.get(i).setCorrectType(ResultType.BRAIN_SIGNAL);
			} else {
				segments.get(i).setCorrectType(ResultType.MUSCLE);
			}
		}
		return segments;
	}

	/*
	 * format used for learning file is <line> .=. <target> <feature>:<value>
	 * ... <feature>:<value> # <info> <target> .=. +1 | -1 | 0 | <float>
	 * <feature> .=. <integer> | "qid" <value> .=. <float> <info> .=. <string>
	 */
	private static String createLineContent(AbstractSegment segment,
			ResultType negativeExample) {
		String lineContent = "";
		if (segment.getCorrectType() == negativeExample) {
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

	public void exportSegments(List<Segment> segments,
			ResultType negativeExample, String fileName) {
		String lineContent = "";

		for (AbstractSegment segment : segments) {
			lineContent += createLineContent(segment, negativeExample) + "\n";
		}

		OutputFileWriter.getInstance().writeToFile(lineContent, fileName);
	}

	public static void callSvmClassify(File testingFile, File modelFile,
			File resultFile) {
		System.out.println("Start classifying ...");

		String cmd = Configuration.PROJECT_PATH + "/svm/svm_classify.exe";
		try {
			cmd += " " + testingFile.getAbsolutePath() + " "
					+ modelFile.getAbsolutePath() + " "
					+ resultFile.getAbsolutePath();
			System.out.println("Classify cmd: " + cmd);

			Process proc = Runtime.getRuntime().exec(cmd);

			InputStream istr = proc.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(istr));
			String line;
			/*while ((line = in.readLine()) != null) {
				System.out.println(line);
			}*/
		} catch (Exception e) {
			//e.printStackTrace();
			//throw new RuntimeException("Exception while classifying SVM-Light");
		}
	}

	public void classify(List<Segment> segments) {
		exportSegments(segments, ResultType.BRAIN_SIGNAL,
				inputBinaryTestFilename);
		File testFile = new File(inputBinaryTestFilename);
		File modelFile = new File(binaryModelFilename);
		File predictionFile = new File(outputBinaryFilename);
		callSvmClassify(testFile, modelFile, predictionFile);
	}

}
