package edu.utcn.eeg.artifactdetection.model;

import java.util.List;

public class MultiChannelSegment extends AbstractSegment implements Comparable{

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
	
	@Override
	public int compareTo(Object arg0) {
		if(!(arg0 instanceof Segment)){
			return -1;
		}
		Segment segm = (Segment)arg0;
			if(segm.getInitIdx()>this.getInitIdx()){
				return -1;
			}else if(segm.getInitIdx()<this.getInitIdx()){
				return 1;
			}
			return 0;
		}
}
