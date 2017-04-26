package edu.utcn.eeg.artifactdetection.model;

import com.google.common.base.MoreObjects;

public class Segment extends AbstractSegment{

	private static final long serialVersionUID = 1L;
	private int channelNr;
	private double[] values;

	public Segment(double[] values) {
		this.values = values.clone();
	}

	public int getChannelNr() {
		return channelNr;
	}

	public void setChannelNr(int channelNr) {
		this.channelNr = channelNr;
	}

	public double[] getValues() {
		return values;
	}

	public void setValues(double[] values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).omitNullValues().add("Channel ", channelNr).add("Iteration ", iterIdx)
				.add("Label ", correctType).toString();
	}
	
	public SegmentKey getMultiChannelKey(){
		return new SegmentKey(iterIdx, initIdx);
	}
	
	public Double getFeatureValueForFeature(FeatureType featureType){
		for (Feature feature : features) {
			if(feature.getFeature().equals(featureType)){
				return feature.getValue();
			}
		}
		return null;
	}
	
	public String getValuesToString(){
		String string = "Start ";
		for (double d : values) {
			string+=d+" ";
		}
		return string+" End";
	}
}
