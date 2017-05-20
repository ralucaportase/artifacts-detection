package edu.utcn.eeg.artifactdetection.postprocessing;

import java.util.List;
import java.util.Map;

/**
 * This interface is used for reconstructing all the binary files of a sample (128 channels).
 * @author Tolas Ramona
 *
 */
import edu.utcn.eeg.artifactdetection.model.Segment;

public interface SampleFilesGenerator {
	/**
	 * This method reconstructs from a map that connects number of channel with
	 * a list of overlapping segments the coresponding binary files.
	 *
	 * @return A string representing path to the folder containing the files.
	 */
	String generateSample(Map<Integer, List<Segment>> segments);

}
