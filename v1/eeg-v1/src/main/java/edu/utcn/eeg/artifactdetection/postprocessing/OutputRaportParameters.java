package edu.utcn.eeg.artifactdetection.postprocessing;

import java.util.Hashtable;
import java.util.List;

import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class OutputRaportParameters {

	private int noOfOcularArtifacts;
	private int noOfMuscularArtifacts;
	private Hashtable<Integer, Integer> segmentsType; //hashTabel with segmentsNumber and their status
	private Hashtable<Integer, Segment> orderedSegments; //hashTabel with segmentsNumber and the segment itself

	public OutputRaportParameters(List<Segment> segments) {
		this.noOfOcularArtifacts = 0;
		this.noOfMuscularArtifacts = 0;
		
		segmentsType = new Hashtable<Integer, Integer>();

		for (AbstractSegment segment : segments) {
			orderedSegments.put(segment.getIterIdx(), (Segment)segment);
			if (segment.getCorrectType() == ResultType.MUSCLE) {
				this.noOfMuscularArtifacts++;
				this.segmentsType.put(segment.getIterIdx(), 1);
			} else if (segment.getCorrectType() == ResultType.OCCULAR) {
				this.noOfOcularArtifacts++;
				this.segmentsType.put(segment.getIterIdx(), 2);
			}
			else this.segmentsType.put(segment.getIterIdx(), 0);
		}

	}
	
	public Hashtable<Integer, Integer> getSegmentsType(){
		return this.segmentsType;
	}
	
	public Hashtable<Integer, Segment> getOrderedSegments(){
		return this.orderedSegments;
	}

	public int getNoOfOcularArtifacts() {
		return this.noOfOcularArtifacts;
	}

	public int getNoOfMuscularArtifacts() {
		return this.noOfMuscularArtifacts;
	}

}
