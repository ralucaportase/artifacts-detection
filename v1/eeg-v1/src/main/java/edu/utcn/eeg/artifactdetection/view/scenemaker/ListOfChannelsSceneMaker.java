package edu.utcn.eeg.artifactdetection.view.scenemaker;

import java.util.List;
import java.util.stream.Collectors;

import edu.utcn.eeg.artifactdetection.classifier.Classifier;
import edu.utcn.eeg.artifactdetection.classifier.decisiontree.DecisionTreeClassifier;
import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.Segment;
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
		HBox listBox =new HBox();
		listBox.getChildren().addAll(getInitialPane());
		listBox.setAlignment(Pos.BASELINE_CENTER);
		hBox.getChildren().addAll(createMenuBar(),getInitialPane());
		hBox.setMinWidth(stage.getWidth());
		hBox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(hBox, LENGTH_STAGE, HIGH_STAGE);
		return scene;
	}

	private ScrollPane getInitialPane() {

		ScrollPane pane1 = new ScrollPane();
		HBox root = new HBox();
		
		VBox rootA = new VBox();
		HBox hboxA = new HBox();
		Label labelA=new Label("Channels group A");
		hboxA.getChildren().addAll(labelA);
		hboxA.setAlignment(Pos.BASELINE_CENTER);
		rootA.getChildren().addAll(hboxA);
		
		VBox rootB = new VBox();
		HBox hboxB = new HBox();
		Label labelB=new Label("Channels group B");
		hboxB.setAlignment(Pos.BASELINE_CENTER);
		hboxB.getChildren().addAll(labelB);
		rootB.getChildren().addAll(hboxB);
		
		VBox rootC = new VBox();
		HBox hboxC = new HBox();
		Label labelC=new Label("Channels group C");
		hboxC.setAlignment(Pos.BASELINE_CENTER);
		hboxC.getChildren().addAll(labelC);
		rootC.getChildren().addAll(hboxC);
		
		VBox rootD = new VBox();
		HBox hboxD = new HBox();
		Label labelD=new Label("Channels group D");
		hboxD.getChildren().addAll(labelD);
		hboxD.setAlignment(Pos.BASELINE_CENTER);
		rootD.getChildren().addAll(hboxD);
		
		rootA.setSpacing(10);
		rootA.setPadding(new Insets(10));
		rootB.setSpacing(10);
		rootB.setPadding(new Insets(10));
		rootC.setSpacing(10);
		rootC.setPadding(new Insets(10));
		rootD.setSpacing(10);
		rootD.setPadding(new Insets(10));

		btnChannels = new Button[NR_CHANNELS];
		labelChannels = new Label[NR_CHANNELS];
		for (int i = 0; i < NR_CHANNELS / 4; i++) {
			HBox hbox = new HBox();
			btnChannels[i] = new Button();
			btnChannels[i].setMinSize(100, 20);
			btnChannels[i].setText("Vizualize");
			addActionHandlerForButtonVizualize(btnChannels[i], i + 1);
			labelChannels[i] = new Label("Channel " + i);
			labelChannels[i].setMinSize(100, 20);
			hbox.getChildren().addAll(labelChannels[i], btnChannels[i]);
			rootA.getChildren().addAll(hbox);
		}
		for (int i = NR_CHANNELS / 4; i < NR_CHANNELS / 2; i++) {
			HBox hbox = new HBox();
			btnChannels[i] = new Button();
			btnChannels[i].setMinSize(100, 20);
			btnChannels[i].setText("Vizualize");
			addActionHandlerForButtonVizualize(btnChannels[i], i + 1);
			labelChannels[i] = new Label("Channel " + i);
			labelChannels[i].setMinSize(100, 20);
			hbox.getChildren().addAll(labelChannels[i], btnChannels[i]);
			rootB.getChildren().addAll(hbox);
		}
		for (int i = NR_CHANNELS / 2; i < NR_CHANNELS / 4 + NR_CHANNELS / 2; i++) {
			HBox hbox = new HBox();
			btnChannels[i] = new Button();
			btnChannels[i].setMinSize(100, 20);
			btnChannels[i].setText("Vizualize");
			addActionHandlerForButtonVizualize(btnChannels[i], i + 1);
			labelChannels[i] = new Label("Channel " + i);
			labelChannels[i].setMinSize(100, 20);
			hbox.getChildren().addAll(labelChannels[i], btnChannels[i]);
			rootC.getChildren().addAll(hbox);
		}
		for (int i = NR_CHANNELS / 4 + NR_CHANNELS / 2; i < NR_CHANNELS; i++) {
			HBox hbox = new HBox();
			btnChannels[i] = new Button();
			btnChannels[i].setMinSize(100, 20);
			btnChannels[i].setText("Vizualize");
			addActionHandlerForButtonVizualize(btnChannels[i], i + 1);
			labelChannels[i] = new Label("Channel " + i);
			labelChannels[i].setMinSize(100, 20);
			hbox.getChildren().addAll(labelChannels[i], btnChannels[i]);
			rootD.getChildren().addAll(hbox);
		}
		root.getChildren().addAll(rootA, rootB, rootC, rootD);
		root.setAlignment(Pos.BASELINE_CENTER);
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
				Classifier dt = new DecisionTreeClassifier();
				List<Segment> testSegm=	provider.provideSegments(nrChannel);	
				List<Segment> classifiedSegments = dt.classifySegments(testSegm);
				System.out.println("Here!");
				SimpleSegmentViewSceneMaker sm = new SimpleSegmentViewSceneMaker(stage, classifiedSegments, 0);
				stage.setScene(sm.makeScene());

			}
		});
	}

}