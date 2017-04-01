package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class FileProcessor {
	
	private Logger logger = LoggerUtil.logger(FileProcessor.class);
	private FixedWindowSegmentation fws;
	
	public FileProcessor(){
		fws=new FixedWindowSegmentation();
	}
	
	public List<SegmentRepository> parseDataDirectory(File folder){
		int i=0;
		for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	logger.info(fileEntry.getAbsolutePath());
	            parseFile(fileEntry,i++);
	        }
	    }
		return fws.getStructureBuilder().getSerializableStructures();
	}

	private void parseFile(File file, int index){
		int channel = getChannelFromFile(file.getName());
		if(channel<65){
			return;
		}
		logger.info("Fisierul channel "+channel);
		List<Double> data = new ArrayList<>();
		List<Double> testData = new ArrayList<>();
		try(Scanner scan = new Scanner(file)){
			while (scan.hasNextDouble()) {
				if(data.size() <= Configuration.TRAIN_MAX_INDEX){
					data.add(scan.nextDouble());
				}
				else if(testData.size()<Configuration.TEST_MAX_INDEX){
					testData.add(scan.nextDouble());
				}
				else{
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		logger.info("Nr elemente train "+data.size());
		logger.info("Nr elemente test "+testData.size());
		segment(data, index,channel,false);
		segment(testData, index, channel, true);	
	}
	
	private void segment(List<Double> data, int index, int channel, boolean test){
		fws.segment(data.stream().mapToDouble(i -> i).toArray(), index, channel,test);
	}
	
	private int getChannelFromFile(String file){
		String delims = "[._]+";
		String[] tokens = file.split(delims);
		for(String token:tokens){
			try{
				return Integer.parseInt(token);
			}catch(NumberFormatException e){
				continue;
			}
		}
		return -1;
	}
}