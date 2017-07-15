package edu.utcn.eeg.artifactdetection.view.scenemaker;

import java.util.List;

import org.apache.log4j.Logger;

import edu.utcn.eeg.artifactdetection.helpers.LoggerUtil;
import edu.utcn.eeg.artifactdetection.model.MultiChannelSegment;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MultipleSegmentLabeledBinaryViewSceneMaker extends MultipleSegmentLabeledViewSceneMaker{

	Logger logger = LoggerUtil.logger(getClass());

	public MultipleSegmentLabeledBinaryViewSceneMaker(Stage stage, List<MultiChannelSegment> segments,
			int indexOfSegmentToShow) {
		super(stage, segments, indexOfSegmentToShow);
	}

	@Override
	protected GridPane paneWithLabelValidation() {
		GridPane pane = new GridPane();
		final ToggleGroup group = new ToggleGroup();
		VBox vboxChecks = new VBox();
		vboxChecks.setSpacing(10);
		vboxChecks.setPadding(new Insets(20));
		Label label = new Label("Change the label of the segment");
		RadioButton artef = new RadioButton("Artefact");
		artef.setToggleGroup(group);
		RadioButton clean = new RadioButton("Clean");
		clean.setToggleGroup(group);
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() != null) {
					RadioButton selected = (RadioButton) group.getSelectedToggle();
					MultiChannelSegment currentSegment = segments.get(indexOfSegmentToShow);
					if (selected.equals(clean)) {
						logger.info("changed label to clean");
						currentSegment.setCorrectType(ResultType.BRAIN_SIGNAL);
					} else {
						if (selected.equals(artef)) {
							logger.info("artef");
							currentSegment.setCorrectType(ResultType.OCCULAR);
						}
					}
				}
			}
		});
		vboxChecks.getChildren().addAll(artef, clean);
		pane.add(label, 0, 0);
		pane.add(vboxChecks, 0, 1);
		return pane;
	}
	
	
	@Override
	@SuppressWarnings("restriction")
	protected void addActionHandlerForNextButton() {
		btnNextSegment.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (indexOfSegmentToShow < segments.size() - 1) {
					indexOfSegmentToShow++;
					MultipleSegmentLabeledBinaryViewSceneMaker sm = new MultipleSegmentLabeledBinaryViewSceneMaker(stage, segments,
							indexOfSegmentToShow);
					stage.setScene(sm.makeScene());
				} else {
					logger.info("no more segments");
				}
			}
		});
	}
	
	@Override
	@SuppressWarnings("restriction")
	protected void addActionHandlerForPreviousButton() {
		btnPreviousSegment.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (indexOfSegmentToShow > 0) {
					indexOfSegmentToShow--;
					MultipleSegmentLabeledBinaryViewSceneMaker sm = new MultipleSegmentLabeledBinaryViewSceneMaker(stage, segments,
							indexOfSegmentToShow);
					stage.setScene(sm.makeScene());
				} else {
					logger.info("no more segments");
				}
			}
		});
	}
	
	@Override
	@SuppressWarnings("restriction")
	protected void addActionHandlerForBackButton() {
		btnBack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				logger.info("back");
				ListOfRegionsBinaryClassificationSceneMaker sm = new ListOfRegionsBinaryClassificationSceneMaker(stage);
				stage.setScene(sm.makeScene());
			}
		});
	}
}
