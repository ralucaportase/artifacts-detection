package edu.utcn.eeg.artifactdetection.builders;

import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.FeatureType;

public class FeatureBuilder {
	
	public static Feature[] createStandardFeaturesInstances(){
		Feature[] features = new Feature[FeatureType.values().length];
		int i=0;
		for(FeatureType feat: FeatureType.values()){
			features[i++]= new Feature(feat);
		}
		return features;
	}

}
