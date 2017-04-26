package eeg.eeg_processor.classifier;

import java.util.List;

import edu.utcn.eeg.artifactdetection.model.*;

public interface Classifier {
	/**
	 * This method assigns a label to each segment from the list.
	 * 
	 * @param segments
	 *            A list on unlabeled segments.
	 * @return A list with Segments, all containing label set.
	 */
	List<AbstractSegment> classifySegments(List<AbstractSegment> segments);
}
