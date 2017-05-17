package edu.utcn.eeg.artifactdetection.helpers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Configuration;

public class OutputFileWriter {

	public static void writeToFile(String content, String filePath) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			if (content == null) {
				System.out.println("Null string content");
			}
			fw = new FileWriter(filePath);
			bw = new BufferedWriter(fw);
			bw.write(content);
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

}
