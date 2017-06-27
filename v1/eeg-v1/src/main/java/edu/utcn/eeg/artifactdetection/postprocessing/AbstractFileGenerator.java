package edu.utcn.eeg.artifactdetection.postprocessing;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import edu.utcn.eeg.artifactdetection.helpers.OutputFileWriter;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;

public abstract class AbstractFileGenerator {

	protected static final String OUTPUT_FILENAME = Configuration.PROJECT_PATH
			+ "/output/";

	/**
	 * This method writes a binary file containing the eeg signal hold in
	 * overlapping segments.
	 * 
	 * @param segments
	 *            A list of overlapping segments containing the eeg signal.
	 * @return Path to the file generated in current system.
	 */
	public String generateFileFromSegment(List<Segment> segments) {
		String cleanFileContent = "";
		double values[];
		int index = 0;
		OutputRaportParameters reportParameters = new OutputRaportParameters(
				segments);
		LinkedHashMap<Integer, Integer> segmentsType = reportParameters
				.getSegmentsType();
		LinkedHashMap<Integer, Segment> orderedSegments = reportParameters
				.getOrderedSegments();

		for (int i : segmentsType.keySet()) {
			index++;
			if (index == segmentsType.size()) {
				// remove last zeros
				if (segmentsType.get(i) == 0) {
					values = orderedSegments.get(i).getValues();
					for (int j = 0; j < values.length; j++) {
						if (values[j] != 0)
							cleanFileContent += values[j] + " ";
					}
				}
			}

			else if (segmentsType.get(i) == 0) { // clean
				// signal
				values = orderedSegments.get(i).getValues();
				for (int j = 0; j < values.length; j++) {
					cleanFileContent += values[j] + " ";
				}
			}
		}

		String filePath = OUTPUT_FILENAME + "cleanEEG"
				+ segments.get(0).getChannelNr() + ".dat";

		OutputFileWriter.getInstance().writeToFile(cleanFileContent, filePath);

		return filePath;
	}

	/**
	 * Method for writing to a file the statistics about every segment
	 * 
	 * @param segments
	 *            A list of overlapping segments containing the eeg signal.
	 * @return Path to the file generated in current system.
	 */
	public abstract String outputStatistics(List<Segment> segments);

}
