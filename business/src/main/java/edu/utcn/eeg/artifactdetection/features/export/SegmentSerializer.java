package edu.utcn.eeg.artifactdetection.features.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import org.apache.log4j.Logger;
import edu.utcn.eeg.artifactdetection.input.segmentation.LoggerUtil;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class SegmentSerializer {

	private static final Logger logger = LoggerUtil.logger(SegmentSerializer.class);

	public static void serialize(SegmentRepository segment, String path) {

		try {
			File dir = getOutputDir(path);

			File outputFile = new File(dir, segment.getName() + ".ser");
			FileOutputStream fileOut = new FileOutputStream(outputFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(segment);
			out.close();
			fileOut.close();
			logger.info("Serialized data " + segment.getName() + " is saved");
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

	public static void serializeList(List<Segment> segments, File outputFile)
	{

		try
		{
			FileOutputStream fileOut = new FileOutputStream(outputFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(segments);
			out.close();
			fileOut.close();
		}
		catch (IOException i)
		{
			i.printStackTrace();
		}
	}
}
