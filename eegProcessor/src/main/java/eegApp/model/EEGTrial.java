package eegApp.model;

import java.util.List;

/**
 * A trial represents a set of points ( values of tension measured with
 * electrodes from the EEG headset.
 * 
 * @author Tolas Ramona
 *
 */
public class EEGTrial {

	private List<Double> values;
	private int eventCode;
	/**
	 * If the artifact is null, trial is not affected by noise, otherwise an
	 * Artifact affects the EEG signal.
	 */
	private Artefact artefact;

	public List<Double> getValues() {
		return values;
	}

	public void setValues(List<Double> values) {
		this.values = values;
	}

	public int getEventCode() {
		return eventCode;
	}

	public void setEventCode(int eventCode) {
		this.eventCode = eventCode;
	}

	public Artefact getArtefact() {
		return artefact;
	}

	public void setArtefact(Artefact artefact) {
		this.artefact = artefact;
	}

	@Override
	public String toString() {
		String reprezentation = "EEGTrial with event code " + eventCode + System.getProperty("line.separator");
		if (values != null) {
			int numberOfValues = values.size();

			for (int i = 0; i < numberOfValues; i++) {
				reprezentation = reprezentation + System.getProperty("line.separator") + " " + i + "-" + values.get(i);
			}
		}
		reprezentation += System.getProperty("line.separator") + " Affected by artefact " + artefact;
		return reprezentation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artefact == null) ? 0 : artefact.hashCode());
		result = prime * result + eventCode;
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
		EEGTrial other = (EEGTrial) obj;
		if (artefact == null) {
			if (other.artefact != null)
				return false;
		} else if (!artefact.equals(other.artefact))
			return false;
		if (eventCode != other.eventCode)
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

}
