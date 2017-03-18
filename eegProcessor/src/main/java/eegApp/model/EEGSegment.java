package eegApp.model;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * This class represents a piece of data taken from a trial that starts from an
 * index from data of a trial and has a length smaller than a trial.
 * 
 * @author Tolas Ramona
 *
 */
public class EEGSegment {
	
	@Id
	private String id;

	private List<Double> values;
	private int subjectId;
	private int channelId;
	private int nrTrialFromSample;
	private int startIndexInTrial;
	private int nrSegmentFromTrial;
	private ArtefactType artefactType;

	public ArtefactType getArtefactType() {
		return artefactType;
	}

	public void setArtefactType(ArtefactType artefactType) {
		this.artefactType = artefactType;
	}

	public int getNrSegmentFromTrial() {
		return nrSegmentFromTrial;
	}

	public void setNrSegmentFromTrial(int nrSegmentFromTrial) {
		this.nrSegmentFromTrial = nrSegmentFromTrial;
	}

	public List<Double> getValues() {
		return values;
	}

	public void setValues(List<Double> values) {
		this.values = values;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getNrTrialFromSample() {
		return nrTrialFromSample;
	}

	public void setNrTrialFromSample(int nrTrialFromSample) {
		this.nrTrialFromSample = nrTrialFromSample;
	}

	public int getStartIndexInTrial() {
		return startIndexInTrial;
	}

	public void setStartIndexInTrial(int startIndexInTrial) {
		this.startIndexInTrial = startIndexInTrial;
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
		result = prime * result + ((artefactType == null) ? 0 : artefactType.hashCode());
		result = prime * result + channelId;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + nrSegmentFromTrial;
		result = prime * result + nrTrialFromSample;
		result = prime * result + startIndexInTrial;
		result = prime * result + subjectId;
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		EEGSegment other = (EEGSegment) obj;
		if (artefactType != other.artefactType)
			return false;
		if (channelId != other.channelId)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nrSegmentFromTrial != other.nrSegmentFromTrial)
			return false;
		if (nrTrialFromSample != other.nrTrialFromSample)
			return false;
		if (startIndexInTrial != other.startIndexInTrial)
			return false;
		if (subjectId != other.subjectId)
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EEGSegment [id=" + id + ", values=" + values + ", subjectId=" + subjectId + ", channelId=" + channelId
				+ ", nrTrialFromSample=" + nrTrialFromSample + ", startIndexInTrial=" + startIndexInTrial
				+ ", nrSegmentFromTrial=" + nrSegmentFromTrial + ", artefactType=" + artefactType + "]";
	}
	
	

}
