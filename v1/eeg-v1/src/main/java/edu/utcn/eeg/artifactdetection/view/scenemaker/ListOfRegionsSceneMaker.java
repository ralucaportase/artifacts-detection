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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListOfRegionsSceneMaker extends AbstractSceneMaker {

	private static final int NR_REGIONS = 4;
	private static final int NR_REGIONS_LOGICAL = 4;
	private static final String[] regions = new String[] { "A", "B", "C", "D" };
	private static final String[] localRegions = new String[] { "Frontal","Parietal","Occipital","Temporal"};

	protected Button[] btnRegions;
	protected Label[] labelRegions;

	public ListOfRegionsSceneMaker(Stage stage) {
		super(stage);
	}

	@Override
	public Scene makeScene() {
		VBox hBox = new VBox();
		hBox.getChildren().addAll(createMenuBar(), getInitialPane());
		Scene scene = new Scene(hBox, LENGTH_STAGE, HIGH_STAGE);
		scene.getStylesheets().add("file:src/resources/stylesheet.css");
		return scene;
	}

	private ScrollPane getInitialPane() {

		ScrollPane pane1 = new ScrollPane();
		VBox root = new VBox();
		root.setSpacing(10);
		root.setPadding(new Insets(10));

		btnRegions = new Button[NR_REGIONS];
		labelRegions = new Label[NR_REGIONS];
		for (int i = 0; i < NR_REGIONS; i++) {

			HBox hbox = new HBox();
			btnRegions[i] = new Button();
			btnRegions[i].setMinSize(100, 20);
			btnRegions[i].setText("Vizualize");
			addActionHandlerForButtonVizualize(btnRegions[i], i);
			labelRegions[i] = new Label("Region " + regions[i]);
			labelRegions[i].setMinSize(100, 20);
			hbox.getChildren().addAll(labelRegions[i], btnRegions[i]);
			root.getChildren().addAll(hbox);

		}

		pane1.setContent(root);
		pane1.setPannable(true); // it means that the user should be able to pan
									// the viewport by using the mouse.

		return pane1;
	}
	
	

	/**
	 * Sets the next view scene by reading the from a region The region is
	 * defined by nrRegion
	 * 
	 * @param btn
	 * @param nrRegion
	 */
	protected void addActionHandlerForButtonVizualize(Button btn, int nrRegion) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				logger.info("vizualize");
				MultipleSegmentProvider provider = new MultipleSegmentProvider(MultiChannelSegmentSelector.A);
				MultipleSegmentViewSceneMaker sm = new MultipleSegmentViewSceneMaker(stage, provider.provideSegments(),
						0);
				stage.setScene(sm.makeScene());

			}
		});
	}
}
