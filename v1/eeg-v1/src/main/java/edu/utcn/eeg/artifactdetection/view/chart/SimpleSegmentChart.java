package edu.utcn.eeg.artifactdetection.view.chart;

import java.util.List;

import edu.utcn.eeg.artifactdetection.model.Segment;
import javafx.scene.chart.LineChart;

public class SimpleSegmentChart {

	public LineChart<Number, Number> generateChartFromSegment(Segment segment) {
		ChartHolder ch = new ChartHolder("Simple Segment");
		ch.addSeries(segment.getValues(), "Segm");
		LineChart<Number, Number> lineChart = ch.getLineChart();
		return lineChart;
	}

}
