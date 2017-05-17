package edu.utcn.eeg.artifactdetection.postprocessing;

import java.util.List;

import edu.utcn.eeg.artifactdetection.model.AbstractSegment;

public interface FileGeneratorInterface {
	/**
	 * This method writes a binary file containing the eeg signal hold in
	 * overlapping segments.
	 * 
	 * @param segments
	 *            A list of overlapping segments containing the eeg signal.
	 * @return Path to the file generated in current system.
	 */
	String generateFileFromSegment(List<AbstractSegment> segments);

	/**
	 * Method for writing to a file the statistics about every segment
	 * 
	 * @param segments
	 *            A list of overlapping segments containing the eeg signal.
	 * @return Path to the file generated in current system.
	 */
	String outputStatistics(List<AbstractSegment> segments);

}
