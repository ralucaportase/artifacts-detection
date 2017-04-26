package edu.utcn.eeg.artifactdetection.view.chart;

import java.util.List;

import edu.utcn.eeg.artifactdetection.model.MultiChannelSegment;
import edu.utcn.eeg.artifactdetection.model.Segment;
import javafx.scene.chart.LineChart;

public class MultiSegmentChart {

	private static final int SEPARATOR = 9;

	public LineChart<Number, Number> generateChartFromMultiSegment(MultiChannelSegment multiChSeg) {
		ChartHolder ch = new ChartHolder("MultiChannelSegment");
		List<Segment> normalSegments = multiChSeg.getSegments();
		int n = normalSegments.size();
		for (int i = 0; i < n; i++) {
			double[] values = normalSegments.get(i).getValues();
			ch.addSeries(incrementWithSeparatorValues(values, i * SEPARATOR), "Segm");

		}

		LineChart<Number, Number> lineChart = ch.getLineChart();
		return lineChart;
	}

	private double[] incrementWithSeparatorValues(double[] input, double value) {

		int n = input.length;
		double[] output = new double[n];
		for (int i = 0; i < n; i++) {
			output[i] = input[i] + value;
		}

		return output;

	}

}
