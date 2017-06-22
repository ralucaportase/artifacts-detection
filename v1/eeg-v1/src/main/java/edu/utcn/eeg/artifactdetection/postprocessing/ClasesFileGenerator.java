package edu.utcn.eeg.artifactdetection.postprocessing;

import java.util.Hashtable;
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
		Hashtable<Integer, Integer> segmentsType = reportParameters
				.getSegmentsType();
		for (int i = 0; i < segmentsType.size(); i++) {
			segmentsTypeFileContent += i + ", " + segmentsType.get(i) + "\n";
		}

		String filePath = OUTPUT_FILENAME + "statistics.csv";

		OutputFileWriter.getInstance().writeToFile(segmentsTypeFileContent, filePath);

		return filePath;
	}

	

}
