package eegApp.model;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * This class is the mapping of the file with EEG trials : it contains more EEG
 * trials and metadata asociated with the file.
 * 
 * @author Tolas Ramona
 *
 */

public class EEGSample {

	@Id
	private String id;
	private int subjectId;
	private int channelId;
	private List<EEGTrial> trials;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<EEGTrial> getTrials() {
		return trials;
	}

	public void setTrials(List<EEGTrial> trials) {
		this.trials = trials;
	}

	@Override
	public String toString() {
		String reprezentation = "EEGSample "
				+ System.getProperty("line.separator");
		if (trials != null) {
			int numberOfTrials = trials.size();

			for (int i = 0; i < numberOfTrials; i++) {
				reprezentation = reprezentation + trials.get(i)
						+ System.getProperty("line.separator");
			}
		}
		return reprezentation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + channelId;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + subjectId;
		result = prime * result + ((trials == null) ? 0 : trials.hashCode());
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
		EEGSample other = (EEGSample) obj;
		if (channelId != other.channelId)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (subjectId != other.subjectId)
			return false;
		if (trials == null) {
			if (other.trials != null)
				return false;
		} else if (!trials.equals(other.trials))
			return false;
		return true;
	}

	

}
