package edu.utcn.eeg.artifactdetection.model;

import java.io.Serializable;

import com.google.common.base.Objects;

public class AbstractSegment implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int initIdx;
	protected int iterIdx;
	protected Feature[] features;
	protected ResultType correctType;
	

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
	
	@Override
	public int hashCode() {
		return Objects.hashCode(initIdx,iterIdx);
	};
	
	@Override
	public boolean equals(Object object){
		if(object==this){
			return true;
		}
		if(!(object instanceof AbstractSegment)){
			return false;
		}
		AbstractSegment segment = (AbstractSegment)object;
		if(segment.initIdx!=this.initIdx||segment.iterIdx!=this.iterIdx){
			return false;
		}
		return true;
	}

}
