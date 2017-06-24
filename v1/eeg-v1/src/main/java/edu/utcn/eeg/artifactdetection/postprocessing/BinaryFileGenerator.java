package edu.utcn.eeg.artifactdetection.postprocessing;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import java_cup.internal_error;
import edu.utcn.eeg.artifactdetection.helpers.OutputFileWriter;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class BinaryFileGenerator extends AbstractFileGenerator {

	@Override
	public String outputStatistics(List<Segment> segments) {
		String segmentsTypeFileContent = "";
		OutputRaportParameters reportParameters = new OutputRaportParameters(
				segments);
		LinkedHashMap<Integer, Integer> overlappingSegmentsType = reportParameters
				.getOverlappingSegmentsType();

		int noOfArtifacts = reportParameters.getNoOfMuscularArtifacts()
				+ reportParameters.getNoOfOcularArtifacts();
		segmentsTypeFileContent = "Channel " + segments.get(0).getChannelNr()
				+ " has " + noOfArtifacts + " from "
				+ overlappingSegmentsType.size() + " windows with artifacts.\n";

		for (int i : overlappingSegmentsType.keySet()) {
			if (overlappingSegmentsType.get(i) != 0) {
				segmentsTypeFileContent += i + ", Artefact" + "\n";
			} else {
				segmentsTypeFileContent += i + ", Brain" + "\n";
			}
		}

		String filePath = OUTPUT_FILENAME + "statisticsBinary"
				+ segments.get(0).getChannelNr() + ".dat";

		OutputFileWriter.getInstance().writeToFile(segmentsTypeFileContent,
				filePath);

		return filePath;
	}
}
