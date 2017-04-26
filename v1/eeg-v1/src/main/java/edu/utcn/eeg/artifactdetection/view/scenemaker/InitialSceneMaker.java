package edu.utcn.eeg.artifactdetection.view.scenemaker;

import edu.utcn.eeg.artifactdetection.view.provider.MultipleSegmentProvider;
import edu.utcn.eeg.artifactdetection.view.provider.SimpleChannelSegmentProvider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class InitialSceneMaker extends AbstractSceneMaker {
	private Button btnMultipleSegmentView;
	private Button btnSampleView;

	public InitialSceneMaker(Stage stage) {
		super(stage);
	}

	@Override
	public Scene makeScene() {
		HBox hBox = new HBox();
		hBox.getChildren().addAll(getInitialPane());
		Scene scene = new Scene(hBox, LENGTH_STAGE, HIGH_STAGE);
		return scene;
	}

	private Pane getInitialPane() {

		btnMultipleSegmentView = new Button();
		btnMultipleSegmentView.setText("Go to multiple channel segment view");
		addActionHandlerForButton();
		btnSampleView = new Button();
		btnSampleView.setText("Go to single channel segment view");
		addActionHandlerForSampleButton();
		GridPane pane1 = new GridPane();
		pane1.setAlignment(Pos.TOP_CENTER);
		pane1.setHgap(50);
		pane1.setVgap(50);
		pane1.setPadding(new Insets(1, 1, 1, 1));
		pane1.add(btnMultipleSegmentView, 0, 0);
		pane1.add(btnSampleView, 0, 1);
		return pane1;
	}

	private void addActionHandlerForButton() {
		btnMultipleSegmentView.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				MultipleSegmentProvider segmentProvider = new MultipleSegmentProvider();
				MultipleSegmentViewSceneMaker sm = new MultipleSegmentViewSceneMaker(stage,
						segmentProvider.provideSegments(), 0);
				stage.setScene(sm.makeScene());

			}
		});
	}

	private void addActionHandlerForSampleButton() {
		btnSampleView.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				SimpleChannelSegmentProvider provider = new SimpleChannelSegmentProvider();
				SimpleSegmentViewSceneMaker sm = new SimpleSegmentViewSceneMaker(stage, provider.provideSegments(), 0);
				stage.setScene(sm.makeScene());

			}
		});
	}
}
