package edu.utcn.eeg.artifactdetection.postprocessing;

import java.util.Hashtable;
import java.util.List;

import edu.utcn.eeg.artifactdetection.model.AbstractSegment;

public class ClasesFileGenerator implements FileGeneratorInterface {

	public String generateFileFromSegment(List<AbstractSegment> segments) {
		String segmentsTypeFile = "";
		OutputRaportParameters reportParameters = new OutputRaportParameters(
				segments);
		Hashtable<Integer, Integer> segmentsType = reportParameters
				.getSegmentsType();
		for (int i = 0; i < segmentsType.size(); i++) {
			segmentsTypeFile += i + ", " + segmentsType.get(i) + "\n";
		}
		return segmentsTypeFile;
	}

}
