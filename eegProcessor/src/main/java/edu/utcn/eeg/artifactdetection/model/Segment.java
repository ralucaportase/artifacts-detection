package edu.utcn.eeg.artifactdetection.model;



import java.io.Serializable;

import com.google.common.base.MoreObjects;

public class Segment implements Serializable {

	private static final long serialVersionUID = 1L;
	private int channelNr;
	private int initIdx;
	private int iterIdx;

	private double[] values;
	private Feature[] features;
	private ResultType correctType;

	public Segment(double[] values) {
		this.values = values;
	}

	public int getChannelNr() {
		return channelNr;
	}

	public void setChannelNr(int channelNr) {
		this.channelNr = channelNr;
	}

	public int getInitIdx() {
		return initIdx;
	}

	public void setInitIdx(int tInit) {
		this.initIdx = tInit;
	}

	public Feature[] getFeatures() {
		return features;
	}

	public void setFeatures(Feature[] features) {
		this.features = features;
	}

	public int getIterIdx() {
		return iterIdx;
	}

	public void setIterIdx(int id) {
		this.iterIdx = id;
	}

	public ResultType getCorrectType() {
		return correctType;
	}

	public void setCorrectType(ResultType correctType) {
		this.correctType = correctType;
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
}
