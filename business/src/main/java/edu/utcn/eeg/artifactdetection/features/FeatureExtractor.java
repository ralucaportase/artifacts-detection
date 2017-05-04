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
		case SKEWNESS: 
			return timeFeatureExtractor.computeSkewness(data);
		case KURTOSIS:
			return timeFeatureExtractor.computeKurtosis(data);
		case ENTROPY: 
			return frequencyFeatureExtractor.computeShannonEntropy();
		case MEAN: 
			return timeFeatureExtractor.computeMean(data);
		case MEDIAN:
			return timeFeatureExtractor.computeMedian(data);
		case RMS:
			return timeFeatureExtractor.computeRootMeanSquare(data);
		case PEARSON: 
			return new Double(0);
		default:
			return null;
		}
	}
	
	public static Double getPearsonCoefficientValue(double[] x, double[] y){
		CorrelationFeaturesExtractor correlationFeaturesExtractor = new CorrelationFeaturesExtractor();
		return correlationFeaturesExtractor.computePearsonCorrelationCoeficient(x, y);
	}

}
