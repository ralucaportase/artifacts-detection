package edu.utcn.eeg.artifactdetection.model;



public interface FrequencyBands {

	public static int SAMPLING_FREQUENCY = 512;
	
	public static int DELTA_MIN = 0; 
	public static int DELTA_MAX = 4; 
	
	public static int THETA_MIN = 4; 
	public static int THETA_MAX = 7;
	
	public static int ALPHA_MIN = 8; 
	public static int ALPHA_MAX = 12;
	
	public static int BETA_LOW_MIN = 12; 
	public static int BETA_LOW_MAX = 20;
	
	public static int BETA_HIGH_MIN = 20; 
	public static int BETA_HIGH_MAX = 30;
	
	public static int GAMMA_LOW_MIN = 30; 
	public static int GAMMA_LOW_MAX = 60;
	
	public static int GAMMA_HIGH_MIN = 60; 
	public static int GAMMA_HIGH_MAX = 120;

}
