package edu.utcn.eeg.artifactdetection.model;

import java.util.Arrays;

import java.util.List;

import com.google.common.collect.Range;

public enum ArtifactType {

	OCCULAR(Arrays.asList(Range.closed(72, 74), Range.closed(77, 86), Range.closed(89, 96))), 
	MUSCLE_D(Arrays.asList(Range.closed(97, 128))), 
	MUSCLE_C(Arrays.asList(Range.closed(72, 96))), 
	REJECT(Arrays.asList(Range.closed(72, 96))){
		@Override
		public Boolean isArtifact(Range<Integer> segmentRange, List<Range<Integer>> artifactRanges){
			for (Range<Integer> range : artifactRanges) {
				try{
					segmentRange.intersection(range);
					return true;
				}catch(IllegalArgumentException exception){
				}
			}
			return false;
		}
	};
	
	private List<Range<Integer>> channels; 
	
	private ArtifactType(List<Range<Integer>>  channels){
		this.channels = channels;
	}
	
	public List<Range<Integer>> getChannels(){
		return channels;
	}
	
	public boolean isSuitableForType(int channel){
		for (Range<Integer> range : channels) {
			if(range.contains(channel)){
				return true;
			}
		}
		return false;
	}
	
	public Boolean isArtifact(Range<Integer> segmentRange, List<Range<Integer>> artifactRanges){
		if(disjunctiveIntersectionCondition(segmentRange, artifactRanges)){
			return false;
		}
		boolean reject = false;
		for (Range<Integer> range : artifactRanges) {
			try{
				Range<Integer> intersection = segmentRange.intersection(range);
				if(getRangeSize(intersection)>=getRangeSize(segmentRange)/5){
					return true;
				}
				reject = true;
			}catch(IllegalArgumentException exception){
				
			}
		}
		if(reject){
			return null;
		}
		return false;
	}

	private boolean disjunctiveIntersectionCondition(Range<Integer> segmentRange, List<Range<Integer>> artifactRanges) {
		return segmentRange.upperEndpoint()<artifactRanges.get(0).lowerEndpoint() || segmentRange.lowerEndpoint() > artifactRanges.get(artifactRanges.size()-1).upperEndpoint();
	}
	
	private Integer getRangeSize(Range<Integer> range){
		return range.upperEndpoint()-range.lowerEndpoint()+1;
	}
}
