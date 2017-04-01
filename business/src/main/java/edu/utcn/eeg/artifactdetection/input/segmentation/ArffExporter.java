package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.utcn.eeg.artifactdetection.features.export.ArffGenerator;
import edu.utcn.eeg.artifactdetection.features.export.SegmentDeserializer;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class ArffExporter {
	
	public static void export() throws IOException{
		SegmentRepository cleanRepo = SegmentDeserializer.deserializeSegmentsFromFile("D:/Projects/Diploma_EEG_Artefact_Detection_Maven/serialized/Clean76-80.ser");
		SegmentRepository occularRepo = SegmentDeserializer.deserializeSegmentsFromFile("D:/Projects/Diploma_EEG_Artefact_Detection_Maven/serialized/Occular76-80.ser");
		SegmentRepository muscleRepo = SegmentDeserializer.deserializeSegmentsFromFile("D:/Projects/Diploma_EEG_Artefact_Detection_Maven/serialized/Muscle76-80.ser");
		System.out.println("Clean "+cleanRepo.getSegments().size());
		System.out.println("Occular "+occularRepo.getSegments().size());
		System.out.println("Muscle "+muscleRepo.getSegments().size());
		
		List<AbstractSegment> cleaned = new ArrayList<>();
		
		for (AbstractSegment segm : cleanRepo.getSegments()) {
			if(segm.getCorrectType().equals(ResultType.BRAIN_SIGNAL)){
				cleaned.add(segm);
			}
		}
		
		List<AbstractSegment> all = new ArrayList<>();
		
		all.addAll(cleaned);
		all.addAll(occularRepo.getSegments());
		all.addAll(muscleRepo.getSegments());
		
		Collections.shuffle(all);
				

		ArffGenerator arffGenerator = new ArffGenerator(Configuration.ARFF_NAME);
		
		for (AbstractSegment segment : all) {
			arffGenerator.writeSegmentFeatures(segment.getFeatures(), segment.getCorrectType());
		}
	}

}
