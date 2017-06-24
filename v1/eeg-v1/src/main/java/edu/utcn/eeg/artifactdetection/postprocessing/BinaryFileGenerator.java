package edu.utcn.eeg.artifactdetection.postprocessing;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

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
		LinkedHashMap<Integer, Integer> segmentsType = reportParameters
				.getSegmentsType();
		for (int i = 0; i < segmentsType.size(); i++) {
			if (segmentsType.get(i) != 0) {
				segmentsTypeFileContent += i + ", 1" + "\n";
			} else {
				segmentsTypeFileContent += i + ", 0" + "\n";
			}
		}
		
		String filePath = OUTPUT_FILENAME + "statistics.csv";

		OutputFileWriter.getInstance().writeToFile(segmentsTypeFileContent, filePath);

		return filePath;
	}

}
