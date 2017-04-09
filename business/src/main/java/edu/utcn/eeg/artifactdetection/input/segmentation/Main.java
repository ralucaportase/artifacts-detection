package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.util.List;

import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class Main {

	public static void main(String[] args) throws Exception{
	/*	System.out.println("Start");
		LoggerUtil.configure();
		Logger logger = LoggerUtil.logger(Main.class);
		FileProcessor fp = new FileProcessor();
		String fileName = Configuration.INPUT_FILES; 
		List<SegmentRepository> segmentRepositories = fp.parseDataDirectory(new File(fileName));
		
		for (SegmentRepository segmentRepository : segmentRepositories) {
			logger.info(segmentRepository.getName()+" "+segmentRepository.getSegments().size());
		}
		
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
		*/
		
		ArffExporter.export();
	
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
