package eegApp.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class EEGSegmentsHolderFromSample {
	
	@Id
	private String id;
	
	private List<EEGSegment> segments;
	private int channelId;
	private int subjectId;
	private int step;
	private int lengthOfSegments;
	public List<EEGSegment> getSegments() {
		return segments;
	}
	public void setSegments(List<EEGSegment> segments) {
		this.segments = segments;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getLengthOfSegments() {
		return lengthOfSegments;
	}
	public void setLengthOfSegments(int lengthOfSegments) {
		this.lengthOfSegments = lengthOfSegments;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + channelId;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + lengthOfSegments;
		result = prime * result + ((segments == null) ? 0 : segments.hashCode());
		result = prime * result + step;
		result = prime * result + subjectId;
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
		EEGSegmentsHolderFromSample other = (EEGSegmentsHolderFromSample) obj;
		if (channelId != other.channelId)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lengthOfSegments != other.lengthOfSegments)
			return false;
		if (segments == null) {
			if (other.segments != null)
				return false;
		} else if (!segments.equals(other.segments))
			return false;
		if (step != other.step)
			return false;
		if (subjectId != other.subjectId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "EEGSegmentsHolderFromSample [id=" + id + ", segments=" + segments + ", channelId=" + channelId
				+ ", subjectId=" + subjectId + ", step=" + step + ", lengthOfSegments=" + lengthOfSegments + "]";
	}
	
	
	

}
