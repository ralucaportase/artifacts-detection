package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
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
	private SegmentRepository occularStruct;
	private SegmentRepository muscleStruct;
	private SegmentRepository brainStruct;
	private SegmentRepository occularStructTest;
	private SegmentRepository muscleStructTest;
	private SegmentRepository brainStructTest;

	public MultiChannelSegmentation(List<SegmentRepository> segmentRepositories){
		this.segmentRepositories = segmentRepositories;
		multimap = ArrayListMultimap.create();
		brainStruct = new SegmentRepository("Clean");
		muscleStruct = new SegmentRepository("Muscle");
		occularStruct = new SegmentRepository("Occular");
		brainStructTest = new SegmentRepository("Clean_Test");
		muscleStructTest = new SegmentRepository("Muscle_Test");
		occularStructTest = new SegmentRepository("Occular_Test");
	}
	
	public void createMultiChannelSegments(){
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
		int testSize = keys.size()*Configuration.TEST_PROPORTION/100;
		int trainSize = keys.size() - testSize;
		int i=0;
		logger.info("Build multichannel segments. Train size: "+trainSize+" Test size: "+testSize);
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
			addToSerializableStructure(segment, i>trainSize);
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
			val+=segment.getFeatureValueForFeature(feature.getFeature());
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
	
	
	private void addToSerializableStructure(MultiChannelSegment segment, boolean test){
		switch (segment.getCorrectType()) {
		case OCCULAR:
			if(test){
				occularStructTest.addSegment(segment);
			}else{
				occularStruct.addSegment(segment);
			}
			break;
		case MUSCLE: 
			if(test){
				muscleStructTest.addSegment(segment);
			}else{
				muscleStruct.addSegment(segment);
			}
			break;
		default:
			if(test){
				brainStructTest.addSegment(segment);
			}else{
				brainStruct.addSegment(segment);
			}
		}
	}
	
	public List<SegmentRepository> getSerializableStructures(){
		return Lists.newArrayList(occularStruct,occularStructTest,muscleStruct,muscleStructTest,brainStruct,brainStructTest);
	}
}
