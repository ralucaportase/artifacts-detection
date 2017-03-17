package edu.utcn.eeg.artifactdetection.features;

/**
 * Computes the DFT of a time signal 
 * @author Elena
 *
 */
public class DFT {
	
	private double[] signal; 
	private double[] DFTReal;
	private double[] DFTImag;
	private double[] magnitudes;

	public DFT(double[] signal) {
		this.signal = signal; 
		int n = signal.length;
		int m = n/2 + 1;
		DFTReal = new double[m];
		DFTImag = new double[m];
		computeDFT(m,n);
	}
	
	//real numbers
	private void computeDFT(int m, int n){
		for (int k = 0; k < m; k++) {  // For each output element
	        double sumreal = 0;
	        double sumimag = 0;
	        for (int t = 0; t < n; t++) {  // For each input element
	            double angle = 2 * Math.PI * t * k / n;
	            sumreal +=  signal[t] * Math.cos(angle);
	            sumimag +=  -signal[t] * Math.sin(angle);
	        }
	        DFTReal[k] = sumreal;
	        DFTImag[k] = sumimag;
		}
	}
	
	private void computeMagnitude(int m){
		magnitudes = new double[m];
		for(int i =0 ;i<m; i++){
			magnitudes[i] = Math.sqrt(DFTReal[i]*DFTReal[i]+DFTImag[i]*DFTImag[i]);
		}
	}

	public double[] getDFTReal() {
		return DFTReal;
	}

	public double[] getDFTImag() {
		return DFTImag;
	}
	
	public double getMagnitudeForBean(int b){
		if(magnitudes==null){
			computeMagnitude(signal.length/2+1);
		}
		return magnitudes[b];
	}

}
