package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.utcn.eeg.artifactdetection.model.Configuration;

public class FileProcessor {
	

	FixedWindowSegmentation fws;
	
	public FileProcessor(){
		fws=new FixedWindowSegmentation();
	}
	
	public void parseDataDirectory(File folder){
		int i=0;
		for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	System.out.println(fileEntry.getAbsolutePath());
	            parseFile(fileEntry,i++);
	        }
	    }
		fws.getStructureBuilder().serialize();
	}

	private void parseFile(File file, int index){
		int channel = getChannelFromFile(file.getName());
		if(channel<65){
			return;
		}
		System.out.println("Fisierul ch "+channel);
		List<Double> data = new ArrayList<>();
		try(Scanner scan = new Scanner(file)){
			while (scan.hasNextDouble()) {
				data.add(scan.nextDouble());
				if(data.size() > Configuration.MAX_INDEX){
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Nr elemente "+data.size());
		fws.segment(data.stream().mapToDouble(i -> i).toArray(), index, channel);
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