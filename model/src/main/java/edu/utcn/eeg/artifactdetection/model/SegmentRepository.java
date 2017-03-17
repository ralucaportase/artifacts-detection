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
		segments  = new ArrayList<>();
	}
	
	public String getName(){
		return name;
	}
	
	public void addSegment(Segment segment){
		segments.add(segment);
	}

}
