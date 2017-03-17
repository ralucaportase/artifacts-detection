package edu.utcn.eeg.artifactdetection.features.export;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class DecisionTreeXMLWriter {
	public Document createXML() {
		try {
			Element myExample = new Element("EXAMPLES");
			Document doc = new Document(myExample);
			XMLOutputter xmlOutput = new XMLOutputter();

			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("/decisionTreeFeatures.xml"));
			return doc;
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
		return null;
	}
	
	public void addParameter(Document doc, String type,String name ){
		Element parameter = new Element("PARAMETER");
		parameter.setAttribute(new Attribute("type", type));
		parameter.setText(name);
		doc.getRootElement().addContent(parameter);
	}
	
	public void addExample(Document doc, String type, Map<String,String> pairs){
		Element dataSet = new Element("EXAMPLE");
		dataSet.setAttribute(new Attribute("type", type));
		for (String pair : pairs.keySet()) {
			Element valuElem = new Element("VALUE");
			valuElem.setAttribute(new Attribute("parameter", pair));
			valuElem.setText(pairs.get(pair));
			dataSet.addContent(valuElem);
		}

		doc.getRootElement().addContent(dataSet);
		XMLOutputter xmlOutput = new XMLOutputter();

		// display nice nice
		xmlOutput.setFormat(Format.getPrettyFormat());
		try {
			xmlOutput.output(doc, new FileWriter("DTinputData.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
