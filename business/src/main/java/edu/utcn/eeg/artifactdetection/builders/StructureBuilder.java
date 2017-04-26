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
	private SegmentRepository occularStructTrain;
	private SegmentRepository muscleStructTrain;
	private SegmentRepository brainStructTrain;
	private SegmentRepository occularStructTest;
	private SegmentRepository muscleStructTest;
	private SegmentRepository brainStructTest;
	private SegmentRepository occularStructEval;
	private SegmentRepository muscleStructEval;
	private SegmentRepository brainStructEval;

	public StructureBuilder() {
		ArtifactsLabelsExtractor artifactsLabelsExtractor = new ArtifactsLabelsExtractor();
		artifactsStructures = artifactsLabelsExtractor.parseLabelsDirectory(Configuration.RESOURCES_PATH+"/Labels");
		//dataGen = new DataGeneratorForDecisionTree();
		brainStructEval = new SegmentRepository("Clean_Eval");
		muscleStructEval = new SegmentRepository("Muscle_Eval");
		occularStructEval = new SegmentRepository("Occular_Eval");
		brainStructTest = new SegmentRepository("Clean_Test");
		muscleStructTest = new SegmentRepository("Muscle_Test");
		occularStructTest = new SegmentRepository("Occular_Test");
		brainStructTrain = new SegmentRepository("Clean_Train");
		muscleStructTrain = new SegmentRepository("Muscle_Train");
		occularStructTrain = new SegmentRepository("Occular_Train");
	}

	public void buildDataStructures(double[] data, int iter, int localIndex, int channel, int type) {
		int index = localIndex;
		if(type==2){
			index = localIndex + Configuration.TRAIN_MAX_INDEX;
		}else if(type==3){
			index = localIndex + Configuration.TEST_MAX_INDEX;
		}
		ResultType resultType = computeCorrectType(index, channel);
		if (resultType != null) {
			Segment segment = createSegment(data, iter, index, channel, resultType);
			//dataGen.writeSegment(segment);
			addToSerializableStructure(segment, type);
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
	
	private void addToSerializableStructure(Segment segment, int type){
		switch (segment.getCorrectType()) {
		case OCCULAR:
			switch (type) {
			case 1:
				occularStructTrain.addSegment(segment);
				break;
			case 2:
				occularStructTest.addSegment(segment);
				break;
			default:
				occularStructEval.addSegment(segment);
				break;
			}			
			break;
		case MUSCLE: 
			switch (type) {
			case 1:
				muscleStructTrain.addSegment(segment);
				break;
			case 2:
				muscleStructTest.addSegment(segment);
				break;
			default:
				muscleStructEval.addSegment(segment);
				break;
			}		
			break;
		default:
			switch (type) {
			case 1:
				brainStructTrain.addSegment(segment);
				break;
			case 2:
				brainStructTest.addSegment(segment);
				break;
			default:
				brainStructEval.addSegment(segment);
				break;
			}		
			break;
		}
	}
	
	public List<SegmentRepository> getSerializableStructures(){
		return Lists.newArrayList(occularStructTrain,occularStructEval,occularStructTest,muscleStructTrain,muscleStructEval,muscleStructTest,brainStructTrain,brainStructEval,brainStructTest);
		
	}
		

}
