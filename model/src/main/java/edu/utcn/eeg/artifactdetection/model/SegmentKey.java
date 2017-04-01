package edu.utcn.eeg.artifactdetection.model;

import java.io.Serializable;

public class SegmentKey implements Serializable{

	private static final long serialVersionUID = 1L;
	private int initIdx;
	private int iterIdx;
	
	public SegmentKey(int iterIdx, int initIdx) {
		this.initIdx = initIdx;
		this.iterIdx = iterIdx;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + initIdx;
		result = prime * result + iterIdx;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SegmentKey other = (SegmentKey) obj;
		if (initIdx != other.initIdx)
			return false;
		if (iterIdx != other.iterIdx)
			return false;
		return true;
	}
	
	
}
