package eegApp.helpers;

import java.util.ArrayList;
import java.util.List;

import eegApp.model.ArtefactType;
import eegApp.model.EEGSegment;
import eegApp.model.EEGTrial;

/**
 * This class generates segments from a trial. A segment is a piece of data
 * taken from values of a trial from a specific index
 * 
 * A sliding window method is used in order to generate the segments. First
 * segments represents first n values from trial, next segment will have data
 * starting from index STEP, next second will have data from index 2*STEP and so
 * on.
 * 
 * @author Tolas Ramona
 *
 */
public class EEGSegmentsGeneratorFromTrial {

	private int step;
	private int lengthOfSegment;

	public EEGSegmentsGeneratorFromTrial(int step, int lengthOfSegment) {
		this.step = step;
		this.lengthOfSegment = lengthOfSegment;
	}

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//TODO : add Elena's segmentation code
	public List<EEGSegment> generateSegmentsFromTrial(EEGTrial trial, int channelId, int subjectId) {
		List<Double> values = trial.getValues();
		List<EEGSegment> segments = new ArrayList<EEGSegment>();
		EEGSegment segment = new EEGSegment();
		segment.setArtefactType(ArtefactType.MUSCULAR);
		segment.setValues(new ArrayList<Double>(values.subList(1, 4)));
		segment.setNrTrialFromSample(1);
		segment.setChannelId(channelId);
		segment.setSubjectId(subjectId);
		segment.setId(trial.getEventCode() + "");
		segments.add(segment);

		EEGSegment segment2 = new EEGSegment();
		segment2.setArtefactType(ArtefactType.MUSCULAR);
		segment2.setValues(new ArrayList<Double>(values.subList(2, 5)));
		segment2.setNrTrialFromSample(2);
		segment2.setChannelId(channelId);
		segment2.setSubjectId(subjectId);
		segment2.setId(trial.getEventCode() + "+");
		segments.add(segment2);
		return segments;
	}

}
