package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import edu.utcn.eeg.artifactdetection.model.Configuration;
import edu.utcn.eeg.artifactdetection.model.Segment;

public class MultiChannelFileProcessorThread implements Callable<List<Segment>>{

	private Logger logger = LoggerUtil.logger(MultiChannelFileProcessorThread.class);
	private File file;
	private int channelNr;
	
	public MultiChannelFileProcessorThread(File file, int channelNr){
		this.file = file;
		this.channelNr = channelNr;
	}
	
	private List<Segment> createSegments(double[] data){
		List<Segment> segments = new ArrayList<>(); 
		for(int i =0; i<Configuration.WINDOW_SIZE; i+=Configuration.STEP){
			for(int j=i; j<data.length; j+=Configuration.WINDOW_SIZE){
				double[] values = Arrays.copyOfRange(data, j, j+Configuration.WINDOW_SIZE);
				Segment segment = new Segment(values);
				segment.setInitIdx(j);
				segment.setIterIdx(i);
				segment.setChannelNr(channelNr);
				segments.add(segment);
			}
		}
		return segments;
	}

	@Override
	public List<Segment> call() throws Exception {
		List<Double> data = new ArrayList<>();
		try(Scanner scan = new Scanner(file)){
			while (scan.hasNextDouble()) {
				data.add(scan.nextDouble());
			}
		} catch (FileNotFoundException e) {
			logger.error("A problem occured while reading file "+file.getName(),e);
		}
		return createSegments(data.stream().mapToDouble(i -> i).toArray());
	}
}
