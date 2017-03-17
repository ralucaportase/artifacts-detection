package edu.utcn.eeg.artifactdetection.features;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class TimeFeatureExtractor {

	
	public double computeMean(double[] values){
		double mean = 0; 
		for (double d : values) {
			mean+=d;
		}
		return mean/values.length;
	}
	
	public double computeStandardDeviation(double[] values){
		double stdDev = 0; 
		double mean = computeMean(values);
		for (double d : values) {
			stdDev+=(d-mean)*(d-mean);
		}
		stdDev = stdDev/(values.length-1);
		return (double) Math.sqrt(stdDev);
	}
	
	public double computeMedian(Double[] values){
		List<Double> valList= Arrays.asList(values);
		Collections.sort(valList);
		if(valList.size()%2==0){
			return (valList.get(valList.size()/2) + valList.get(valList.size()/2+1))/2;
		}
		return valList.get(valList.size()/2);
	}
	
	public double computeRootMeanSquare(double[] values){
		double rms=0;
		for (double d : values) {
			rms+=d*d;
		}
		rms = rms/values.length;
		return (double)Math.sqrt(rms);
	}

}
