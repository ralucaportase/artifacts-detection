package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.common.collect.Lists;
import edu.utcn.eeg.artifactdetection.features.export.ArffGenerator;
import edu.utcn.eeg.artifactdetection.features.export.SegmentDeserializer;
import edu.utcn.eeg.artifactdetection.features.export.SegmentSerializer;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class ArffExporter {
	
//	private SegmentRepository trainRepo;
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
		//trainRepo = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/TrainData.ser");
		this.cleanRepo = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Clean_Train.ser");
		this.occularRepo = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Occular_Train.ser");
		this.muscleRepo = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Muscle_Train.ser");
		this.cleanTest = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Clean_Test.ser");
		this.occularTest = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Occular_Test.ser");
		this.muscleTest = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Muscle_Test.ser");
		this.cleanEval = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Clean_Eval.ser");
		this.occularEval = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Occular_Eval.ser");
		this.muscleEval = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Muscle_Eval.ser");
		//System.out.println("Full train "+trainRepo.getSegments().size());
		System.out.println("Clean " + this.cleanRepo.getSegments()
													.size());
		System.out.println("Occular " + this.occularRepo.getSegments()
														.size());
		System.out.println("Muscle " + this.muscleRepo.getSegments()
													  .size());
		System.out.println("Clean test " + this.cleanTest.getSegments()
														 .size());
		System.out.println("Occular test " + this.occularTest.getSegments()
															 .size());
		System.out.println("Muscle test " + this.muscleTest.getSegments()
														   .size());
		System.out.println("Clean eval " + this.cleanEval.getSegments()
														 .size());
		System.out.println("Occular eval " + this.occularEval.getSegments()
															 .size());
		System.out.println("Muscle eval " + this.muscleEval.getSegments()
														   .size());
	}

	public ArffExporter(boolean nix)
	{

	}
	
	public List<SegmentRepository> getSegmentRepositories(){
		return Lists.newArrayList(this.occularRepo, this.occularEval, this.occularTest, this.muscleRepo, this.muscleEval, this.muscleTest, this.cleanRepo, this.cleanEval, this.cleanTest);
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

		for (AbstractSegment segm : this.cleanRepo.getSegments())
		{
			if(segm.getCorrectType().equals(ResultType.BRAIN_SIGNAL)){
				cleaned.add(segm);
			}
		}
		
		List<AbstractSegment> all = new ArrayList<>();
		
		all.addAll(cleaned);
		all.addAll(this.occularRepo.getSegments());
		all.addAll(this.muscleRepo.getSegments());
		
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
		SegmentRepository trainsrep= new SegmentRepository("AllForTrain");
		trainsrep.setSegments(all);
		SegmentSerializer.serialize(trainsrep, Configuration.RESULTS_PATH);
		
		List<AbstractSegment> testSegm=new ArrayList<>();
		testSegm.addAll(this.cleanTest.getSegments());
		testSegm.addAll(this.muscleTest.getSegments());
		testSegm.addAll(this.occularTest.getSegments());
		testSegm.addAll(this.cleanEval.getSegments());
		testSegm.addAll(this.muscleEval.getSegments());
		testSegm.addAll(this.occularEval.getSegments());
		Collections.shuffle(testSegm);
		ArffGenerator arffGenerator2 = new ArffGenerator(Configuration.ARFF_TEST_NAME);
		
		for (AbstractSegment segment : testSegm) {
			arffGenerator2.writeSegmentFeatures(segment.getFeatures(), segment.getCorrectType());
		}
		SegmentRepository teSegmentRepository= new SegmentRepository("AllForTest");
		teSegmentRepository.setSegments(testSegm);
		SegmentSerializer.serialize(teSegmentRepository, Configuration.RESULTS_PATH);
//		
//		DataBalancer dBalancer=new DataBalancer();
//		List<AbstractSegment> trainSegments = dBalancer.undersample(cleanRepo, occularRepo, muscleRepo);
//		
//		ArffGenerator arffGenerator3 = new ArffGenerator(Configuration.RESULTS_PATH+"/WekaFullTrainInput.arff");
//		for (AbstractSegment abstractSegment : trainSegments) {
//			arffGenerator3.writeSegmentFeatures(abstractSegment.getFeatures(), abstractSegment.getCorrectType());
//		}
//		
		
	}

}
