package edu.utcn.eeg.artifactdetection.classifier.knn;
//compared with Record, add another attribute - distance which is used to
//store the distance between testRecord and the current trainRecord

public class TrainRecord extends Record {
	
	private double distance;
	
	public TrainRecord(double[] attributes, int classLabel) {
		super(attributes, classLabel);
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	
}
