package edu.utcn.eeg.artifactdetection.classifier.knn;

public class ManhattenDistance implements Metric {

	public double getDistance(Record s, Record e) {
		assert s.attributes.length == e.attributes.length : "s and e are different types of records!";
		int numOfAttributes = s.attributes.length;
		double sum = 0;

		for (int i = 0; i < numOfAttributes; i++) {
			sum += Math.abs(s.attributes[i] - e.attributes[i]);
		}

		return sum;
	}

}