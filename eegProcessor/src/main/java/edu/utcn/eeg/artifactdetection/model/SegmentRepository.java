package edu.utcn.eeg.artifactdetection.model;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SegmentRepository implements Serializable{

	private static final long serialVersionUID = 3L;

	private List<Segment> segments; 
	private String name;
	public SegmentRepository(String name) {
		this.name = name;
		segments  = new ArrayList<Segment>();
	}
	
	public String getName(){
		return name;
	}
	
	public void addSegment(Segment segment){
		segments.add(segment);
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}
	
	

}
