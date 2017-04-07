package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import edu.utcn.eeg.artifactdetection.features.export.SegmentExporter;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class Main {

	public static void main(String[] args) throws Exception{
		
		LoggerUtil.configure();
		FileProcessor fp = new FileProcessor();
		String fileName = Configuration.INPUT_FILES; 
		List<SegmentRepository> segmentRepositories = fp.parseDataDirectory(new File(fileName));
		
		SegmentExporter.exportAll(segmentRepositories);
		
		DataBalancer dBalancer=new DataBalancer();
		List<AbstractSegment> trainSegments = dBalancer.undersample(findRepository(segmentRepositories, "Clean_Train"), findRepository(segmentRepositories, "Occular_Train"), findRepository(segmentRepositories, "Muscle_Train"));
		SegmentRepository trainSegmRepo = new SegmentRepository("TrainData");
		trainSegmRepo.setSegments(trainSegments);
		SegmentExporter.exportAll(Arrays.asList(trainSegmRepo));
		
		MultiChannelSegmentation multiChannelSegmentation = new MultiChannelSegmentation(segmentRepositories);
		multiChannelSegmentation.buildMultichannelSegments();
		List<SegmentRepository> segmentRepositories2 = multiChannelSegmentation.getSerializableStructures();
		SegmentExporter.exportAll(segmentRepositories2);
		
	}
	
	private static SegmentRepository findRepository(List<SegmentRepository>repositories, String name){
		for (SegmentRepository segmentRepository : repositories) {
			if(segmentRepository.getName().equals(name)){
				return segmentRepository;
			}
		}
		throw new IllegalArgumentException(name+" is not a repository name!");
	}

}
