package edu.utcn.eeg.artifactdetection.view.scenemaker;

import java.util.List;

import edu.utcn.eeg.artifactdetection.classifier.Classifier;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Feature;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.postprocessing.AbstractFileGenerator;
import edu.utcn.eeg.artifactdetection.postprocessing.BinaryFileGenerator;
import edu.utcn.eeg.artifactdetection.view.chart.SimpleSegmentChart;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimpleSegmentLabeldViewSceneMaker extends AbstractSceneMaker {

	protected List<Segment> segments;
	protected int indexOfSegmentToShow;
	protected Button btnBack;
	protected Button btnNextSegment;
	protected Button btnPreviousSegment;
	
	protected Button btnGenerateReport;
	protected Button btnGenerateClenSignal;
	protected Label initIndexLabel = new Label("Init index: ");
	protected Label labelLabel = new Label("Label: ");
	private Classifier clasiffier;

	public SimpleSegmentLabeldViewSceneMaker(Stage stage, Classifier clasiffier, List<Segment> segments,
			int indexOfSegmentToShow) {
		super(stage);
		this.segments = segments;
		this.indexOfSegmentToShow = indexOfSegmentToShow;
		this.clasiffier = clasiffier;
	}

	public Scene makeScene() {

		btnNextSegment = new Button();
		btnNextSegment.setText("Next");
		addActionHandlerForNextButton();
		btnPreviousSegment = new Button();
		btnPreviousSegment.setText("Previous");
		addActionHandlerForPreviousButton();
		btnBack = new Button();
		btnBack.setText("Back to list of channels");
		addActionHandlerForBackButton();
		
		btnGenerateReport = new Button();
		btnGenerateReport.setText("Generate report");
		addActionHandlerForbtnGenerateReport();
		
		btnGenerateClenSignal = new Button();
		btnGenerateClenSignal.setText("Generate clean signal");
		addActionHandlerForGenerateClenSignal();
		
		HBox hBox = new HBox();
		hBox.getChildren().addAll(paneWithInfo());

		SimpleSegmentChart lineChartFromSegment = new SimpleSegmentChart();
		hBox.getChildren().addAll(lineChartFromSegment.generateChartFromSegment(segments.get(indexOfSegmentToShow)));
		hBox.getChildren().addAll(paneWithLabelValidation());
		VBox vBox = new VBox();
		vBox.getChildren().addAll(hBox,this.paneWithFlowControl());
		Scene scene = new Scene(vBox, LENGTH_STAGE, HIGH_STAGE);
		return scene;
	}

	@SuppressWarnings("restriction")
	protected void addActionHandlerForGenerateClenSignal() {
		btnGenerateClenSignal.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("generate clean");
				AbstractFileGenerator fileGenerator = new BinaryFileGenerator();
				fileGenerator.generateFileFromSegment(segments);
				System.out.println("File with clean signal was generated");
			}
		});
	}
	@SuppressWarnings("restriction")
	protected void addActionHandlerForbtnGenerateReport() {
		btnGenerateReport.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("generate report");
				AbstractFileGenerator fileGenerator = new BinaryFileGenerator();
				fileGenerator.outputStatistics(segments);
				System.out.println("Statistics was generated!");
			}
		});
	}
	@SuppressWarnings("restriction")
	protected void addActionHandlerForBackButton() {
		btnBack.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("back");
				ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
						stage, clasiffier);
				stage.setScene(sm.makeScene());
			}
		});
	}

	@SuppressWarnings("restriction")
	protected void addActionHandlerForNextButton() {
		btnNextSegment.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				if (indexOfSegmentToShow < segments.size() - 1) {
					indexOfSegmentToShow++;
					SimpleSegmentLabeldViewSceneMaker sm = new SimpleSegmentLabeldViewSceneMaker(stage, clasiffier,
							segments, indexOfSegmentToShow);
					stage.setScene(sm.makeScene());
				} else {
					System.out.println("no more segments");
				}
			}
		});
	}

	@SuppressWarnings("restriction")
	protected void addActionHandlerForPreviousButton() {
		btnPreviousSegment.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				if (indexOfSegmentToShow > 0) {
					indexOfSegmentToShow--;
					SimpleSegmentLabeldViewSceneMaker sm = new SimpleSegmentLabeldViewSceneMaker(stage, clasiffier,segments,
							indexOfSegmentToShow);
					stage.setScene(sm.makeScene());
				} else {
					System.out.println("no more segments");
				}
			}
		});
	}

	private GridPane paneWithFlowControl() {

		GridPane pane1 = new GridPane();
		pane1.setAlignment(Pos.TOP_CENTER);
		pane1.setHgap(50);
		pane1.setVgap(50);
		pane1.setPadding(new Insets(1, 1, 1, 1));		
		pane1.add(btnNextSegment, 5, 1);
		pane1.add(btnPreviousSegment, 3, 1);
		
		return pane1;
	}

	protected GridPane paneWithLabelValidation() {
		GridPane pane = new GridPane();
		final ToggleGroup group = new ToggleGroup();
		VBox vboxChecks = new VBox();
		vboxChecks.setSpacing(10);
		vboxChecks.setPadding(new Insets(20));
		Label label = new Label("Change the label of the segment");
		RadioButton ocular = new RadioButton("Ocular");
		ocular.setToggleGroup(group);
		RadioButton muscular = new RadioButton("Muscular");
		muscular.setToggleGroup(group);
		RadioButton clean = new RadioButton("Clean");
		clean.setToggleGroup(group);
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() != null) {
					RadioButton selected = (RadioButton) group.getSelectedToggle();
					Segment currentSegment = segments.get(indexOfSegmentToShow);
					if (selected.equals(clean)) {
						System.out.println("changed label to clean");
						currentSegment.setCorrectType(ResultType.BRAIN_SIGNAL);
					} else {
						if (selected.equals(ocular)) {
							System.out.println("changed label to ocular");
							currentSegment.setCorrectType(ResultType.OCCULAR);
						} else {
							System.out.println("changed label to muscular");
							currentSegment.setCorrectType(ResultType.MUSCLE);
						}
					}
				}
			}
		});
		vboxChecks.getChildren().addAll(ocular, muscular, clean);
		pane.add(label, 0, 0);
		pane.add(vboxChecks, 0, 1);
		pane.add(btnGenerateClenSignal, 0, 3);
		pane.add(btnGenerateReport, 0, 4);
		return pane;
	}
	
	private VBox paneWithInfo() {
		GridPane pane1 = new GridPane();
		pane1.setAlignment(Pos.TOP_CENTER);
		pane1.setHgap(50);
		pane1.setVgap(50);
		pane1.setPadding(new Insets(1, 1, 1, 1));
		pane1.add(btnBack, 0, 0);
		initIndexLabel.setText(initIndexLabel.getText() + segments.get(indexOfSegmentToShow).getInitIdx());
		labelLabel.setText(labelLabel.getText() + segments.get(indexOfSegmentToShow).getCorrectType().name());
		pane1.add(initIndexLabel, 0, 2);
		pane1.add(labelLabel, 0, 3);
		
		VBox vbox=new VBox();
		vbox.getChildren().addAll(pane1, constructPaneWithSegmentInfo(segments.get(indexOfSegmentToShow)));
		return vbox;
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
