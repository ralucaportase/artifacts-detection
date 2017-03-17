package edu.utcn.eeg.artifactdetection.builders;

import java.util.Map;

import com.google.common.collect.Range;

import edu.utcn.eeg.artifactdetection.features.FeatureExtractor;
import edu.utcn.eeg.artifactdetection.features.export.DataGeneratorForDecisionTree;
import edu.utcn.eeg.artifactdetection.features.export.SegmentSerializer;
import edu.utcn.eeg.artifactdetection.labels.ArtifactsLabelsExtractor;
import edu.utcn.eeg.artifactdetection.model.ArtifactType;
import edu.utcn.eeg.artifactdetection.model.ArtifactsStructure;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class StructureBuilder {

	private DataGeneratorForDecisionTree dataGen;
	private Map<ArtifactType, ArtifactsStructure> artifactsStructures;
	private SegmentRepository occularStruct;
	private SegmentRepository muscleStruct;
	private SegmentRepository brainStruct;
	private int occ; 
	private int musc; 
	private int brain;

	public StructureBuilder() {
		ArtifactsLabelsExtractor artifactsLabelsExtractor = new ArtifactsLabelsExtractor();
		artifactsStructures = artifactsLabelsExtractor.parseLabelsDirectory(Configuration.RESOURCES_PATH+"/Labels");
		//dataGen = new DataGeneratorForDecisionTree();
		brainStruct = new SegmentRepository("Clean");
		muscleStruct = new SegmentRepository("Muscle");
		occularStruct = new SegmentRepository("Occular");
	}

	public void buildDataStructures(double[] data, int iter, int index, int channel) {
		ResultType resultType = computeCorrectType(index, channel);
		if (resultType != null) {
			Segment segment = createSegment(data, iter, index, channel, resultType);
			//dataGen.writeSegment(segment);
			addToSerializableStructure(segment);
			System.out.println(segment);
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
				System.out.println("Error with the feature extraction!");
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
	
	private void addToSerializableStructure(Segment segment){
		switch (segment.getCorrectType()) {
		case OCCULAR:
			occularStruct.addSegment(segment);
			occ++;
			break;
		case MUSCLE: 
			muscleStruct.addSegment(segment);
			musc++;
		default:
			brainStruct.addSegment(segment);
			brain++;
		}
	}
	
	public void serialize(){
		SegmentSerializer.serialize(occularStruct, Configuration.RESULTS_PATH);
		SegmentSerializer.serialize(muscleStruct, Configuration.RESULTS_PATH);
		SegmentSerializer.serialize(brainStruct, Configuration.RESULTS_PATH);
		System.out.println("Ocular = "+occ+" Muscular = "+musc+" Brain = "+brain);
	}

}
