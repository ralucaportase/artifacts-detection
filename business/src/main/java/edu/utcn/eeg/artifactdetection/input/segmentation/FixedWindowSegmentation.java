package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.util.Arrays;

import edu.utcn.eeg.artifactdetection.builders.StructureBuilder;
import edu.utcn.eeg.artifactdetection.model.Configuration;

public class FixedWindowSegmentation {

	private StructureBuilder sb;
	
	public FixedWindowSegmentation(){
		sb=new StructureBuilder();
	}
	
	/**
	 * 
	 * @param allValues
	 * @param windowSize - nr de valori (calculul timp - nr val deja efectuat)
	 * @return
	 */
	public void segment(double[] allValues, int index, int channel, int type){
		int i=0;
		for(i = 0; i<Configuration.WINDOW_SIZE; i+=Configuration.STEP){
			iterativeSegm(allValues, i,channel, type);
		}
	}
	
	private void iterativeSegm(double[] allValues, int start, int channel, int type){
		int i=0;
		for(i=start; i<allValues.length; i+=Configuration.WINDOW_SIZE){
			double[] values = Arrays.copyOfRange(allValues, i, i+Configuration.WINDOW_SIZE-1);
			sb.buildDataStructures(values, start, i, channel,type);		}
		if(i<allValues.length){
			double[] values = Arrays.copyOfRange(allValues, i, allValues.length-1);
			sb.buildDataStructures(values, start, i, channel,type);
		}
	}
	
	public StructureBuilder getStructureBuilder(){
		return sb;
	}
}
