package edu.utcn.eeg.artifactdetection.postprocessing;

import java.util.Hashtable;
import java.util.List;

import edu.utcn.eeg.artifactdetection.helpers.OutputFileWriter;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;

public class ClasesFileGenerator extends FileGeneratorInterface {

	@Override
	public String outputStatistics(List<AbstractSegment> segments) {
		String segmentsTypeFileContent = "";
		OutputRaportParameters reportParameters = new OutputRaportParameters(
				segments);
		Hashtable<Integer, Integer> segmentsType = reportParameters
				.getSegmentsType();
		for (int i = 0; i < segmentsType.size(); i++) {
			segmentsTypeFileContent += i + ", " + segmentsType.get(i) + "\n";
		}

		OutputFileWriter fileWriter = new OutputFileWriter();
		String filePath = OUTPUT_FILENAME + "statistics.csv";

		OutputFileWriter.writeToFile(segmentsTypeFileContent, filePath);

		return filePath;
	}

}
