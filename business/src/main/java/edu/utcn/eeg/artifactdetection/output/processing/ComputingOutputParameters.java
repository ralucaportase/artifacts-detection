package edu.utcn.eeg.artifactdetection.output.processing;

import java.util.List;

/**
 * Methods for computing parameters for the classification
 * 
 * @author RalucaPortase
 *
 */
public class ComputingOutputParameters {

	/**
	 * Precision, also referred to as positive predictive value (PPV)
	 * 
	 * @param truePositive
	 * @param falsePositive
	 */
	public double computePrecision(int truePositive, int falsePositive) {
		return (double) truePositive / (truePositive + falsePositive) * 100;
	}

	/**
	 * Recall, also referred to as the true positive rate or sensitivity
	 * 
	 * @param truePositive
	 * @param falseNegative
	 */
	public double computeRecall(int truePositive, int falseNegative) {
		return (double) truePositive / (truePositive + falseNegative) * 100;
	}

	/**
	 * True negative rate, also called specificity.
	 * 
	 * @param trueNegative
	 * @param falsePositive
	 */
	public double computeTrueNegativeRate(int trueNegative, int falsePositive) {
		return (double) trueNegative / (trueNegative + falsePositive) * 100;
	}

	/**
	 * Accuracy
	 * 
	 * @param truePositive
	 * @param trueNegative
	 * @param falsePositive
	 * @param falseNegative
	 */
	public double computeAccuracy(int truePositive, int trueNegative,
			int falsePositive, int falseNegative) {
		return (double) (trueNegative + truePositive)
				/ (trueNegative + truePositive + falsePositive + falseNegative)
				* 100;
	}

	/**
	 * F-measure is the harmonic mean of precision and recall
	 * 
	 * @param truePositive
	 * @param falsePositive
	 * @param falseNegative
	 */
	public double computeFMeasure(int truePositive, int falsePositive,
			int falseNegative) {
		double precision = computePrecision(truePositive, falsePositive);
		double recall = computeRecall(truePositive, falseNegative);

		return (double) 2 * precision * recall / (precision + recall) ;
	}

}
