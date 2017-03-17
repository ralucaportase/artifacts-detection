package edu.utcn.eeg.artifactdetection.features.export;
import java.util.HashMap;
import java.util.Map;

import org.jdom2.Document;

import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.FeatureType;
import edu.utcn.eeg.artifactdetection.model.Segment;


public class DataGeneratorForDecisionTree {
	
	private DecisionTreeXMLWriter xmlWriter;
	private Document document;
	
	public DataGeneratorForDecisionTree(){
		generateFile();
	}

	
	public void writeSegment(Segment segment){
			Map<String,String> pair = new HashMap<>();
			for(Feature feat : segment.getFeatures()){
				pair.put(feat.getFeature().toString(), Double.toString(feat.getValueName()));
			}
			pair.put("Label", segment.getCorrectType().toString());
			xmlWriter.addExample(document, "training", pair);
			System.out.println("Added a segment "+segment.getChannelNr()+" "+segment.getIterIdx()+" "+segment.getInitIdx());
	}
	
	private void generateFile() {
		xmlWriter = new DecisionTreeXMLWriter();
		document = xmlWriter.createXML();
		for (FeatureType feature : FeatureType.values()) {
			xmlWriter.addParameter(document, "input", feature.toString());
		}
		xmlWriter.addParameter(document, "output", "Label");
	}
	
}
