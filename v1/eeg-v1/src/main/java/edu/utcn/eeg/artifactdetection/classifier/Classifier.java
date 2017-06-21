package edu.utcn.eeg.artifactdetection.classifier;

import java.util.List;

import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Segment;

public interface Classifier {
	/**
	 * This method assigns a label to each segment from the list.
	 * 
	 * @param segments
	 *            A list on unlabeled segments.
	 * @return A list with Segments, all containing label set.
	 */
	List<Segment> classifySegments(List<Segment> segments);
	}
