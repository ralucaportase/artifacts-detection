package edu.utcn.eeg.artifactdetection.model;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Range;

public enum Region {

	A(Arrays.asList(Range.closed(1, 32))),
	B(Arrays.asList(Range.closed(33, 64))),
	C(Arrays.asList(Range.closed(65, 96))),
	D(Arrays.asList(Range.closed(97, 128)));
	
	private List<Range<Integer>>  channels;
	
	private Region(List<Range<Integer>>  channels){
		this.channels = channels;
	}
	

	public List<Range<Integer>> getChannels(){
		return channels;
	}
	
	public static Region getRegionByChannel(int channel){
		for (Region region : Region.values()) {
			for (Range<Integer> range : region.getChannels()) {
				if(range.contains(channel)){
					return region;
				}
			}
		}
		return null;
	}
	
}
