package eegApp.report.model;

import org.jfree.chart.JFreeChart;
import eegApp.model.ArtefactType;

/**
 * This class represents an element that is going to be in the report. It
 * contains the chart for a segment and the artefact detected on that chart.
 * 
 * @author Tolas Ramona
 *
 */
public class ReportElement {

	private JFreeChart chart;
	private ArtefactType artefactType;

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public ArtefactType getArtefactType() {
		return artefactType;
	}

	public void setArtefactType(ArtefactType artefactType) {
		this.artefactType = artefactType;
	}

}
