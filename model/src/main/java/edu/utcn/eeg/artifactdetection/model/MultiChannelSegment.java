package edu.utcn.eeg.artifactdetection.model;

import java.util.ArrayList;
import java.util.List;

public class MultiChannelSegment extends AbstractSegment{

	private static final long serialVersionUID = 1L;
	private List<Segment> segments; 
	
	public MultiChannelSegment(List<Segment> segments){
		this.segments = segments;
		this.correctType = setCorrectType();
	}

	public List<Segment> getSegments() {
		return this.segments;
	}

	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}

	public List<Segment> getRegionalSegment(Segment segment)
	{
		SegmentKey key = segment.getMultiChannelKey();
		List<Segment> otherSegments = new ArrayList<>();
		for (Segment segment1 : this.getSegments())
		{
			if (!segment.equals(segment1))
			{
				SegmentKey key1 = segment1.getMultiChannelKey();
				if (key.equals(key1))
				{
					otherSegments.add(segment1);
				}
			}
		}
		return otherSegments;
	}

	private ResultType setCorrectType()
	{
		for (Segment segment : this.segments)
		{
			if (ResultType.OCCULAR.equals(segment.getCorrectType()))
			{
				return ResultType.OCCULAR;
			}
			if (ResultType.MUSCLE.equals(segment.getCorrectType()))
			{
				return ResultType.MUSCLE;
			}
		}
		return ResultType.BRAIN_SIGNAL;
	}
	
}
