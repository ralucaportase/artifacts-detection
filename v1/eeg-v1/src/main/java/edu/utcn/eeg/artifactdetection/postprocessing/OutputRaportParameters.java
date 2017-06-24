package edu.utcn.eeg.artifactdetection.postprocessing;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import java_cup.internal_error;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class OutputRaportParameters {

	private int noOfOcularArtifacts;
	private int noOfMuscularArtifacts;
	private LinkedHashMap<Integer, Integer> segmentsType; // hashTabel with
															// segmentsNumber
															// and their status
	private LinkedHashMap<Integer, Segment> orderedSegments; // hashTabel with
																// segmentsNumber
																// and the
																// segment
																// itself

	public OutputRaportParameters(List<Segment> segments) {
		this.noOfOcularArtifacts = 0;
		this.noOfMuscularArtifacts = 0;

		segmentsType = new LinkedHashMap<Integer, Integer>();
		orderedSegments = new LinkedHashMap<Integer, Segment>();

		for (Segment segment : segments) {
			if (segment.getIterIdx() == 0) {
				int index = segment.getInitIdx();
				orderedSegments.put(index, segment);
				if (segment.getCorrectType() == ResultType.MUSCLE) {
					this.noOfMuscularArtifacts++;
					this.segmentsType.put(index, 1);
				} else if (segment.getCorrectType() == ResultType.OCCULAR) {
					this.noOfOcularArtifacts++;
					this.segmentsType.put(index, 2);
				} else
					this.segmentsType.put(index, 0);
			}
		}

	}

	public LinkedHashMap<Integer, Integer> getSegmentsType() {
		return this.segmentsType;
	}

	public LinkedHashMap<Integer, Segment> getOrderedSegments() {
		return this.orderedSegments;
	}

	public int getNoOfOcularArtifacts() {
		return this.noOfOcularArtifacts;
	}

	public int getNoOfMuscularArtifacts() {
		return this.noOfMuscularArtifacts;
	}

}
