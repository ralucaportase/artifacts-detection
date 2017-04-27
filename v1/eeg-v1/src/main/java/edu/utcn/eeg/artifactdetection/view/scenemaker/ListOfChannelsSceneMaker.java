package edu.utcn.eeg.artifactdetection.view.scenemaker;

import edu.utcn.eeg.artifactdetection.view.provider.SimpleChannelSegmentProvider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class ListOfChannelsSceneMaker extends AbstractSceneMaker {

	private static final int NR_CHANNELS = 128;

	protected Button[] btnChannels;
	protected Label[] labelChannels;

	public ListOfChannelsSceneMaker(Stage stage) {
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

		btnChannels = new Button[NR_CHANNELS];
		labelChannels = new Label[NR_CHANNELS];
		for (int i = 0; i < NR_CHANNELS; i++) {
			btnChannels[i] = new Button();
			btnChannels[i].setText("Vizualize");
			addActionHandlerForButtonVizualize(btnChannels[i], i + 1);
			labelChannels[i] = new Label("Channel " + i);
			root.getChildren().addAll(labelChannels[i], btnChannels[i]);
		}

		pane1.setContent(root);
		pane1.setPannable(true); // it means that the user should be able to pan
									// the viewport by using the mouse.

		return pane1;
	}

	

	/**
	 * Sets the next view scene by reading the i-th channel !!!!! number of
	 * channels from 1 to 128
	 * 
	 * @param btn
	 * @param nrChannel
	 */
	protected void addActionHandlerForButtonVizualize(Button btn, int nrChannel) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("vizualize");
				SimpleChannelSegmentProvider provider = new SimpleChannelSegmentProvider(nrChannel);
				SimpleSegmentViewSceneMaker sm = new SimpleSegmentViewSceneMaker(stage, provider.provideSegments(), 0);
				stage.setScene(sm.makeScene());

			}
		});
	}

	
}