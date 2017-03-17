/**
 * 
 */
package edu.utcn.eeg.artifactdetection.features;

import edu.utcn.eeg.artifactdetection.model.FrequencyBands;

/**
 * @author Elena
 *
 */
public class FrequencyFeaturesExtractor {
	
	private double[] signal;
	private DFT dft;
	
	public FrequencyFeaturesExtractor(double[] signal) {
		this.signal = signal;
		this.dft = new DFT(signal);
	}
	
	public double computeDeltaMean(){
		return computeMean(FrequencyBands.DELTA_MIN, FrequencyBands.DELTA_MAX, signal.length);
	}
	
	public double computeThetaMean(){
		return computeMean(FrequencyBands.THETA_MIN, FrequencyBands.THETA_MAX, signal.length);
	}
	
	public double computeAlphaMean(){
		return computeMean(FrequencyBands.ALPHA_MIN, FrequencyBands.ALPHA_MAX, signal.length);
	}
	
	public double computeBetaLowMean(){
		return computeMean(FrequencyBands.BETA_LOW_MIN, FrequencyBands.BETA_LOW_MAX, signal.length);
	}
	
	public double computeBetaHighMean(){
		return computeMean(FrequencyBands.BETA_HIGH_MIN, FrequencyBands.BETA_HIGH_MAX, signal.length);
	}
	
	public double computeGammaLowMean(){
		return computeMean(FrequencyBands.GAMMA_LOW_MIN, FrequencyBands.GAMMA_LOW_MAX, signal.length);
	}
	
	public double computeGammaHighMean(){
		return computeMean(FrequencyBands.GAMMA_HIGH_MIN, FrequencyBands.GAMMA_HIGH_MAX, signal.length);
	}
	
	private double computeMean(int frecvMin, int frecvMax, int n){
		int beanMin = (int) findBean(n, frecvMin);
		int beanMax = (int) findBean(n, frecvMax);
		double mean = 0;
		for(int i = beanMin; i<=beanMax; i++){
			mean += dft.getMagnitudeForBean(i);
		}
		return mean/(beanMax-beanMin+1);
	}
	
	/**
	 * 
	 * @param n nr de puncte din fereastra
	 * @return
	 */
	private double findBean(int n, int f){
		return (double)f*n/FrequencyBands.SAMPLING_FREQUENCY;
	}

}
