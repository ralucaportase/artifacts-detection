package edu.utcn.eeg.artifactdetection.classifier.knn;
/**
 * This class models a specific kind of record: the test record which has an
 * extra attribute, predicted label by the classifier. The true label and the
 * predicted label are used for calculating the accuracy of the classifier.
 * 
 * @author Tolas Ramona
 *
 */

public class TestRecord extends Record {

	private int predictedLabel;

	public TestRecord(double[] attributes, int classLabel) {
		super(attributes, classLabel);
	}

	public int getPredictedLabel() {
		return predictedLabel;
	}

	public void setPredictedLabel(int predictedLabel) {
		this.predictedLabel = predictedLabel;
	}

}
