package edu.utcn.eeg.artifactdetection.view.scenemaker;

import edu.utcn.eeg.artifactdetection.model.MultiChannelSegmentSelector;
import edu.utcn.eeg.artifactdetection.view.provider.MultipleSegmentProvider;
import edu.utcn.eeg.artifactdetection.view.provider.SimpleChannelSegmentProvider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListOfRegionsSceneMaker extends AbstractSceneMaker {

	private static final int NR_CHANNELS = 4;

	protected Button[] btnRegions;
	protected Label[] labelRegions;
	
	public ListOfRegionsSceneMaker(Stage stage) {
		super(stage);
	}

	@Override
	public Scene makeScene() {
		VBox hBox = new VBox();
		hBox.getChildren().addAll(createMenuBar(),getInitialPane());
		Scene scene = new Scene(hBox, LENGTH_STAGE, HIGH_STAGE);
		return scene;
	}

	private ScrollPane getInitialPane() {

		ScrollPane pane1 = new ScrollPane();
		VBox root = new VBox();
		root.setSpacing(10);
		root.setPadding(new Insets(10));

		btnRegions = new Button[NR_CHANNELS];
		labelRegions = new Label[NR_CHANNELS];
		for (int i = 0; i < NR_CHANNELS; i++) {
			btnRegions[i] = new Button();
			btnRegions[i].setText("Vizualize");
			addActionHandlerForButtonVizualize(btnRegions[i], i );
			labelRegions[i] = new Label("Region " + i);
			root.getChildren().addAll(labelRegions[i], btnRegions[i]);
		}

		pane1.setContent(root);
		pane1.setPannable(true); // it means that the user should be able to pan
									// the viewport by using the mouse.

		return pane1;
	}

	

	/**
	 * Sets the next view scene by reading the from a region
	 * The region is defined by nrRegion
	 * 
	 * @param btn
	 * @param nrRegion
	 */
	protected void addActionHandlerForButtonVizualize(Button btn, int nrRegion) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("vizualize");
				MultipleSegmentProvider provider = new MultipleSegmentProvider(MultiChannelSegmentSelector.A);
				MultipleSegmentViewSceneMaker sm = new MultipleSegmentViewSceneMaker(stage, provider.provideSegments(), 0);
				stage.setScene(sm.makeScene());

			}
		});
	}
}
