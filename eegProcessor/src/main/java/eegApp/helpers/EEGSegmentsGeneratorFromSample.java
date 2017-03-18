package eegApp.helpers;

import java.util.ArrayList;
import java.util.List;

import eegApp.model.EEGSample;
import eegApp.model.EEGSegment;
import eegApp.model.EEGTrial;

/**
 * This class is used for generating segments from an entire sample. It uses
 * EEGSegmentsGeneratorFromTrial for generating segments from all trials of a
 * sample.
 * 
 * @author Tolas Ramona
 *
 */
public class EEGSegmentsGeneratorFromSample {

	private int step;
	private int lengthOfSegment;

	public EEGSegmentsGeneratorFromSample(int step, int lengthOfSegment) {
		this.step = step;
		this.lengthOfSegment = lengthOfSegment;
	}

	private EEGSegmentsGeneratorFromTrial segmentGeneratorFromTrial = new EEGSegmentsGeneratorFromTrial(step,
			lengthOfSegment);

	public List<EEGSegment> generateSegments(EEGSample sample) {
		List<EEGSegment> segments = new ArrayList<EEGSegment>();
		List<EEGTrial> trialsFromSample = sample.getTrials();
		for (EEGTrial trial : trialsFromSample) {
			List<EEGSegment> segmentsFromCurrentTrial = segmentGeneratorFromTrial.generateSegmentsFromTrial(trial,
					sample.getChannelId(), sample.getSubjectId());
			segments.addAll(segmentsFromCurrentTrial);
		}
		return segments;
	}

}
