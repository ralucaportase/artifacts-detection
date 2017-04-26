package edu.utcn.eeg.artifactdetection.view.chart;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ChartHolder {

	final NumberAxis xAxis = new NumberAxis();
	final NumberAxis yAxis = new NumberAxis();

	private LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

	public ChartHolder(String chartName) {

		lineChart.setTitle(chartName);
		xAxis.setLabel("Value");
		lineChart.setCreateSymbols(false);

	}

	public void addSeries(double[] values, String seriesName) {
		int n = values.length;
		XYChart.Series series = new XYChart.Series();
		series.setName(seriesName);

		for (int i = 0; i < n; i++) {
			series.getData().add(new XYChart.Data(i, values[i]));
		}

		lineChart.getData().addAll(series);

	}

	public LineChart<Number, Number> getLineChart() {
		return lineChart;
	}

}
