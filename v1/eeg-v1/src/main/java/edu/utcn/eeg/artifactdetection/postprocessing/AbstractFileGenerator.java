package edu.utcn.eeg.artifactdetection.postprocessing;

import java.util.Hashtable;
import java.util.List;

import edu.utcn.eeg.artifactdetection.helpers.OutputFileWriter;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;
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
	String generateFileFromSegment(List<AbstractSegment> segments){
		String cleanFileContent = "";
		double values[];
		OutputRaportParameters reportParameters = new OutputRaportParameters(
				segments);
		Hashtable<Integer, Integer> segmentsType = reportParameters
				.getSegmentsType();
		Hashtable<Integer, Segment> orderedSegments = reportParameters
				.getOrderedSegments();
		for (int i = 0; i < segmentsType.size(); i++) {
			if (segmentsType.get(i) == 0) { // clean signal
				values = orderedSegments.get(i).getValues();
				for (int j = 0; i < values.length; j++) {
					cleanFileContent += values[j] + " ";
				}
			}
		}

		String filePath = OUTPUT_FILENAME + "cleanEEG.dat";

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
	abstract String outputStatistics(List<AbstractSegment> segments);

}
