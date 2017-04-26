package edu.utcn.eeg.artifactdetection.helpers;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import edu.utcn.eeg.artifactdetection.model.*;
public class SegmentDeserializer {
	
	public SegmentRepository deserializeSegmentsFromFile(String pathToFile){
		SegmentRepository deserializedRepository = null;
	    try
	    {
	      System.out.println("Now Deserializing Repository Class !!!");
	      FileInputStream inputFileStream = new FileInputStream(pathToFile);
	      ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
	      deserializedRepository= ( SegmentRepository)objectInputStream.readObject();
	      objectInputStream.close();
	      inputFileStream.close();
	    }
	    catch(ClassNotFoundException e)
	    {
	      e.printStackTrace();
	    }
	    catch(IOException i)
	    {
	      i.printStackTrace();
	    }
		return deserializedRepository;
	}

}
