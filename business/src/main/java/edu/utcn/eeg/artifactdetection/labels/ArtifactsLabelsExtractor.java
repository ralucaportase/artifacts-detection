package edu.utcn.eeg.artifactdetection.labels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.common.collect.Range;

import edu.utcn.eeg.artifactdetection.model.ArtifactType;
import edu.utcn.eeg.artifactdetection.model.ArtifactsStructure;
import edu.utcn.eeg.artifactdetection.model.Configuration;

public class ArtifactsLabelsExtractor {

	public Map<ArtifactType, ArtifactsStructure> parseLabelsDirectory(String directoryName){
		File folder = new File(directoryName);
		Map<ArtifactType, ArtifactsStructure> artifactsStructures = new HashMap<>();
		for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	ArtifactsStructure as = parseFile(fileEntry);
	        	artifactsStructures.put(as.getType(), as);
	        }
	    }
		return artifactsStructures;
	}
	
	private ArtifactsStructure parseFile(File file){
		ArtifactType type = getArtifactTypeFromFile(file.getName());
		List<Range<Integer>> ranges = new ArrayList<>();

		try(Scanner scan = new Scanner(file)){
			while (scan.hasNextDouble()) {
				Integer low = computeIndexFromTime(scan);
				Integer high = computeIndexFromTime(scan);
				Range<Integer> range = Range.closed(low, high);
				ranges.add(range);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new ArtifactsStructure(type, ranges);
	}

	private int computeIndexFromTime(Scanner scan) {
		int val = scan.nextInt();
		System.out.println(val);
		return (int)(val*Configuration.RATE);
	}
	
	private ArtifactType getArtifactTypeFromFile(String file){
		return ArtifactType.valueOf(file.substring(0,file.length()-4));
	}
}
