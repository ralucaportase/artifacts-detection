package edu.utcn.eeg.artifactdetection.classifier.knn;
/**
 * This interface is used for defining the general use of a metric mesaure
 * between two points.
 * 
 * @author Tolas Ramona
 *
 */

public interface Metric {
	double getDistance(Record s, Record e);
}
