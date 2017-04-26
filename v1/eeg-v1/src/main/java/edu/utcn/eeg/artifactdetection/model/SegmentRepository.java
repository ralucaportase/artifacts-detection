package edu.utcn.eeg.artifactdetection.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SegmentRepository implements Serializable{

	private static final long serialVersionUID = 3L;

	private List<AbstractSegment> segments; 
	private String name;
	public SegmentRepository(String name) {
		this.name = name;
		segments  = new ArrayList<AbstractSegment>();
	}
	
	public String getName(){
		return name;
	}
	
	public void addSegment(AbstractSegment segment){
		segments.add(segment);
	}
	
	public List<AbstractSegment> getSegments(){
		return this.segments;
	}

	@Override
	public String toString(){
		return "Structure "+name+" "+segments.size();
	}
	
	public void setSegments(List<AbstractSegment> segments){
		this.segments = segments;
	}
}
