package edu.utcn.eeg.artifactdetection.classifier.knn;

import java.util.List;
import edu.utcn.eeg.artifactdetection.classifier.Classifier;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class KnnClassifier implements Classifier {
	
	private static final int NUMBER_FEATURES = 9;

	@Override
	public List<Segment> classifySegments(List<Segment> segments) {
		int numberOfSegments = segments.size();
		TestRecord[] testRecords = new TestRecord[numberOfSegments];
		int i = 0;
		for (AbstractSegment segment : segments) {
			TestRecord currentRecord = new TestRecord(getFeaturesValues(segment.getFeatures()), -1);
			testRecords[i] = currentRecord;
			i++;
		}
		int[] predictedValues = KnnManager.predict("artefacte_train2.txt", testRecords, 10, new ManhattenDistance());
		System.out.println("KNN algoritm is running - K=10 and distance Manhatten");
		for (int j = 0; j < segments.size(); j++) {
			// System.out.println(segments.get(j) + "going to be set " +
			// calculateResultType(predictedValues[j])
			// + "and it was " + segments.get(j).getCorrectType());
			segments.get(j).setCorrectType(calculateResultType(predictedValues[j]));
			// System.out.println(segments.get(j) + "has now " +
			// segments.get(j).getCorrectType());
		}
		System.out.println("Prediction is over!");
		return segments;
	}

	private double[] getFeaturesValues(Feature[] features) {
		double[] values = new double[NUMBER_FEATURES];
		values[0] = features[2].getValue();
		values[1] = features[3].getValue();
		values[2] = features[4].getValue();
		values[3] = features[7].getValue();
		values[4] = features[8].getValue();
		values[5] = features[10].getValue();
		values[6] = features[13].getValue();
		values[7] = features[14].getValue();
		values[8] = features[15].getValue();
		
		return values;
	}

	private ResultType calculateResultType(int label) {
		if (label == 1) {
			return ResultType.BRAIN_SIGNAL;
		} else {
			if (label == 2) {
				return ResultType.OCCULAR;
			} else {
				if (label == 3) {
					return ResultType.MUSCLE;
				}
			}
		}
		return null;
	}

}
