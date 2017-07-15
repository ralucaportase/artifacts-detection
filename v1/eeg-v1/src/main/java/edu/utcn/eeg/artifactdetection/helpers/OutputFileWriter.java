package edu.utcn.eeg.artifactdetection.helpers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class OutputFileWriter {

	private static OutputFileWriter instance = null;
	Logger logger = LoggerUtil.logger(getClass());

	public static OutputFileWriter getInstance() {
		if (instance == null) {
			instance = new OutputFileWriter();
		}
		return instance;
	}

	public void writeToFile(String content, String filePath) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			if (content == null) {
				logger.info("Null string content");
			}
			fw = new FileWriter(filePath);
			bw = new BufferedWriter(fw);
			bw.write(content);
			logger.info("Done");
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
