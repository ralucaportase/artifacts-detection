package edu.utcn.eeg.artifactdetection.classifier.knn;
/**
 * This class is used for implementic a mtric by euclidian metric formula.
 * 
 * @author Tolas Ramona
 *
 */
public class EuclideanDistance implements Metric {

	public double getDistance(Record s, Record e) {
		assert s.attributes.length == e.attributes.length : "s and e are different types of records!";
		int numOfAttributes = s.attributes.length;
		double sum = 0;

		for (int i = 0; i < numOfAttributes; i++) {
			sum += Math.pow(s.attributes[i] - e.attributes[i], 2);
		}

		return Math.sqrt(sum);
	}

}
