package edu.utcn.eeg.artifactdetection.model;

public enum MultiChannelFeatureType {

	STANDARD_DEVIATION,
	ENTROPY,
	MAX_CORRELATION;
	
	public static FeatureType getFeatureTypeFromMultiChannelType(MultiChannelFeatureType multiChannelFeatureType){
		return FeatureType.valueOf(multiChannelFeatureType.toString());
	}
}
