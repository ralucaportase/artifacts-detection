package edu.utcn.eeg.artifactdetection.builders;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import edu.utcn.eeg.artifactdetection.features.FeatureExtractor;
import edu.utcn.eeg.artifactdetection.input.segmentation.LoggerUtil;
import edu.utcn.eeg.artifactdetection.labels.ArtifactsLabelsExtractor;
import edu.utcn.eeg.artifactdetection.model.ArtifactType;
import edu.utcn.eeg.artifactdetection.model.ArtifactsStructure;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class StructureBuilder {
	
	private Logger logger = LoggerUtil.logger(StructureBuilder.class);

	private Map<ArtifactType, ArtifactsStructure> artifactsStructures;
	private SegmentRepository occularStruct;
	private SegmentRepository muscleStruct;
	private SegmentRepository brainStruct;
	private SegmentRepository occularStructTest;
	private SegmentRepository muscleStructTest;
	private SegmentRepository brainStructTest;

	public StructureBuilder() {
		ArtifactsLabelsExtractor artifactsLabelsExtractor = new ArtifactsLabelsExtractor();
		artifactsStructures = artifactsLabelsExtractor.parseLabelsDirectory(Configuration.RESOURCES_PATH+"/Labels");
		//dataGen = new DataGeneratorForDecisionTree();
		brainStruct = new SegmentRepository("Clean");
		muscleStruct = new SegmentRepository("Muscle");
		occularStruct = new SegmentRepository("Occular");
		brainStructTest = new SegmentRepository("Clean_Test");
		muscleStructTest = new SegmentRepository("Muscle_Test");
		occularStructTest = new SegmentRepository("Occular_Test");
	}

	public void buildDataStructures(double[] data, int iter, int index, int channel, boolean test) {
		ResultType resultType = computeCorrectType(index, channel);
		if (resultType != null) {
			Segment segment = createSegment(data, iter, index, channel, resultType);
			//dataGen.writeSegment(segment);
			addToSerializableStructure(segment, test);
			logger.info(segment);
		}
	}

	private Segment createSegment(double[] data, int iter, int index, int channel, ResultType resultType) {
		Segment segment = new Segment(data);
		segment.setIterIdx(iter);
		segment.setChannelNr(channel);
		segment.setInitIdx(index);
		segment.setFeatures(computeFeaturesForSegment(data));
		segment.setCorrectType(resultType);
		return segment;
	}

	private Feature[] computeFeaturesForSegment(double[] data) {
		Feature[] features = FeatureBuilder.createStandardFeaturesInstances();
		for (Feature feat : features) {
			if (FeatureExtractor.getFeatureValue(feat.getFeature(), data) == null) {
				logger.error("Error with the feature extraction! in StructureBuilder[computeFeaturesForSegment]");
				return null;
			}
			feat.setValue(FeatureExtractor.getFeatureValue(feat.getFeature(), data));
		}
		return features;
	}

	private ResultType computeCorrectType(int startIndex, int channel) {
		int endIndex = startIndex + Configuration.WINDOW_SIZE;
		Range<Integer> segmentRange = Range.closed(startIndex, endIndex);
		if (ArtifactType.REJECT.isSuitableForType(channel)) {
			ArtifactsStructure structure = artifactsStructures.get(ArtifactType.REJECT);
			if (ArtifactType.REJECT.isArtifact(segmentRange, structure.getRanges()) == true) {
				return null;
			}
		}
		if (ArtifactType.MUSCLE_C.isSuitableForType(channel)) {
			ArtifactsStructure structure = artifactsStructures.get(ArtifactType.MUSCLE_C);
			Boolean result = ArtifactType.MUSCLE_C.isArtifact(segmentRange, structure.getRanges());
			if(result == null){
				return null;
			}
			if (result) {
				return ResultType.MUSCLE;
			}
		}
		if (ArtifactType.OCCULAR.isSuitableForType(channel)) {
			ArtifactsStructure structure = artifactsStructures.get(ArtifactType.OCCULAR);
			Boolean result = ArtifactType.OCCULAR.isArtifact(segmentRange, structure.getRanges());
			if(result == null){
				return null;
			}
			if (result) {
				return ResultType.OCCULAR;
			} else {
				return ResultType.BRAIN_SIGNAL;
			}
		}
		if (ArtifactType.MUSCLE_D.isSuitableForType(channel)) {
			ArtifactsStructure structure = artifactsStructures.get(ArtifactType.MUSCLE_D);
			Boolean result = ArtifactType.MUSCLE_D.isArtifact(segmentRange, structure.getRanges());
			if(result == null){
				return null;
			}
			if (result) {
				return ResultType.MUSCLE;
			} else {
				return ResultType.BRAIN_SIGNAL;
			}
		}
		return null;
	}
	
	private void addToSerializableStructure(Segment segment, boolean test){
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
