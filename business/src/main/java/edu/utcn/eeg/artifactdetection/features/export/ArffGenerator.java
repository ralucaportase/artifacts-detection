package edu.utcn.eeg.artifactdetection.features.export;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.FeatureType;
import edu.utcn.eeg.artifactdetection.model.MultiChannelFeatureType;
import edu.utcn.eeg.artifactdetection.model.Region;
import edu.utcn.eeg.artifactdetection.model.ResultType;

public class ArffGenerator {

	private final BufferedWriter writer;
	
	public ArffGenerator(String name) throws IOException{
		this.writer = new BufferedWriter(new FileWriter(name, true));
		generateArff(name);
	}

	public ArffGenerator(String name, boolean multiCh) throws IOException
	{
		this.writer = new BufferedWriter(new FileWriter(name, true));
		generateMultichArff(name);
	}

	private void generateMultichArff(String name) throws IOException
	{

		String data = writeMultichAttributes();

		this.writer.write(data);
		this.writer.flush();
	}

	private void generateArff(String name) throws IOException{
        
        String data=writeAttributes();

		this.writer.write(data);
		this.writer.flush();
	}

	private String writeMultichAttributes()
	{
		String data = "@relation eegArtifacts\n";
		for (int i = 0; i < 12; i += 3)
		{
			for (MultiChannelFeatureType feature : MultiChannelFeatureType.values())
			{
				data += writeAttribute(feature, Region.getRegionByIndex(i));
			}
		}
		data += "@attribute 'class' {" + ResultType.BRAIN_SIGNAL + ", " + ResultType.MUSCLE + ", " + ResultType.OCCULAR + "}\n";
		data += "@data\n";
		return data;
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

	private String writeAttribute(MultiChannelFeatureType featureType, Region region)
	{
		return "@attribute '" + featureType.toString() + "_" + region + "' real\n";
	}
	
	public void writeSegmentFeatures(Feature[] features, ResultType correctType) throws IOException{
		String data="";
		for(Feature feat : features){
			data+= Double.toString(feat.getValue())+",";
		}
		data+=correctType+"\n";
		this.writer.write(data);
		this.writer.flush();
	}
	
}
