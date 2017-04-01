package edu.utcn.eeg.artifactdetection.model;

import java.io.Serializable;

public class Feature implements Serializable{

	private static final long serialVersionUID = 2L;
	private FeatureType feature;
	private double value;

	public Feature(FeatureType feature) {
		this.feature = feature;
	}
	
	public FeatureType getFeature() {
		return feature;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Pair [featureName=" + feature + ", valueName=" + value + "]";
	}
}
