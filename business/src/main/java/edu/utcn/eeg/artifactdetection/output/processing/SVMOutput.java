package edu.utcn.eeg.artifactdetection.output.processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.utcn.eeg.artifactdetection.model.Configuration;

/**
 * Methods for computing svm output precision
 * 
 * @author RalucaPortase
 *
 */
public class SVMOutput {

	public List<Double> parseOutputFile(File file) {
		List<Double> fileOutput = new ArrayList<Double>();

		//FileInputStream fis;
		try {
			//fis = new FileInputStream(file);

			Scanner scan = new Scanner(file);
			while (scan.hasNextDouble()) {
				fileOutput.add(scan.nextDouble());
				if (fileOutput.size() > Configuration.MAX_INDEX) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileOutput;
	}

	/**
	 * 
	 * @param inputClassification
	 * @param outputClassification
	 * @return no of false positive
	 */
	public int computeFalsePositiveRate(List<Double> inputClassification,
			List<Double> outputClassification) {
		int noOfFalsePositive = 0;
		for (int i = 0; i < inputClassification.size(); i++) {
			//input is clean but output is considered artifact
			if (inputClassification.get(i) > 0
					&& outputClassification.get(i) < 0) {
				noOfFalsePositive++;
			}
		}
		return noOfFalsePositive;
	}

	/**
	 * 
	 * @param inputClassification
	 * @param outputClassification
	 * @return no of true positive
	 */
	public int computeTruePositiveRate(List<Double> inputClassification,
			List<Double> outputClassification) {
		int noOfTruePositive = 0;
		for (int i = 0; i < inputClassification.size(); i++) {
			//contains artifacts, classified as so
			if (inputClassification.get(i) < 0
					&& outputClassification.get(i) < 0) {
				noOfTruePositive++;
			}
		}
		return noOfTruePositive;
	}

	/**
	 * Function which computes the numbers of values that are artifacts but classified as clean
	 * @param inputClassification
	 * @param outputClassification
	 * @return no of false negative
	 */
	public int computeFalseNegativeRate(List<Double> inputClassification,
			List<Double> outputClassification) {
		int noOfFalseNegative = 0;
		for (int i = 0; i < inputClassification.size(); i++) {
			//input is artifact, but output is considered clean
			if (inputClassification.get(i) < 0
					&& outputClassification.get(i) > 0) {
				noOfFalseNegative++;
			}
		}
		return noOfFalseNegative;
	}

	/**
	 * 
	 * @param inputClassification
	 * @param outputClassification
	 * @return no of true negative
	 */
	public int computeTrueNegativeRate(List<Double> inputClassification,
			List<Double> outputClassification) {
		int noOfTrueNegative = 0;
		for (int i = 0; i < inputClassification.size(); i++) {
			//clean, classified as so
			if (inputClassification.get(i) > 0
					&& outputClassification.get(i) > 0) {
				noOfTrueNegative++;
			}
		}
		return noOfTrueNegative;
	}
}
