package edu.utcn.eeg.artifactdetection.view.scenemaker;

import java.util.List;

import edu.utcn.eeg.artifactdetection.model.MultiChannelSegment;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.view.chart.MultiSegmentChart;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MultipleSegmentLabeledViewSceneMaker extends AbstractSceneMaker {

	protected List<MultiChannelSegment> segments;
	protected int indexOfSegmentToShow;

	protected Button btnBack;

	protected Button btnNextSegment;
	protected Button btnPreviousSegment;
	private Label initIndexLabel = new Label("Init index: ");
	private Label labelLabel = new Label("Label: ");

	public MultipleSegmentLabeledViewSceneMaker(Stage stage, List<MultiChannelSegment> segments, int indexOfSegmentToShow) {
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
		btnBack.setText("Back to menu");
		addActionHandlerForBackButton();
		
		HBox hBox = new HBox();
		hBox.getChildren().addAll(scrollPaneWithRegister());

		MultiSegmentChart lineChartFromMultiSegment = new MultiSegmentChart();
		hBox.getChildren()
				.addAll(lineChartFromMultiSegment.generateChartFromMultiSegment(segments.get(indexOfSegmentToShow)));

		hBox.getChildren().addAll(paneWithLabelValidation());
		
		Scene scene = new Scene(hBox, LENGTH_STAGE, HIGH_STAGE);
		return scene;
	}

	@SuppressWarnings("restriction")
	protected void addActionHandlerForBackButton() {
		btnBack.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				logger.info("back");
				ListOfRegionMulticlassClassificationSceneMaker sm = new ListOfRegionMulticlassClassificationSceneMaker(stage);
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
					MultipleSegmentLabeledViewSceneMaker sm = new MultipleSegmentLabeledViewSceneMaker(stage, segments,
							indexOfSegmentToShow);
					stage.setScene(sm.makeScene());
				} else {
					logger.info("no more segments");
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
					MultipleSegmentLabeledViewSceneMaker sm = new MultipleSegmentLabeledViewSceneMaker(stage, segments,
							indexOfSegmentToShow);
					stage.setScene(sm.makeScene());
				} else {
					logger.info("no more segments");
				}
			}
		});
	}

	

	private GridPane scrollPaneWithRegister() {

		GridPane pane1 = new GridPane();
		pane1.setAlignment(Pos.TOP_CENTER);
		pane1.setHgap(50);
		pane1.setVgap(50);
		pane1.setPadding(new Insets(1, 1, 1, 1));
		initIndexLabel.setText(initIndexLabel.getText() + segments.get(indexOfSegmentToShow).getInitIdx());
		labelLabel.setText(labelLabel.getText() + segments.get(indexOfSegmentToShow).getCorrectType().name());
		pane1.add(btnNextSegment, 0, 0);
		pane1.add(btnPreviousSegment, 0, 1);
		pane1.add(initIndexLabel, 0, 2);
		pane1.add(labelLabel, 0, 3);
		pane1.add(btnBack, 1, 0);
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
					MultiChannelSegment currentSegment = segments.get(indexOfSegmentToShow);
					if (selected.equals(clean)) {
						logger.info("changed label to clean");
						currentSegment.setCorrectType(ResultType.BRAIN_SIGNAL);
					} else {
						if (selected.equals(ocular)) {
							logger.info("changed label to ocular");
							currentSegment.setCorrectType(ResultType.OCCULAR);
						} else {
							logger.info("changed label to muscular");
							currentSegment.setCorrectType(ResultType.MUSCLE);
						}
					}
				}
			}
		});
		vboxChecks.getChildren().addAll(ocular, muscular, clean);
		pane.add(label, 0, 0);
		pane.add(vboxChecks, 0, 1);
		return pane;
	}
}
