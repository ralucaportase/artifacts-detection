package edu.utcn.eeg.artifactdetection.classification;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import edu.utcn.eeg.artifactdetection.model.Configuration;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.REPTree;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class DTWekaModelCreator {

	public static void main(String[] args) throws FileNotFoundException, Exception{
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
