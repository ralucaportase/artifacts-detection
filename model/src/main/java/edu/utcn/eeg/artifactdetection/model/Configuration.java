package edu.utcn.eeg.artifactdetection.model;

public interface Configuration {
	public static final int WINDOW_SIZE = 512; //1s 
	public static final int STEP = 25;//50ms
	public static final double RATE = 	(double) 512/1000; //512 sampling freq
	public static final String PROJECT_PATH = "D:/Projects/Diploma_EEG_Artefact_Detection_Maven";
	public static final String RESOURCES_PATH = PROJECT_PATH + "/resources";
	public static final String RESULTS_PATH = PROJECT_PATH + "/results";
}
