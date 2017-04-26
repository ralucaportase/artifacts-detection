package eeg.eeg_processor.preprocessing;

import java.util.List;

import edu.utcn.eeg.artifactdetection.model.AbstractSegment;

public interface SegmentsGeneratorInterface {
	/**
	 * This method reads a file from a path and process that file in order to
	 * obtain a list of segment of eeg signal.
	 * 
	 * @param path
	 *            The path to the .bin file;
	 * @return A list of Segments generated from binary file.
	 */
	List<AbstractSegment> generateSegments(String path);

}
