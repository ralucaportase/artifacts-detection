package edu.utcn.eeg.artifactdetection.classifier.knn;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * This class is used for implementing different features of knn algorithm.
 * 
 * @author Tolas Ramona
 *
 */
public class KnnManager {

	public static int[] predict(String trainingFile, TestRecord[] testRecords, int k, Metric metric) {
		int lengthOfTestingRecords = testRecords.length;
		int[] predictedLabels = new int[lengthOfTestingRecords];

		try {
			TrainRecord[] trainingSet = FileManager.readTrainFile(trainingFile);

			for (int i = 0; i < lengthOfTestingRecords; i++) {
				TrainRecord[] neighbors = findKNearestNeighbors(trainingSet, testRecords[i], k, metric);
				int classLabel = classify(neighbors);
				testRecords[i].setPredictedLabel(classLabel);
				predictedLabels[i] = classLabel;
			}
			int correctPrediction = 0;
			for (int j = 0; j < lengthOfTestingRecords; j++) {
				if (testRecords[j].getPredictedLabel() == testRecords[j].classLabel)
					correctPrediction++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return predictedLabels;

	}

	private static TrainRecord[] findKNearestNeighbors(TrainRecord[] trainingSet, TestRecord testRecord, int K,
			Metric metric) {
		int NumOfTrainingSet = trainingSet.length;
		TrainRecord[] neighbors = new TrainRecord[K];
		int index;
		for (index = 0; index < K; index++) {
			trainingSet[index].setDistance(metric.getDistance(trainingSet[index], testRecord));
			neighbors[index] = trainingSet[index];
		}
		for (index = K; index < NumOfTrainingSet; index++) {
			trainingSet[index].setDistance(metric.getDistance(trainingSet[index], testRecord));
			int maxIndex = 0;
			for (int i = 1; i < K; i++) {
				if (neighbors[i].getDistance() > neighbors[maxIndex].getDistance())
					maxIndex = i;
			}
			if (neighbors[maxIndex].getDistance() > trainingSet[index].getDistance())
				neighbors[maxIndex] = trainingSet[index];
		}

		return neighbors;
	}

	/**
	 * This method is used for calculating the label of a record using it's
	 * neghbors.
	 * 
	 * @param neighbors
	 *            : set of points that was found to be closest neigbors
	 * @return predicted label by appling knn algorithm
	 */
	private static int classify(TrainRecord[] neighbors) {

		HashMap<Integer, Double> map = new HashMap<Integer, Double>();
		int num = neighbors.length;

		for (int index = 0; index < num; index++) {
			TrainRecord temp = neighbors[index];
			int key = temp.classLabel;
			if (!map.containsKey(key))
				map.put(key, 1 / temp.getDistance());
			else {
				double value = map.get(key);
				value += 1 / temp.getDistance();
				map.put(key, value);
			}
		}
		double maxSimilarity = 0;
		int returnLabel = -1;
		Set<Integer> labelSet = map.keySet();
		Iterator<Integer> it = labelSet.iterator();
		while (it.hasNext()) {
			int label = it.next();
			double value = map.get(label);
			if (value > maxSimilarity) {
				maxSimilarity = value;
				returnLabel = label;
			}
		}
		return returnLabel;
	}

}
