package edu.utcn.eeg.artifactdetection.view.scenemaker;

import java.util.List;

import edu.utcn.eeg.artifactdetection.classifier.svm.SvmClassifier;
import edu.utcn.eeg.artifactdetection.model.ResultType;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.postprocessing.AbstractFileGenerator;
import edu.utcn.eeg.artifactdetection.postprocessing.BinaryFileGenerator;
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

public class SimpleSegmentLabeledBinaryViewSceneMaker extends
		SimpleSegmentLabeldViewSceneMaker {

	private String clasificator;

	public SimpleSegmentLabeledBinaryViewSceneMaker(Stage stage,
			List<Segment> segments, int indexOfSegmentToShow,
			String clasificator) {
		super(stage, new SvmClassifier(), segments, indexOfSegmentToShow, 0);
		this.clasificator = clasificator;
	}

	protected GridPane paneWithLabelValidation() {
		GridPane pane = new GridPane();
		final ToggleGroup group = new ToggleGroup();
		VBox vboxChecks = new VBox();
		vboxChecks.setSpacing(10);
		vboxChecks.setPadding(new Insets(20));
		Label label = new Label("Change segment label");

		RadioButton artef = new RadioButton("Artefact");
		artef.setToggleGroup(group);
		RadioButton clean = new RadioButton("Clean");
		clean.setToggleGroup(group);
		group.selectedToggleProperty().addListener(
				new ChangeListener<Toggle>() {
					public void changed(ObservableValue<? extends Toggle> ov,
							Toggle old_toggle, Toggle new_toggle) {
						if (group.getSelectedToggle() != null) {
							RadioButton selected = (RadioButton) group
									.getSelectedToggle();
							Segment currentSegment = segments
									.get(indexOfSegmentToShow);
							if (selected.equals(clean)) {
								System.out.println("changed label to clean");
								currentSegment
										.setCorrectType(ResultType.BRAIN_SIGNAL);
							} else {
								if (selected.equals(artef)) {
									System.out
											.println("changed label to artefact");
									currentSegment
											.setCorrectType(ResultType.MUSCLE);
								}
							}
						}
					}
				});
		vboxChecks.getChildren().addAll(artef, clean);
		pane.add(new Label(), 0, 0);
		pane.add(label, 0, 1);
		pane.add(vboxChecks, 0, 2);
		return pane;
	}

	protected void addActionHandlerForBackButton() {
		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println("back");
				ListOfChannelsBinaryClassificationSceneMaker sm = new ListOfChannelsBinaryClassificationSceneMaker(
						stage, clasificator);
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
					SimpleSegmentLabeledBinaryViewSceneMaker sm = new SimpleSegmentLabeledBinaryViewSceneMaker(
							stage, segments, indexOfSegmentToShow, clasificator);
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
					SimpleSegmentLabeledBinaryViewSceneMaker sm = new SimpleSegmentLabeledBinaryViewSceneMaker(
							stage, segments, indexOfSegmentToShow, clasificator);
					stage.setScene(sm.makeScene());
				} else {
					System.out.println("no more segments");
				}
			}
		});
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
}
