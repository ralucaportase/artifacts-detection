package edu.utcn.eeg.artifactdetection.features.export;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.FeatureType;
import edu.utcn.eeg.artifactdetection.model.ResultType;

public class ArffGenerator {
	
	private  BufferedWriter writer;
	
	public ArffGenerator(String name) throws IOException{
	    writer = new BufferedWriter(new FileWriter(name,true));
		generateArff(name);
	}

	private void generateArff(String name) throws IOException{
        
        String data=writeAttributes();
 
        writer.write(data);
        writer.flush();
	}
	
	private String writeAttributes(){
		String data = "@relation eegArtifacts\n";
		for (FeatureType feature : FeatureType.values()) {
			data+=writeAttribute(feature);
		}
		data+="@attribute 'class' {"+ResultType.BRAIN_SIGNAL+", "+ResultType.MUSCLE+", "+ResultType.OCCULAR+"}\n";
		data+="@data\n";
		return data;
	}
	
	private String writeAttribute(FeatureType featureType){
		return "@attribute '" + featureType.toString() +"' real\n";
	}
	
	public void writeSegmentFeatures(Feature[] features, ResultType correctType) throws IOException{
		String data="";
		for(Feature feat : features){
			data+= Double.toString(feat.getValue())+",";
		}
		data+=correctType+"\n";
		writer.write(data);
		writer.flush();
	}
	
}
