package edu.utcn.eeg.artifactdetection.view.scenemaker;

import java.text.DecimalFormat;
import java.util.List;

import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.FeatureType;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.view.chart.SimpleSegmentChart;
import javafx.scene.layout.VBox;
import javafx.scene.control.Toggle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ToggleGroup;

public class SimpleSegmentViewSceneMaker extends AbstractSceneMaker {

	private List<Segment> segments;
	private int indexOfSegmentToShow;
	private Button btnBack;
	private Button btnNextSegment;
	private Button btnPreviousSegment;
	private Label initIndexLabel = new Label("Init index: ");
	private Label channelNrLabel = new Label("Channel number: ");

	public SimpleSegmentViewSceneMaker(Stage stage, List<Segment> segments,
			int indexOfSegmentToShow) {
		super(stage);
		this.segments = segments;
		this.indexOfSegmentToShow = indexOfSegmentToShow;

	}

	public Scene makeScene() {

		btnNextSegment = new Button();
		btnNextSegment.setText("Next");
		addActionHandlerForNextButton();
		btnPreviousSegment = new Button();
		btnPreviousSegment.setText("Previous");
		addActionHandlerForPreviousButton();
		btnBack = new Button();
		btnBack.setText("Back to channels list");
		addActionHandlerForBackButton();

		HBox hBox = new HBox();
		hBox.getChildren().addAll(paneWithInfo());
		SimpleSegmentChart lineChartFromSegment = new SimpleSegmentChart();
		hBox.setPadding(new Insets(15, 10, 0, 10));
		hBox.getChildren().addAll(
				lineChartFromSegment.generateChartFromSegment(segments
						.get(indexOfSegmentToShow)));
		VBox vBox = new VBox();
		vBox.getChildren().addAll(hBox, this.paneWithFlowControl());
		Scene scene = new Scene(vBox, LENGTH_STAGE, HIGH_STAGE);
		scene.getStylesheets().add("file:src/resources/stylesheet.css");
		return scene;
	}

	@SuppressWarnings("restriction")
	private void addActionHandlerForBackButton() {
		btnBack.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				logger.info("back");
				ListOfChannelsSceneMaker sm = new ListOfChannelsSceneMaker(
						stage);
				stage.setScene(sm.makeScene());
			}
		});
	}

	@SuppressWarnings("restriction")
	private void addActionHandlerForNextButton() {
		btnNextSegment.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				if (indexOfSegmentToShow < segments.size() - 1) {
					indexOfSegmentToShow++;
					SimpleSegmentViewSceneMaker sm = new SimpleSegmentViewSceneMaker(
							stage, segments, indexOfSegmentToShow);
					stage.setScene(sm.makeScene());
				} else {
					logger.info("no more segments");
				}
			}
		});
	}

	@SuppressWarnings("restriction")
	private void addActionHandlerForPreviousButton() {
		btnPreviousSegment.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				if (indexOfSegmentToShow > 0) {
					indexOfSegmentToShow--;
					SimpleSegmentViewSceneMaker sm = new SimpleSegmentViewSceneMaker(
							stage, segments, indexOfSegmentToShow);
					stage.setScene(sm.makeScene());
				} else {
					logger.info("no more segments");
				}
			}
		});
	}

	private VBox paneWithInfo() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(20, 15, 20, 10));
		vbox.getChildren()
				.addAll(new Label("Segment informations"),
						new Label(""),
						new Label(""),
						initIndexLabel,
						new Label(""),
						channelNrLabel,
						new Label(""),
						constructPaneWithSegmentInfo(segments
								.get(indexOfSegmentToShow)));
		return vbox;
	}

	private GridPane paneWithFlowControl() {

		GridPane pane1 = new GridPane();
		// pane1.setAlignment(Pos.TOP_CENTER);
		pane1.setHgap(50);
		pane1.setVgap(50);
		pane1.setPadding(new Insets(1, 1, 1, 1));
		initIndexLabel.setText(initIndexLabel.getText()
				+ segments.get(indexOfSegmentToShow).getInitIdx());
		channelNrLabel.setText(channelNrLabel.getText()
				+ segments.get(indexOfSegmentToShow).getChannelNr());
		pane1.add(btnNextSegment, 12, 0);
		pane1.add(btnPreviousSegment, 11, 0);
		pane1.add(btnBack, 10, 0);

		return pane1;
	}

	private Pane constructPaneWithSegmentInfo(AbstractSegment segm) {
		GridPane pane1 = new GridPane();
		pane1.add(new Label("Feature set "), 0, 0);
		Feature[] features = segm.getFeatures();
		int i = 1;
		DecimalFormat df = new DecimalFormat("#.00");
		for (Feature f : features) {
			if (f.getFeature() != FeatureType.ENTROPY) {
				Label featureLabel = new Label(f.getFeature().toString() + ": "
						+ df.format(f.getValue()));
				pane1.add(featureLabel, 0, i);
			} else {
				Label featureLabel = new Label(f.getFeature().toString() + ": "
						+ df.format(-f.getValue()));

				pane1.add(featureLabel, 0, i);
			}
			i++;
		}
		return pane1;
	}
}
