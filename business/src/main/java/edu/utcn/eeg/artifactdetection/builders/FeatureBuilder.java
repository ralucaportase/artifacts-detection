package edu.utcn.eeg.artifactdetection.builders;

import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.FeatureType;
import edu.utcn.eeg.artifactdetection.model.MultiChannelFeatureType;
import edu.utcn.eeg.artifactdetection.model.Region;

public class FeatureBuilder {
	
	public static Feature[] createStandardFeaturesInstances(){
		Feature[] features = new Feature[FeatureType.values().length];
		int i=0;
		for(FeatureType feat: FeatureType.values()){
			features[i++]= new Feature(feat);
		}
		return features;
	}
	
	public static Feature[] createStandardMultiChannelFeaturesInstances(){
		Feature[] features = new Feature[MultiChannelFeatureType.values().length];
		int i=0;
		for(MultiChannelFeatureType feat: MultiChannelFeatureType.values()){
			features[i++]= new Feature(feat);
		}
		return features;
	}
	
	public static Feature[] createStandardMultiChannelFeaturesPerRegionInstances(){
		Feature[] features = new Feature[MultiChannelFeatureType.values().length*4];
		int i=0;
		for(MultiChannelFeatureType feat: MultiChannelFeatureType.values()){
			features[i]= new Feature(feat,Region.getRegionByIndex(i));
			i++;
		}
		return features;
	}

}
