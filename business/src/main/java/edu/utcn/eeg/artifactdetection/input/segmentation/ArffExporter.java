package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import edu.utcn.eeg.artifactdetection.features.export.ArffGenerator;
import edu.utcn.eeg.artifactdetection.features.export.SegmentDeserializer;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class ArffExporter {
	
	private SegmentRepository trainRepo;
	private SegmentRepository cleanRepo;
	private SegmentRepository occularRepo;
	private SegmentRepository muscleRepo;
	private SegmentRepository cleanTest;
	private SegmentRepository occularTest;
	private SegmentRepository muscleTest;
	private SegmentRepository cleanEval;
	private SegmentRepository occularEval;
	private SegmentRepository muscleEval;
	
	public ArffExporter(){
		trainRepo = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/TrainData.ser");
		cleanRepo = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/Clean_Train.ser");
		occularRepo = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/Occular_Train.ser");
		muscleRepo = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/Muscle_Train.ser");
		cleanTest = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/Clean_Test.ser");
		occularTest = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/Occular_Test.ser");
		muscleTest = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/Muscle_Test.ser");
		cleanEval = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/Clean_Eval.ser");
		occularEval = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/Occular_Eval.ser");
		muscleEval = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/Muscle_Eval.ser");
		System.out.println("Full train "+trainRepo.getSegments().size());
		System.out.println("Clean "+cleanRepo.getSegments().size());
		System.out.println("Occular "+occularRepo.getSegments().size());
		System.out.println("Muscle "+muscleRepo.getSegments().size());
		System.out.println("Clean test "+cleanTest.getSegments().size());
		System.out.println("Occular test "+occularTest.getSegments().size());
		System.out.println("Muscle test "+muscleTest.getSegments().size());
		System.out.println("Clean eval "+cleanEval.getSegments().size());
		System.out.println("Occular eval "+occularEval.getSegments().size());
		System.out.println("Muscle eval "+muscleEval.getSegments().size());
	}
	
	public List<SegmentRepository> getSegmentRepositories(){
		return Lists.newArrayList(occularRepo,occularEval,occularTest,muscleRepo,muscleEval,muscleTest,cleanRepo,cleanEval,cleanTest);
	}
	
	public void export() throws IOException{
//		featureFilter.filter(cleanRepo);
//		//featureFilter.filter(trainRepo);
//		featureFilter.filter(cleanEval);
//		featureFilter.filter(cleanTest);
//		featureFilter.filter(muscleEval);
//		featureFilter.filter(muscleRepo);
//		featureFilter.filter(muscleTest);
//		featureFilter.filter(occularEval);
//		featureFilter.filter(occularRepo);
//		featureFilter.filter(occularTest);
		
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
				

		ArffGenerator arffGenerator = new ArffGenerator(Configuration.ARFF_TRAIN_NAME);
		
		for (AbstractSegment segment : all) {
			arffGenerator.writeSegmentFeatures(segment.getFeatures(), segment.getCorrectType());
//			if(segment.getCorrectType().equals(ResultType.BRAIN_SIGNAL)){
//					Segment segment2 = (Segment)segment;
//					//System.out.println(segment2.getValuesToString());
//				
//			}
		}
		
		List<AbstractSegment> testSegm=new ArrayList<>();
		testSegm.addAll(cleanTest.getSegments());
		testSegm.addAll(muscleTest.getSegments());
		testSegm.addAll(occularTest.getSegments());
		testSegm.addAll(cleanEval.getSegments());
		testSegm.addAll(muscleEval.getSegments());
		testSegm.addAll(occularEval.getSegments());
		Collections.shuffle(testSegm);
		ArffGenerator arffGenerator2 = new ArffGenerator(Configuration.ARFF_TEST_NAME);
		
		for (AbstractSegment segment : testSegm) {
			arffGenerator2.writeSegmentFeatures(segment.getFeatures(), segment.getCorrectType());
		}
		
		ArffGenerator arffGenerator3 = new ArffGenerator(Configuration.RESULTS_PATH+"/WekaFullTrainInput.arff");
		for (AbstractSegment abstractSegment : trainRepo.getSegments()) {
			arffGenerator3.writeSegmentFeatures(abstractSegment.getFeatures(), abstractSegment.getCorrectType());
		}
		

	}

}
