package edu.utcn.eeg.artifactdetection.features;

import edu.utcn.eeg.artifactdetection.model.FeatureType;

public class FeatureExtractor {

	public static Double getFeatureValue(FeatureType feature, double[] data){
		TimeFeatureExtractor timeFeatureExtractor = new TimeFeatureExtractor();
		FrequencyFeaturesExtractor frequencyFeatureExtractor = new FrequencyFeaturesExtractor(data);
		switch(feature){
		case STANDARD_DEVIATION: 
			return timeFeatureExtractor.computeStandardDeviation(data);
		case DELTA_SPECTRUM:
			return frequencyFeatureExtractor.computeDeltaMean();
		case ALPHA_SPECTRUM: 
			return frequencyFeatureExtractor.computeAlphaMean();
		case BETHA_LOW_SPECTRUM:
			return frequencyFeatureExtractor.computeBetaLowMean();
		case THETA_SPECTRUM:
			return frequencyFeatureExtractor.computeThetaMean();
		case GAMMA_LOW_SPECTRUM: 
			return frequencyFeatureExtractor.computeGammaLowMean();
		case BETHA_HIGH_SPECTRUM: 
			return frequencyFeatureExtractor.computeBetaHighMean();
		case GAMMA_HIGH_SPECTRUM:
			return frequencyFeatureExtractor.computeGammaHighMean();
		default:
			return null;
		}
	}

}
