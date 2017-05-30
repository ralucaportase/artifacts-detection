package edu.utcn.eeg.artifactdetection.classifier.decisiontree;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.List;

import edu.utcn.eeg.artifactdetection.classifier.Classifier;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.REPTree;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class DecisionTreeClassifier implements Classifier{

	@Override
	public List<AbstractSegment> classifySegments(List<AbstractSegment> segments) {
		return null;
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
