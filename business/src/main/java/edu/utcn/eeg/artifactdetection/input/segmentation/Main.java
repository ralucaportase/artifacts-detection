package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.File;
import java.util.List;

import edu.utcn.eeg.artifactdetection.features.export.SegmentExporter;
import edu.utcn.eeg.artifactdetection.model.Configuration;

public class Main {

	public static void main(String[] args) throws Exception{
		
		
		LoggerUtil.configure();
		FileProcessor fp = new FileProcessor();
		String fileName = Configuration.INPUT_FILES; 
		List<SegmentRepository> segmentRepositories = fp.parseDataDirectory(new File(fileName));
		SegmentExporter.exportAll(segmentRepositories);
		
	}

}
