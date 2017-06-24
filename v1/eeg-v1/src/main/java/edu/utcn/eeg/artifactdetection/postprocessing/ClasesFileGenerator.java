package edu.utcn.eeg.artifactdetection.postprocessing;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import edu.utcn.eeg.artifactdetection.helpers.OutputFileWriter;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class ClasesFileGenerator extends AbstractFileGenerator {

	@Override
	public String outputStatistics(List<Segment> segments) {
		String segmentsTypeFileContent = "";
		OutputRaportParameters reportParameters = new OutputRaportParameters(
				segments);
		LinkedHashMap<Integer, Integer> overlappingSegmentsType = reportParameters
				.getOverlappingSegmentsType();

		segmentsTypeFileContent = "Channel " + segments.get(0).getChannelNr()
				+ " has " + reportParameters.getNoOfMuscularArtifacts()
				+ " muscular artifacts and "
				+ reportParameters.getNoOfOcularArtifacts()
				+ "ocular artifacts" + " from "
				+ overlappingSegmentsType.size() + " windows with artifacts.\n";

		segmentsTypeFileContent += "0 = brain signal, 1 = muscle artifact, 2 = ocular artifact \n";

		for (int i : overlappingSegmentsType.keySet()) {
			segmentsTypeFileContent += i + ", "
					+ overlappingSegmentsType.get(i) + "\n";
		}

		String filePath = OUTPUT_FILENAME + "statisticsClasses"
				+ segments.get(0).getChannelNr() + ".dat";

		OutputFileWriter.getInstance().writeToFile(segmentsTypeFileContent,
				filePath);

		return filePath;
	}
}
