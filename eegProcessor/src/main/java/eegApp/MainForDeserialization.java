package eegApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;
import eegApp.helpers.SegmentDeserializer;
import eegApp.model.ArtefactType;
import eegApp.model.EEGSegment;

public class MainForDeserialization {
private static final String PATH="E:\\Scoala\\licenta\\#0IMPLEMENTATION\\eegProcessor\\src\\main\\resources\\serialized\\Muscle.ser";
	public static void main(String[] args) {
		SegmentDeserializer segmentDeserializer=new SegmentDeserializer();
		SegmentRepository repo=segmentDeserializer.deserializeSegmentsFromFile(PATH);
		List<Segment> segments=repo.getSegments();
		List<EEGSegment> segmentsModel=new ArrayList<EEGSegment>();
		for (Segment seg:segments){
			EEGSegment segmentModel=new EEGSegment();
			double[] values=seg.getValues();
			List<Double> valuesForModel=new ArrayList<Double>();
			int valuesLength=values.length;
			for (int i=0;i<valuesLength;i++){
				valuesForModel.add(values[i]);
			}
			segmentModel.setValues(valuesForModel);
			ArtefactType artefactType;
			ResultType resultType=seg.getCorrectType();
			System.out.println(resultType);
			if (ResultType.OCCULAR.equals(resultType)){
				artefactType=ArtefactType.OCULAR;
			}else {
				if (ResultType.MUSCLE.equals(resultType)){
					artefactType=ArtefactType.MUSCULAR;
				}else{
					artefactType=ArtefactType.NONE;
				}
			}
			segmentModel.setArtefactType(artefactType);
			String idForModel=""+seg.getChannelNr()+"-"+seg.getInitIdx();
			segmentModel.setId(idForModel);
			System.out.println(segmentModel);
		}

	}

}
