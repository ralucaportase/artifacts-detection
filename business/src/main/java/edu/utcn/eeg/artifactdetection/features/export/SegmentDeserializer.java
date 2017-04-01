package edu.utcn.eeg.artifactdetection.features.export;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.log4j.Logger;

import edu.utcn.eeg.artifactdetection.input.segmentation.LoggerUtil;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class SegmentDeserializer {
	
	private static Logger logger = LoggerUtil.logger(SegmentDeserializer.class);


	public static SegmentRepository deserializeSegmentsFromFile(String pathToFile){
		SegmentRepository deserializedRepository = null;
	    try
	    {
	      logger.info("Now Deserializing Repository Class !!!");
	      FileInputStream inputFileStream = new FileInputStream(pathToFile);
	      ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
	      deserializedRepository= ( SegmentRepository)objectInputStream.readObject();
	      objectInputStream.close();
	      inputFileStream.close();
	    }
	    catch(ClassNotFoundException e)
	    {
	    	logger.error(e);
	    	e.printStackTrace();
	    }
	    catch(IOException i)
	    {
	    	logger.error(i);
	    	i.printStackTrace();
	    }
		return deserializedRepository;
	}

}
