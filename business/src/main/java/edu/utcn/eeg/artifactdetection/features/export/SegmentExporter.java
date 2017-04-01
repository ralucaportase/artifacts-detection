package edu.utcn.eeg.artifactdetection.features.export;

import java.util.List;

import org.apache.log4j.Logger;

import edu.utcn.eeg.artifactdetection.input.segmentation.LoggerUtil;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class SegmentExporter {
	
	private static Logger logger = LoggerUtil.logger(SegmentExporter.class);


	public static void exportAll(List<SegmentRepository> segmentRepositories){
		for (SegmentRepository segmentRepository : segmentRepositories) {
			SegmentSerializer.serialize(segmentRepository, Configuration.RESULTS_PATH);
			logger.info("Serialized: "+segmentRepository);

		}
	}
}
