package edu.utcn.eeg.artifactdetection.classifier.knn;
/**
 * This class is used for modeling a point. It is represented by features and
 * label assigned to it.
 * 
 * @author Tolas Ramona
 *
 */
public class Record {

	double[] attributes;
	int classLabel;

	public Record(double[] attributes, int classLabel) {
		this.attributes = attributes;
		this.classLabel = classLabel;
	}

	public double[] getAttributes() {
		return attributes;
	}

	public void setAttributes(double[] attributes) {
		this.attributes = attributes;
	}

	public int getClassLabel() {
		return classLabel;
	}

	public void setClassLabel(int classLabel) {
		this.classLabel = classLabel;
	}

}
