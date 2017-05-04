package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import edu.utcn.eeg.artifactdetection.builders.FeatureBuilder;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.MultiChannelSegment;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.model.SegmentKey;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class MultiChannelSegmentation {
	
	private Logger logger = LoggerUtil.logger(MultiChannelSegmentation.class);
	private List<SegmentRepository> segmentRepositories;
	private Multimap<SegmentKey, Segment> multimap;
//	private SegmentRepository occularStructTrain;
//	private SegmentRepository muscleStructTrain;
//	private SegmentRepository brainStructTrain;
//	private SegmentRepository occularStructTest;
//	private SegmentRepository muscleStructTest;
//	private SegmentRepository brainStructTest;
//	private SegmentRepository occularStructEval;
//	private SegmentRepository muscleStructEval;
//	private SegmentRepository brainStructEval;

	public MultiChannelSegmentation(List<SegmentRepository> segmentRepositories){
		this.segmentRepositories = segmentRepositories;
		multimap = ArrayListMultimap.create();
//		brainStructEval = new SegmentRepository("Clean_Multi_Eval");
//		muscleStructEval = new SegmentRepository("Muscle_Multi_Eval");
//		occularStructEval = new SegmentRepository("Occular_Multi_Eval");
//		brainStructTest = new SegmentRepository("Clean_Multi_Test");
//		muscleStructTest = new SegmentRepository("Muscle_Multi_Test");
//		occularStructTest = new SegmentRepository("Occular_Multi_Test");
//		brainStructTrain = new SegmentRepository("Clean_Multi_Train");
//		muscleStructTrain = new SegmentRepository("Muscle_Multi_Train");
//		occularStructTrain = new SegmentRepository("Occular_Multi_Train");
		createMultiChannelSegments();
	}
	
	private void createMultiChannelSegments(){
		for (SegmentRepository segmentRepository : segmentRepositories) {
			List<AbstractSegment> segments = segmentRepository.getSegments();
			for (AbstractSegment abstractSegment : segments) {
				if(abstractSegment instanceof Segment){
					Segment segment = (Segment)abstractSegment;
					SegmentKey key = segment.getMultiChannelKey();
					multimap.put(key, segment);
				}
			}
		}
	}
	
	
	public void buildMultichannelSegments(){
		Set<SegmentKey> keys = multimap.keySet();
		int trainSize = keys.size()*(100-Configuration.TEST_PROPORTION-Configuration.EVAL_PROPORTION)/100;
		int testMaxIndex = trainSize + keys.size()*(Configuration.TEST_PROPORTION)/100 ;
		MultiChannelFeaturesExtractor multiChannelFeaturesExtractor = new MultiChannelFeaturesExtractor();
		int i=0;
		logger.info("Build multichannel segments. Train size: "+trainSize+" Test size: "+(testMaxIndex-trainSize));
		for (SegmentKey segmentKey : keys) {
			i++;
			List<Segment> segments = new ArrayList<>(multimap.get(segmentKey));
			if(!validateSegments(segments)){
				logger.error("Not all segments in a multi channel have the same correct type, iteration index or start index!");
			}
			MultiChannelSegment segment = new MultiChannelSegment(segments);
			segment.setCorrectType(segments.get(0).getCorrectType());
			segment.setFeatures(computeFeatures(segments));
			segment.setInitIdx(segments.get(0).getInitIdx());
			segment.setIterIdx(segments.get(0).getIterIdx());
//			int type = 3; 
//			if(i<trainSize){
//				type = 1;
//			}else if(i<testMaxIndex){
//				type = 2;
//			}
			multiChannelFeaturesExtractor.setMultiChannelFeatureForAllSegments(segment);
			//addToSerializableStructure(segment,type);
		}
	}
	
	private Feature[] computeFeatures(List<Segment> segments){
		Feature[] features = FeatureBuilder.createStandardFeaturesInstances();
		for (Feature feat : features) {
			feat.setValue(getMeanValue(feat, segments));
		}
		return features;
	}
	
	private Double getMeanValue(Feature feature, List<Segment> segments){
		double val = 0;
		for (Segment segment : segments) {
			if(segment.getFeatureValueForFeature(feature.getFeature())!=null){
				val+=segment.getFeatureValueForFeature(feature.getFeature());
			}
		}
		return val/segments.size();
	}
	
	private boolean validateSegments(List<Segment>segments){
		ResultType resultType = segments.get(0).getCorrectType();
		int initIdx = segments.get(0).getInitIdx();
		int iterIdx = segments.get(0).getIterIdx();
		for (Segment segment : segments) {
			if(!segment.getCorrectType().equals(resultType)){
				return false;
			}
			if(segment.getInitIdx()!=initIdx){
				return false;
			}
			if(segment.getIterIdx()!=iterIdx){
				return false;
			}
		}
		return true;
	}
	
//	
//	private void addToSerializableStructure(MultiChannelSegment segment, int type){
//		switch (segment.getCorrectType()) {
//		case OCCULAR:
//			switch (type) {
//			case 1:
//				occularStructTrain.addSegment(segment);
//				break;
//			case 2:
//				occularStructTest.addSegment(segment);
//				break;
//			default:
//				occularStructEval.addSegment(segment);
//				break;
//			}			
//			break;
//		case MUSCLE: 
//			switch (type) {
//			case 1:
//				muscleStructTrain.addSegment(segment);
//				break;
//			case 2:
//				muscleStructTest.addSegment(segment);
//				break;
//			default:
//				muscleStructEval.addSegment(segment);
//				break;
//			}		
//			break;
//		default:
//			switch (type) {
//			case 1:
//				brainStructTrain.addSegment(segment);
//				break;
//			case 2:
//				brainStructTest.addSegment(segment);
//				break;
//			default:
//				brainStructEval.addSegment(segment);
//				break;
//			}		
//			break;
//		}
//	}
//	
//	public List<SegmentRepository> getSerializableStructures(){
//		return Lists.newArrayList(occularStructTrain,occularStructEval,occularStructTest,muscleStructTrain,muscleStructEval,muscleStructTest,brainStructTrain,brainStructEval,brainStructTest);
//	}
		
}
