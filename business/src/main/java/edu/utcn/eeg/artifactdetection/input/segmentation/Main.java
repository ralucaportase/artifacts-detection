package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.File;

import edu.utcn.eeg.artifactdetection.model.Configuration;

public class Main {

	public static void main(String[] args){
		FileProcessor fp = new FileProcessor();
		String fileName = Configuration.RESOURCES_PATH+"/TxtInputFiles"; // could also be a constant
		fp.parseDataDirectory(new File(fileName));
	}

}
