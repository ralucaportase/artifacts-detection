package edu.utcn.eeg.artifactdetection.model;

public interface Configuration {
	public static final int    WINDOW_SIZE         = 512; //1s
	public static final int    STEP                = 128;//2500ms
	public static final double RATE                = (double) 512 / 1000; //512 sampling freq
	public static final String PROJECT_PATH        = "D:\\DiplomaCode\\artifacts-detection";
	public static final String RESOURCES_PATH      = PROJECT_PATH + "/resources";
	public static final String RESULTS_PATH        = PROJECT_PATH + "/results";
	public static final int    MAX_INDEX           = 238000;
	public static final String ARFF_TRAIN_NAME     = RESULTS_PATH + "/WekaTrainInput.arff";
	public static final String ARFF_TEST_NAME      = RESULTS_PATH + "/WekaTestInput.arff";
	public static final int    TEST_PROPORTION     = 20; //20%
	public static final int    EVAL_PROPORTION     = 10; //10%
	public static final int    TRAIN_MAX_INDEX     = (100 - TEST_PROPORTION - EVAL_PROPORTION) * MAX_INDEX / 100;
	public static final int    TEST_MAX_INDEX      = TRAIN_MAX_INDEX + TEST_PROPORTION * MAX_INDEX / 100;
	public static final String INPUT_FILES         = RESOURCES_PATH + "/TxtInputFiles";
	public static final String DT_MODEL = RESOURCES_PATH +"/Classifier/wekaREP2.model";
	public static final String MULTI_SEGMENTS_PATH = RESULTS_PATH + "/MultiSegments";

}
