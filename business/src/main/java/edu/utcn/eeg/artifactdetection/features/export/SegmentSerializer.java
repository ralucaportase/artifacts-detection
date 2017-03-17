package edu.utcn.eeg.artifactdetection.features.export;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class SegmentSerializer {

	public static void serialize(SegmentRepository segment, String path){
	     
	      try {
	         FileOutputStream fileOut =
	         new FileOutputStream(path+"/"+segment.getName()+".ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(segment);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data "+segment.getName()+" is saved");
	      }catch(IOException i) {
	         i.printStackTrace();
	      }
		}
}

