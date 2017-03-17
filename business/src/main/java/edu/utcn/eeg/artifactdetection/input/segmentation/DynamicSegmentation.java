package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DynamicSegmentation {

	private static int MAX_SEGMENT_SIZE = 5; // TODO: calc. size
	private Map<Integer, Float[]> segments = new HashMap<>();
	private int key = 0;

	public void segment(Float[] allValues, int threshold) {
		int lastIndex = 0;
		for (int i = 1; i < allValues.length - 1; i++) {
			if (allValues[i] < allValues[i - 1] && allValues[i] < allValues[i + 1]
					&& Math.abs(allValues[i]) < threshold) {
				addPreviousSegments(allValues, i, lastIndex);
				lastIndex = addLowPeak(allValues, i);
			} else if (allValues[i] > allValues[i - 1] && allValues[i] > allValues[i + 1]
					&& Math.abs(allValues[i]) > threshold) {
				addPreviousSegments(allValues, i, lastIndex);
				lastIndex = addHighPeak(allValues, i);
			}
		}
		if (lastIndex < allValues.length - 1) {
			addLastSegments(allValues, lastIndex);
		}
	}

	private void addLastSegments(Float[] allValues, int lastIndex) {
		Float[] result = Arrays.copyOfRange(allValues, lastIndex, allValues.length);
		if (result.length <= MAX_SEGMENT_SIZE) {
			segments.put(key++, result);
		} else {
			int i=0;
			for (i = 0; i < result.length; i += MAX_SEGMENT_SIZE) {
				Float[] res = Arrays.copyOfRange(result, i, i + MAX_SEGMENT_SIZE);
				segments.put(key++, res);
			}
			if (i < result.length) {
				segments.put(key++, Arrays.copyOfRange(result, i, result.length));
			}
		}
	}

	private void addPreviousSegments(Float[] arr, int peak, int lastInd) {
		int i = peak;
		while (arr[i] < arr[i - 1]) {
			i--;
		}
		Float[] result = Arrays.copyOfRange(arr, lastInd, i);
		if (result.length <= MAX_SEGMENT_SIZE) {
			segments.put(key++, result);
		} else {
			for (i = 0; i < result.length; i += MAX_SEGMENT_SIZE) {
				Float[] res = Arrays.copyOfRange(result, i, i + MAX_SEGMENT_SIZE);
				segments.put(key++, res);
			}
			if (i < result.length) {
				segments.put(key++, Arrays.copyOfRange(result, i, result.length));
			}
		}
	}

	private int addLowPeak(Float[] arr, int peakIndex) {
		int i = peakIndex;
		int j = peakIndex;
		while (arr[i] < arr[i - 1]) {
			i--;
		}
		while (arr[j] < arr[j + 1]) {
			j++;
		}
		segments.put(key++, Arrays.copyOfRange(arr, i, j));
		return j;
	}

	private int addHighPeak(Float[] arr, int peakIndex) {
		int i = peakIndex;
		int j = peakIndex;
		while (arr[i] > arr[i - 1]) {
			i--;
		}
		while (arr[j] > arr[j + 1]) {
			j++;
		}
		segments.put(key++, Arrays.copyOfRange(arr, i, j));
		return j;
	}

}
