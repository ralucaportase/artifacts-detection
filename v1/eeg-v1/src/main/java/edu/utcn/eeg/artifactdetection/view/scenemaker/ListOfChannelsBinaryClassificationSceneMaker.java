package edu.utcn.eeg.artifactdetection.view.scenemaker;

import java.util.List;

import edu.utcn.eeg.artifactdetection.classifier.Classifier;
import edu.utcn.eeg.artifactdetection.classifier.decisiontree.DecisionTreeClassifier;
import edu.utcn.eeg.artifactdetection.classifier.svm.SvmClassifier;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.view.provider.SimpleChannelSegmentProvider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ListOfChannelsBinaryClassificationSceneMaker extends ListOfChannelsSceneMaker {

	public ListOfChannelsBinaryClassificationSceneMaker(Stage stage) {
		super(stage);

	}

	protected void addActionHandlerForButtonVizualize(Button btn) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("vizualize classify with SVM");

				int regionIdx = getRegionComboBoxValue();
				int channelIdx = Integer.parseInt(channelComboBox.getValue().toString());
				int nrChannel = channelIdx + regionIdx * 32;
				System.out.println(channelIdx + " " + regionIdx + " Channel " + nrChannel);
				SimpleChannelSegmentProvider provider = new SimpleChannelSegmentProvider(nrChannel);
				Classifier svm = new SvmClassifier();
				List<Segment> testSegm = provider.provideSegments(nrChannel);
				List<Segment> classifiedSegments = svm.classifySegments(testSegm);

				SimpleSegmentLabeldViewSceneMaker sm = new SimpleSegmentLabeldViewSceneMaker(stage, svm,
						classifiedSegments, 0);
				stage.setScene(sm.makeScene());
			}
		});
	}

}
