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

				int regionIdx = getRegionComboBoxValue();
				int channelIdx;
				if (channelComboBox.getValue() != null) {
					channelIdx = Integer.parseInt(channelComboBox.getValue().toString());
				} else {
					channelIdx = 0;
				}
				int nrChannel = channelIdx + regionIdx * 32;
				System.out.println(channelIdx + " " + regionIdx + " " + nrChannel);
				SimpleChannelSegmentProvider provider = new SimpleChannelSegmentProvider(nrChannel);
				List<Segment> testSegm = provider.provideSegments(nrChannel);
				System.out.println(testSegm);
				if (testSegm == null) {
					System.out.println("list of segments null");
					errorLabel.setText("Segments from that channel are not available!");
				} else {

					List<Segment> classifiedSegments = clasiffier.classifySegments(testSegm);
					if (clasiffier == null) {
						System.out.println("classifier null");
						errorLabel.setText("Choose a classifier!");
					} else {
						SimpleSegmentLabeldViewSceneMaker sm = new SimpleSegmentLabeldViewSceneMaker(stage, clasiffier,
								classifiedSegments, 0, 1);
						stage.setScene(sm.makeScene());
					}
				}
			}
		});
	}

}
