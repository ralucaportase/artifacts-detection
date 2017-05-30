package edu.utcn.eeg.artifactdetection.preprocessing;

import java.util.List;
import java.util.Map;

import edu.utcn.eeg.artifactdetection.model.MultiChannelSegment;
import edu.utcn.eeg.artifactdetection.model.MultiChannelSegmentSelector;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.preprocessing.exception.FileReadingException;

public interface SegmentsGeneratorInterface {
	/**
	 * This method reads a binary files from a folder given by path that
	 * contains eeg files and process that files in order to obtain a list of
	 * segment of eeg signal for each channel.
	 * 
	 * @param path
	 *            The path to the folder nrChannel Number of eeg channel from
	 *            which the signal is used.
	 * @return A map that connects number of channel with list of segments
	 *         generated from binary file. Keys of map should be in range 1-128.
	 * @throws FileReadingException
	 *             with the correct error code
	 */
	Map<Integer, List<Segment>> generateSegments() throws FileReadingException;

	/**
	 * This method reads the coresponding binary files hold in folder given by
	 * path and returns a list of segments of multi-channel type from a region
	 * specified by the selector. It is assumed that in the folder specified,
	 * the files have names from which the channel that is stored in a specific
	 * file can be deduced.
	 * 
	 * @param path
	 *            Path to the folder
	 * @param selector
	 *            Represents the region from where the channels are chosen to be
	 *            put in the multi-channel segment
	 * @return A list of MultiChannelSegment containing eeg signal from channels
	 *         specified by selector.
	 * @throws A
	 *             FileReadingException with the correct error code
	 */
	List<MultiChannelSegment> generateMultisegments(String path, MultiChannelSegmentSelector selector)
			throws FileReadingException;

}
