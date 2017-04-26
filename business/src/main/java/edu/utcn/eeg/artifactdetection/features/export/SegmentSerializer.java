package edu.utcn.eeg.artifactdetection.features.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class SegmentSerializer {

	public static void serialize(SegmentRepository segment, String path) {

		try {
			File dir = getOutputDir(path);

			File outputFile = new File(dir, segment.getName() + ".ser");
			FileOutputStream fileOut = new FileOutputStream(outputFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(segment);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data " + segment.getName() + " is saved");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	private static File getOutputDir(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
}