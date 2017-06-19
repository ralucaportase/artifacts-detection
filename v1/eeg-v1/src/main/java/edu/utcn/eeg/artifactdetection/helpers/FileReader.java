package edu.utcn.eeg.artifactdetection.helpers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.utcn.eeg.artifactdetection.model.Configuration;

public class FileReader {
	
	private static FileReader instance = null;

	public static FileReader getInstance() {
		if (instance == null) {
			instance = new FileReader();
		}
		return instance;
	}


	public List<Double> parseTxtFile(File file) {
		List<Double> fileOutput = new ArrayList<Double>();

		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNextDouble()) {
				fileOutput.add(scan.nextDouble());
				if (fileOutput.size() > Configuration.MAX_INDEX) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileOutput;
	}
}
