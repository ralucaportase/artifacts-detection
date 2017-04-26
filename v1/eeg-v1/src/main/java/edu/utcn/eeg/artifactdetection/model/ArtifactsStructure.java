package edu.utcn.eeg.artifactdetection.model;

import java.util.List;


import com.google.common.collect.Range;

public class ArtifactsStructure {

	private ArtifactType type; 
	private List<Range<Integer>> ranges;
	
	public ArtifactsStructure(ArtifactType type,List<Range<Integer>> ranges) {
		this.type = type; 
		this.ranges =ranges;
	}

	public ArtifactType getType() {
		return type;
	}

	public List<Range<Integer>> getRanges() {
		return ranges;
	}
}
