package eegApp;

import java.util.Arrays;
import java.util.List;

import eegApp.model.EEGSample;
import eegApp.model.EEGTrial;
import eegApp.report.PdfReportGeneratorForSegments;

public class MainForReport {

	public static void main(String[] args) {
		PdfReportGeneratorForSegments reportGenerator = new PdfReportGeneratorForSegments();
		EEGTrial trial1=new EEGTrial();
		trial1.setValues(Arrays.asList(0.5,1.2,-0.75,-1.5,0.25,2.01,0.30));		
		EEGTrial trial2=new EEGTrial();
		trial2.setValues(Arrays.asList(-0.3,-1.2,-1.5,0.7,1.25,1.90,2.5));
		List<EEGTrial> trials=Arrays.asList(trial1,trial2); 
		EEGSample sample=new EEGSample();
		sample.setTrials(trials);
		sample.setChannelId(1);
		sample.setSubjectId(2);
		
		reportGenerator.genereatePDFReportWithSegmentsForSample(null, "segmetChart.pdf");
		System.out.println("done");
	}

}
