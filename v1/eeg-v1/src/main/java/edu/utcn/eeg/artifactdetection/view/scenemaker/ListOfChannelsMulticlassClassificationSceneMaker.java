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
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ListOfChannelsMulticlassClassificationSceneMaker extends ListOfChannelsSceneMaker {

	public ListOfChannelsMulticlassClassificationSceneMaker(Stage stage) {
		super(stage);
		
	}

	protected void addActionHandlerForButtonVizualize(Button btn, int nrChannel) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("vizualize classify with MULTICLASS");
				System.out.println("Elena , I am trying!");
				SimpleChannelSegmentProvider provider = new SimpleChannelSegmentProvider(nrChannel);
				Classifier dt = new DecisionTreeClassifier();
				List<Segment> testSegm=	provider.provideSegments(nrChannel);
				List<Segment> classifiedSegments = dt.classifySegments(testSegm);
				System.out.println("Elena , I was HERE!");
				
				SimpleSegmentLabeldViewSceneMaker sm = new SimpleSegmentLabeldViewSceneMaker(stage,
						classifiedSegments, 0);
				stage.setScene(sm.makeScene());

			}
		});
	}

	
}
