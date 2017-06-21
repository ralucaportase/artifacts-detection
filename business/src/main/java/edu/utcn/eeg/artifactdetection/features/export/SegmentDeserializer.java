package edu.utcn.eeg.artifactdetection.features.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import edu.utcn.eeg.artifactdetection.input.segmentation.LoggerUtil;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class SegmentDeserializer {

	private static final Logger logger = LoggerUtil.logger(SegmentDeserializer.class);

	public static List<Segment> deserializeList(File file)
	{
		List<Segment> segments = new ArrayList<>();
		try
		{
			FileInputStream inputFileStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
			segments = (List<Segment>) objectInputStream.readObject();
			objectInputStream.close();
			inputFileStream.close();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException i)
		{
			i.printStackTrace();
		}
		return segments;
	}

	public static SegmentRepository deserializeSegmentsFromFile(
			String pathToFile) {
		SegmentRepository deserializedRepository = null;
		try {
			logger.info("Now Deserializing Repository Class !!!");
			FileInputStream inputFileStream = new FileInputStream(pathToFile);
			ObjectInputStream objectInputStream = new ObjectInputStream(
					inputFileStream);
			deserializedRepository = (SegmentRepository) objectInputStream
					.readObject();
			objectInputStream.close();
			inputFileStream.close();
		} catch (ClassNotFoundException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (IOException i) {
			logger.error(i);
			i.printStackTrace();
		}
		return deserializedRepository;
	}

}
