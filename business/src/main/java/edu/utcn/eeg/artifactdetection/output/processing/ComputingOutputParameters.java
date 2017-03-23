package edu.utcn.eeg.artifactdetection.output.processing;

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
		return truePositive / (truePositive + falsePositive);
	}

	/**
	 * Recall, also referred to as the true positive rate or sensitivity
	 * 
	 * @param truePositive
	 * @param falseNegative
	 */
	public double computeRecall(int truePositive, int falseNegative) {
		return truePositive / (truePositive + falseNegative);
	}

	/**
	 * True negative rate, also called specificity.
	 * 
	 * @param trueNegative
	 * @param falsePositive
	 */
	public double computeTrueNegativeRate(int trueNegative, int falsePositive) {
		return trueNegative / (trueNegative + falsePositive);
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
		return (trueNegative + truePositive)
				/ (trueNegative + truePositive + falsePositive + falseNegative);
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

		return 2 * precision * recall / (precision + recall);
	}

}
