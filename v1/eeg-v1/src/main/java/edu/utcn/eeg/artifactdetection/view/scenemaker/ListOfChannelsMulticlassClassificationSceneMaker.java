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

	private Classifier clasiffier;

	public ListOfChannelsMulticlassClassificationSceneMaker(Stage stage, Classifier clasiffier) {
		super(stage);
		this.clasiffier = clasiffier;

	}

	public Classifier getClasiffier() {
		return clasiffier;
	}

	public void setClasiffier(Classifier clasiffier) {
		this.clasiffier = clasiffier;
	}

	@SuppressWarnings("restriction")
	protected void addActionHandlerForButtonVizualize(Button btn) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("vizualize classify with MULTICLASS");
				System.out.println("Elena , I am trying!");

				int regionIdx = getRegionComboBoxValue();
				int channelIdx = Integer.parseInt(channelComboBox.getValue().toString());
				int nrChannel = channelIdx + regionIdx * 32;
				System.out.println(channelIdx + " " + regionIdx + " " + nrChannel);
				SimpleChannelSegmentProvider provider = new SimpleChannelSegmentProvider(nrChannel);
				List<Segment> testSegm = provider.provideSegments(nrChannel);
				List<Segment> classifiedSegments = clasiffier.classifySegments(testSegm);

				SimpleSegmentLabeldViewSceneMaker sm = new SimpleSegmentLabeldViewSceneMaker(stage,clasiffier, classifiedSegments,
						0);
				stage.setScene(sm.makeScene());
			}
		});
	}

}
