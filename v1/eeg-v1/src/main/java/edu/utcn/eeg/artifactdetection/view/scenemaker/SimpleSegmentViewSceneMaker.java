package edu.utcn.eeg.artifactdetection.view.scenemaker;

import java.util.List;

import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Feature;
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
	
	

	public SimpleSegmentViewSceneMaker(Stage stage, List<Segment> segments, int indexOfSegmentToShow) {
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
		hBox.getChildren().addAll(lineChartFromSegment.generateChartFromSegment(segments.get(indexOfSegmentToShow)));
		VBox vBox = new VBox();
		vBox.getChildren().addAll(hBox,this.paneWithFlowControl());
		Scene scene = new Scene(vBox, LENGTH_STAGE, HIGH_STAGE);
		return scene;
	}

	@SuppressWarnings("restriction")
	private void addActionHandlerForBackButton() {
		btnBack.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("back");
				ListOfChannelsSceneMaker sm = new ListOfChannelsSceneMaker(stage);
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
					SimpleSegmentViewSceneMaker sm = new SimpleSegmentViewSceneMaker(stage, segments,
							indexOfSegmentToShow);
					stage.setScene(sm.makeScene());
				} else {
					System.out.println("no more segments");
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
					SimpleSegmentViewSceneMaker sm = new SimpleSegmentViewSceneMaker(stage, segments,
							indexOfSegmentToShow);
					stage.setScene(sm.makeScene());
				} else {
					System.out.println("no more segments");
				}
			}
		});
	}

	private VBox paneWithInfo() {
		GridPane pane1 = new GridPane();
		pane1.setAlignment(Pos.TOP_CENTER);
		pane1.setHgap(50);
		pane1.setVgap(50);
		pane1.setPadding(new Insets(1, 1, 1, 1));
		pane1.add(btnBack, 0, 0);
		pane1.add(initIndexLabel, 0, 2);
		VBox vbox=new VBox();
		vbox.getChildren().addAll(pane1, constructPaneWithSegmentInfo(segments.get(indexOfSegmentToShow)));
		return vbox;
	}
	private GridPane paneWithFlowControl() {

		GridPane pane1 = new GridPane();
		//pane1.setAlignment(Pos.TOP_CENTER);
		pane1.setHgap(50);
		pane1.setVgap(50);
		pane1.setPadding(new Insets(1, 1, 1, 1));
		initIndexLabel.setText(initIndexLabel.getText() + segments.get(indexOfSegmentToShow).getInitIdx());
		pane1.add(btnNextSegment, 5, 1);
		pane1.add(btnPreviousSegment, 3, 1);
		
		return pane1;
	}
	
	private Pane constructPaneWithSegmentInfo(AbstractSegment segm){
		GridPane pane1 = new GridPane();
		Feature[] features=segm.getFeatures();
		int i=0;
		for(Feature f :features){
			Label featureLabel = new Label(f.getFeature().toString()+": "+f.getValue());
			pane1.add(featureLabel, 0, i);
			i++;
		}
		return pane1;
	}

}
