package edu.utcn.eeg.artifactdetection.view.scenemaker;

import java.util.List;

import org.apache.log4j.Logger;

import edu.utcn.eeg.artifactdetection.helpers.LoggerUtil;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.view.provider.SimpleChannelSegmentProvider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListOfChannelsSceneMaker extends AbstractSceneMaker {

	private static final int NR_CHANNELS = 128;
	Logger logger = LoggerUtil.logger(getClass());

	protected Button[] btnChannels;
	protected Label[] labelChannels;
	protected Label errorLabel = new Label("");
	protected ComboBox regionComboBox;
	protected ComboBox channelComboBox;
	final Button visualizeButton = new Button("Visualize!");

	public ListOfChannelsSceneMaker(Stage stage) {
		super(stage);
	}

	@Override
	public Scene makeScene() {
		VBox hBox = new VBox();
		HBox listBox = new HBox();
		listBox.getChildren().addAll(getInitialPane());
		listBox.setAlignment(Pos.BASELINE_CENTER);
		hBox.getChildren().addAll(createMenuBar(), getInitialPane());
		hBox.setMinWidth(stage.getWidth());
		hBox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(hBox, LENGTH_STAGE, HIGH_STAGE);
		scene.getStylesheets().add("file:src/resources/stylesheet.css");
		return scene;
	}

	private ScrollPane getInitialPane() {

		ScrollPane pane1 = new ScrollPane();
		HBox root = new HBox();

		VBox rootB = new VBox();
		HBox hboxB = new HBox();
		Label labelB = new Label("Headset configuration");
		hboxB.setAlignment(Pos.BASELINE_CENTER);
		hboxB.getChildren().addAll(labelB);

		Image image = new Image("file:src/resources/casca.png");
		ImageView iv = new ImageView();
		iv.setFitWidth(600);
		iv.setFitHeight(575);
		iv.setImage(image);

		rootB.getChildren().addAll(hboxB, iv);
		rootB.setSpacing(10);
		rootB.setPadding(new Insets(10));

		Label regionsLabel = new Label("Region ");
		Label channelsLabel = new Label("Channel no ");

		regionComboBox = new ComboBox();
		regionComboBox.getItems().addAll("A", "B", "C", "D");

		// regionComboBox.setPromptText("A");

		channelComboBox = new ComboBox();

		for (int i = 0; i < NR_CHANNELS / 4; i++) {
			channelComboBox.getItems().add(i);
		}
		HBox channelsHBox = new HBox();
		channelsHBox.getChildren().addAll(channelsLabel, channelComboBox);
		channelsHBox.setAlignment(Pos.BASELINE_CENTER);

		addActionHandlerForButtonVizualize(visualizeButton);

		GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(10, 30, 20, 50));
		grid.add(new Label("Choose channel set"), 0, 0);
		grid.add(new Label(" "), 0, 1);
		grid.add(new Label("Region: "), 0, 2);
		grid.add(regionComboBox, 1, 2);
		grid.add(new Label("Channel no:"), 0, 3);
		grid.add(channelComboBox, 1, 3);
		grid.add(visualizeButton, 0, 4);
		grid.add(errorLabel, 0, 5);

		root.getChildren().addAll(grid, rootB);
		root.setAlignment(Pos.CENTER_RIGHT);
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
	protected void addActionHandlerForButtonVizualize(Button btn) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int regionIdx = getRegionComboBoxValue();
				int channelIdx;
				if (channelComboBox.getValue() != null) {
					channelIdx = Integer.parseInt(channelComboBox.getValue()
							.toString());
				} else {
					channelIdx = 0;
				}
				int nrChannel = channelIdx + regionIdx * 32;
				logger.info(channelIdx + " " + regionIdx + " "
						+ nrChannel);
				if (nrChannel >= 72) {
					SimpleChannelSegmentProvider provider = new SimpleChannelSegmentProvider(
							nrChannel);
					List<Segment> testSegm = provider
							.provideSegments(nrChannel);
					logger.info(testSegm);
					if (testSegm.equals(null)) {
						logger.info("list of segments null");
						errorLabel.setText("Channel not available!");
					} else {
						SimpleSegmentViewSceneMaker sm = new SimpleSegmentViewSceneMaker(stage, testSegm, 0);
						stage.setScene(sm.makeScene());
					}
				}
				else errorLabel.setText("Channel not available!");
			}
		});
	}

	int getRegionComboBoxValue() {
		int regionIdx;
		if (regionComboBox.getValue() == null) {
			regionIdx = 0;
		} else {
			switch (regionComboBox.getValue().toString()) {
			case "A":
				regionIdx = 0;
				break;
			case "B":
				regionIdx = 1;
				break;
			case "C":
				regionIdx = 2;
				break;
			case "D":
				regionIdx = 3;
				break;
			default:
				regionIdx = 0;
				break;
			}
		}
		return regionIdx;
	}
}