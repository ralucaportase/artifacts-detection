package edu.utcn.eeg.artifactdetection.features;

import org.apache.log4j.Logger;

import edu.utcn.eeg.artifactdetection.input.segmentation.LoggerUtil;

public class CorrelationFeaturesExtractor {
	
	private Logger logger = LoggerUtil.logger(CorrelationFeaturesExtractor.class);


	private double[] shift(double d[], int s) {
		double c[] = new double[d.length];
		for (int i = 0; i < c.length; i++) {
			if ((s + i >= 0) && (s + i < d.length))
				c[i] = d[s + i];
		}
		return c;
	}

	private double dot(final double[] a, final double[] b) {
		final int aLength = a.length;
		if (aLength != b.length) {
			System.out.println("ERROR: Vectors must be of equal length in dot product.");
			return 0;
		}
		double sum = 0;
		for (int i = 0; i < aLength; i++) {
			sum += a[i] * b[i];
		}
		return sum;
	}

	public double[] computeCrossCorrelation(double[] x, double[] y) {
		double[] result = new double[x.length+y.length-1];
		int k=0;
		for (int i = -y.length + 1; i < y.length; i++) {
			double slidingY[] = shift(y, i);
			result[k++] = dot(x, slidingY);
			System.out.println("dot product:" + result[k-1]);
		}
		return result;
	}
	
	public double computePearsonCorrelationCoeficient(double[]x, double[] y){
		double cov = computeCovariance(x, y);
		TimeFeatureExtractor tFeatureExtractor= new TimeFeatureExtractor();
		double stdX = tFeatureExtractor.computeStandardDeviation(x);
		double stdY = tFeatureExtractor.computeStandardDeviation(y);
		return cov/(stdX*stdY);
	}
	
	private double computeCovariance(double[]x, double[]y){
		if(x.length!=y.length){
			logger.error("ERROR! Different size windows! Pearson correlation can't be computed!");
			throw new IllegalArgumentException("ERROR! Different size windows! Pearson correlation can't be computed!");
		}
		int n = x.length;
		double sum = 0;
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				sum+=0.5*(x[i]-x[j])*(y[i]-y[j]);
			}
		}
		return sum/(n*n);
	}
}
