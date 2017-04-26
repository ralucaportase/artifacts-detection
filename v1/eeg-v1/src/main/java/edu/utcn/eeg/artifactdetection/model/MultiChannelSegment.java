package edu.utcn.eeg.artifactdetection.model;

import java.util.List;

public class MultiChannelSegment extends AbstractSegment{

	private static final long serialVersionUID = 1L;
	private List<Segment> segments; 
	
	public MultiChannelSegment(List<Segment> segments){
		this.segments = segments;
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}
	
	
}
