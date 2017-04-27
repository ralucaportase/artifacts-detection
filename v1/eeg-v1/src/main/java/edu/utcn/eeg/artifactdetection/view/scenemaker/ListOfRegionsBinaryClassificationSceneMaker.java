package edu.utcn.eeg.artifactdetection.view.scenemaker;

import edu.utcn.eeg.artifactdetection.model.MultiChannelSegmentSelector;
import edu.utcn.eeg.artifactdetection.view.provider.MultipleSegmentProvider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ListOfRegionsBinaryClassificationSceneMaker extends ListOfRegionsSceneMaker{

	public ListOfRegionsBinaryClassificationSceneMaker(Stage stage) {
		super(stage);
	}

	protected void addActionHandlerForButtonVizualize(Button btn, int nrRegion) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				MultipleSegmentProvider provider = new MultipleSegmentProvider(MultiChannelSegmentSelector.A);
				MultipleSegmentLabeledBinaryViewSceneMaker sm = new MultipleSegmentLabeledBinaryViewSceneMaker(stage, provider.provideSegments(), 0);
				stage.setScene(sm.makeScene());

			}
		});
	}
}
