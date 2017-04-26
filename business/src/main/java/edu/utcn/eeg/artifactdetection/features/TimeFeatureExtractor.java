package edu.utcn.eeg.artifactdetection.features;

import java.util.Collections;
import java.util.List;

import com.google.common.primitives.Doubles;


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
		return Math.sqrt(stdDev);
	}
	
	public double computeMedian(double[] values){
		List<Double> valList= Doubles.asList(values.clone());
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
		return Math.sqrt(rms);
	}
	
	public double computeSkewness(double[] values){
		double skewness = 0; 
		double numa=0;
		double numi=0;
		double mean = computeMean(values);
		for (double d : values) {
			numa+=(d-mean)*(d-mean)*(d-mean);
			numi+=(d-mean)*(d-mean);
		}
		numa/=values.length;
		numi/=values.length;
		numi=numi*numi*numi;
		numi=Math.sqrt(numi);
		skewness = numa/numi;
		return skewness;
	}
	
	public double computeKurtosis(double[] values){
		double numa=0;
		double numi=0;
		double mean = computeMean(values);
		for (double d : values) {
			numa+=(d-mean)*(d-mean)*(d-mean)*(d-mean);
			numi+=(d-mean)*(d-mean);
		}
		numa/=values.length;
		numi/=values.length;
		numi=numi*numi;
		return numa/numi;
	}

}
