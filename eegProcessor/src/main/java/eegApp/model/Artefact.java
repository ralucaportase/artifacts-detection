package eegApp.model;

/**
 * This class is for modeling the noise from the EEG signal that is called an
 * artefact.
 * 
 * @author Tolas Ramona
 *
 */
public class Artefact {

	private ArtefactType type;
	private ArtefactDetectionWay detectionWay;

	public ArtefactType getType() {
		return type;
	}

	public void setType(ArtefactType type) {
		this.type = type;
	}

	public ArtefactDetectionWay getDetectionWay() {
		return detectionWay;
	}

	public void setDetectionWay(ArtefactDetectionWay detectionWay) {
		this.detectionWay = detectionWay;
	}

	@Override
	public String toString() {
		return "Artefact [type=" + type + ", detectionWay=" + detectionWay
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((detectionWay == null) ? 0 : detectionWay.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Artefact other = (Artefact) obj;
		if (detectionWay != other.detectionWay)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
