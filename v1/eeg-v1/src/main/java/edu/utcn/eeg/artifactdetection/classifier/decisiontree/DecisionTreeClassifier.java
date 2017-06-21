package edu.utcn.eeg.artifactdetection.classifier.decisiontree;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import edu.utcn.eeg.artifactdetection.classifier.Classifier;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.FeatureType;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.REPTree;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class DecisionTreeClassifier implements Classifier{

	@Override
	public List<Segment> classifySegments(List<Segment> segments){
		List<Segment> predictedSegments = new ArrayList<>();
		REPTree repTreeClassifier = createClassifier();
		 ArrayList<Attribute> fvWekaAttributes =  createAttributesList();
		 
		Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, segments.size());
		isTrainingSet.setClassIndex(fvWekaAttributes.size()-1);
		 
		for (Segment segment : segments) {
			Segment segm = (Segment) segment;
			Double result = classifySegment(repTreeClassifier, fvWekaAttributes, isTrainingSet, segm);
			Segment predicted = createPredictedInstance(segm, result);
			predictedSegments.add(predicted);
		}
		return predictedSegments;
	}

	private Double classifySegment(REPTree repTreeClassifier, ArrayList<Attribute> fvWekaAttributes,
			Instances isTrainingSet, Segment segm) {
		Instance instance = new DenseInstance(FeatureType.values().length+1);
		for (int i=0; i<FeatureType.values().length; i++){
			instance.setValue(fvWekaAttributes.get(i), segm.getFeatureValueForFeature(FeatureType.values()[i]));
		}
		instance.setValue(fvWekaAttributes.get(FeatureType.values().length), segm.getCorrectType().toString());
		isTrainingSet.add(instance);
		Double result = null;
		try {
			result = repTreeClassifier.classifyInstance(isTrainingSet.lastInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private REPTree createClassifier() {
		REPTree repTreeClassifier = null;
		try {
			repTreeClassifier = (REPTree)SerializationHelper.read(new FileInputStream(Configuration.DT_MODEL));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return repTreeClassifier;
	}

	private ArrayList<Attribute> createAttributesList() {
		ArrayList<Attribute> fvWekaAttributes = new ArrayList<>();
		for (FeatureType featureType : FeatureType.values()) {
			Attribute attribute = new Attribute(featureType.name());
			fvWekaAttributes.add(attribute);
		}

		 List<String> fvClassVal = new ArrayList<>();
		 fvClassVal.add(ResultType.BRAIN_SIGNAL.name());
		 fvClassVal.add(ResultType.OCCULAR.name());
		 fvClassVal.add(ResultType.MUSCLE.name());
		 Attribute classAttribute = new Attribute("class", fvClassVal);
		 fvWekaAttributes.add(classAttribute);
		 return fvWekaAttributes;
	}

	private Segment createPredictedInstance(Segment segm, Double result) {
		Segment predicted = new Segment(segm.getValues());
		predicted.setCorrectType(ResultType.values()[result.intValue()]);
		predicted.setFeatures(segm.getFeatures());
		predicted.setInitIdx(segm.getInitIdx());
		predicted.setIterIdx(segm.getIterIdx());
		return predicted;
	}
	
	
	
	
	private void classify() throws Exception{
		REPTree repTreeClassifier = (REPTree)SerializationHelper.read(new FileInputStream(Configuration.DT_MODEL));
		BufferedReader reader = new BufferedReader(
                new FileReader(Configuration.RESULTS_PATH+"/WekaTestInput.arff"));
		Instances data = new Instances(reader);
		reader.close();
		data.setClassIndex(data.numAttributes() - 1);
		 // evaluate classifier and print some statistics
		 Evaluation eval = new Evaluation(data);
		 eval.evaluateModel(repTreeClassifier, data);
		 System.out.println(eval.toClassDetailsString());
	}

}
