package eegApp.helpers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import eegApp.model.EEGSample;
import eegApp.model.EEGTrial;

/**
 * This class creates an object of type EEGSample from a list of values,
 * timestamp and event codes. It is used in class
 * BynaryFileToEegSampleTransformer as a helper for performing the
 * transformation.
 * 
 * @author Tolas Ramona
 *
 */
public class FileToObjectTransformerHelper {

	private static final Logger LOGGER = Logger.getLogger(FileToObjectTransformerHelper.class);

	private List<Integer> eventCodes;
	private List<Integer> eventTimestamps;
	private List<Float> values;

	public FileToObjectTransformerHelper(List<Integer> eventCodes, List<Integer> eventTimestamps, List<Float> values) {
		this.eventCodes = eventCodes;
		this.eventTimestamps = eventTimestamps;
		this.values = values;
	}

	public EEGSample performTransformationToEEGSampleObject() {
		EEGSample sample;
		int lengthOfEventCodes = eventCodes.size();
		int lengthOFEventTimestamp = eventTimestamps.size();

		if (lengthOfEventCodes != lengthOFEventTimestamp) {

			LOGGER.info("Event codes and timestamps doesn't have the same length!");
			LOGGER.info("Length of event codes is " + lengthOfEventCodes + " and length of timestamps is "
					+ lengthOFEventTimestamp + ".");
			sample = null;

		} else {

			List<EEGTrial> trials = new ArrayList<EEGTrial>();
			int infIndex = 0;
			for (int i = 0; i < lengthOfEventCodes; i++) {
				int eventCode = eventCodes.get(i);
				int supIndex = eventTimestamps.get(i);
				List<Double> valuesOfCurrentTrial = new ArrayList<Double>();
				for (int j = infIndex; j < supIndex; j++) {
					double value = values.get(j);
					valuesOfCurrentTrial.add(value);
				}

				EEGTrial trial = new EEGTrial();
				trial.setEventCode(eventCode);
				trial.setValues(valuesOfCurrentTrial);

				trials.add(trial);

				infIndex = supIndex + 1;

			}

			sample = new EEGSample();
			sample.setTrials(trials);

		}

		return sample;
	}

}
