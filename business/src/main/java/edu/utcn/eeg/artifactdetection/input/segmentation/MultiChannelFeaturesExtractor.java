package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.util.List;
import edu.utcn.eeg.artifactdetection.features.CorrelationFeaturesExtractor;
import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.FeatureType;
import edu.utcn.eeg.artifactdetection.model.MultiChannelSegment;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class MultiChannelFeaturesExtractor {

	public void setMultiChannelFeatureForAllSegments(MultiChannelSegment multiChannelSegment) {
		for (Segment segment : multiChannelSegment.getSegments()) {
			computeMultiChannelFeature(multiChannelSegment, segment);
		}
	}

	public void setMultiChannelFeatureForRegion(MultiChannelSegment multiChannelSegment)
	{
		for (Segment segment : multiChannelSegment.getSegments())
		{
			computeMultiChannelFeature2(multiChannelSegment, segment);
		}
	}

	public void computeMultiChannelFeature2(MultiChannelSegment multiChannelSegment, Segment segment)
	{
		// double[] mean = getMultichannelMeanData(multiChannelSegment,
		// segment);
		//CorrelationFeaturesExtractor correlationFeaturesExtractor = new CorrelationFeaturesExtractor();

		setFeature(segment, getMultichannelMeanMaxCorrResults(multiChannelSegment.getRegionalSegment(segment), segment));
	}

	public void computeMultiChannelFeature(MultiChannelSegment multiChannelSegment, Segment segment) {
		// double[] mean = getMultichannelMeanData(multiChannelSegment,
		// segment);
		//CorrelationFeaturesExtractor correlationFeaturesExtractor = new CorrelationFeaturesExtractor();

		setFeature(segment, getMultichannelMeanResults(multiChannelSegment, segment), getMultichannelMeanMaxCorrResults(multiChannelSegment, segment));
	}


	private void setFeature(Segment segment, double pearson, double maxCorrelation) {
		//double pearson = correlationFeaturesExtractor.computePearsonCorrelationCoeficient(segment.getValues(), mean);
		Feature[] features = new Feature[segment.getFeatures().length + 2];
		int i = 0;
		for (Feature feature : segment.getFeatures()) {
			features[i++] = feature;
		}
		Feature feature = new Feature(FeatureType.PEARSON);
		feature.setValue(pearson);
		features[i++] = feature;
		Feature feature2 = new Feature(FeatureType.MAX_CORRELATION);
		feature2.setValue(maxCorrelation);
		features[i++] = feature2;
		segment.setFeatures(features);
	}

	private void setFeature(Segment segment, double maxCorrelation)
	{
		//double pearson = correlationFeaturesExtractor.computePearsonCorrelationCoeficient(segment.getValues(), mean);
		Feature[] features = new Feature[segment.getFeatures().length + 2];
		int i = 0;
		for (Feature feature : segment.getFeatures())
		{
			if (feature.getFeature()
					   .equals(FeatureType.MAX_CORRELATION))
			{
				feature.setValue(maxCorrelation);
			}
			features[i++] = feature;
		}
		segment.setFeatures(features);
	}

	private double[] getMultichannelMeanData(MultiChannelSegment multiChannelSegment, Segment segment) {
		List<Segment> segments = multiChannelSegment.getSegments();
		int size = segment.getValues().length;
		double[] meanValues = new double[size];
		int n = segments.size() - 1;
		for (int i = 0; i < size; i++) {
			for (Segment otherSegment : segments) {
				if (!otherSegment.equals(segment)) {
					meanValues[i] += otherSegment.getValues()[i];
				}
			}
			meanValues[i] /= n;
		}
		return meanValues;
	}

	private double getMultichannelMeanResults(MultiChannelSegment multiChannelSegment, Segment segment) {
		List<Segment> segments = multiChannelSegment.getSegments();
		CorrelationFeaturesExtractor correlationFeaturesExtractor = new CorrelationFeaturesExtractor();
		int size = segment.getValues().length;
		double mean = 0;
		for (Segment otherSegment : segments) {
			if(!segment.equals(otherSegment)){
				double pearson = correlationFeaturesExtractor.computePearsonCorrelationCoeficient(segment.getValues(),
						otherSegment.getValues());
				mean+=Math.abs(pearson);
			}
		}
		return mean/(size-1);
	}
	
	private double getMultichannelMeanMaxCorrResults(MultiChannelSegment multiChannelSegment, Segment segment) {
		List<Segment> segments = multiChannelSegment.getSegments();
		CorrelationFeaturesExtractor correlationFeaturesExtractor = new CorrelationFeaturesExtractor();
		int size = segment.getValues().length;
		double mean = 0;
		for (Segment otherSegment : segments) {
			if(!segment.equals(otherSegment)){
				double maxCorr = correlationFeaturesExtractor.computeCrossCorrelationMaxValue(segment.getValues(),
						otherSegment.getValues());
				mean+=Math.abs(maxCorr);
			}
		}
		return mean/(size-1);
	}

	private double getMultichannelMeanMaxCorrResults(List<Segment> segments, Segment segment)
	{
		CorrelationFeaturesExtractor correlationFeaturesExtractor = new CorrelationFeaturesExtractor();
		int size = segment.getValues().length;
		double mean = 0;
		for (Segment otherSegment : segments)
		{
			if (!segment.equals(otherSegment))
			{
				double maxCorr = correlationFeaturesExtractor.computeCrossCorrelationMaxValue(segment.getValues(), otherSegment.getValues());
				mean += Math.abs(maxCorr);
			}
		}
		return mean / (size - 1);
	}
}
