package edu.utcn.eeg.artifactdetection.view.scenemaker;

import edu.utcn.eeg.artifactdetection.view.provider.SimpleChannelSegmentProvider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ListOfChannelsBinaryClassificationSceneMaker extends ListOfChannelsSceneMaker {

	public ListOfChannelsBinaryClassificationSceneMaker(Stage stage) {
		super(stage);
		
	}

	protected void addActionHandlerForButtonVizualize(Button btn, int nrChannel) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("vizualize classify with bynary");
				SimpleChannelSegmentProvider provider = new SimpleChannelSegmentProvider(nrChannel);
				SimpleSegmentLabeldViewSceneMaker sm = new SimpleSegmentLabeledBinaryViewSceneMaker(stage,
						provider.provideSegments(nrChannel), 0);
				stage.setScene(sm.makeScene());

			}
		});
	}

	
}
