package edu.utcn.eeg.artifactdetection.view.scenemaker;

import java.util.List;

import edu.utcn.eeg.artifactdetection.classifier.Classifier;
import edu.utcn.eeg.artifactdetection.classifier.decisiontree.DecisionTreeClassifier;
import edu.utcn.eeg.artifactdetection.classifier.knn.KnnClassifier;
import edu.utcn.eeg.artifactdetection.classifier.svm.SvmBinaryClassifier;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.postprocessing.ResultsValidator;
import edu.utcn.eeg.artifactdetection.view.provider.SimpleChannelSegmentProvider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ListOfChannelsBinaryClassificationSceneMaker extends
		ListOfChannelsSceneMaker {

	private String clasificator;

	public ListOfChannelsBinaryClassificationSceneMaker(Stage stage,
			String clasificator) {
		super(stage);
		this.clasificator = clasificator;
	}

	protected void addActionHandlerForButtonVizualize(Button btn) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("vizualize classify with " + clasificator);

				int regionIdx = getRegionComboBoxValue();
				int channelIdx = Integer.parseInt(channelComboBox.getValue()
						.toString());
				int nrChannel = channelIdx + regionIdx * 32;
				System.out.println(channelIdx + " " + regionIdx + " Channel "
						+ nrChannel);
				SimpleChannelSegmentProvider provider = new SimpleChannelSegmentProvider(
						nrChannel);

				List<Segment> testSegm = provider.provideSegments(nrChannel);
				if (testSegm == null) {
					System.out.println("list of segments null");
					errorLabel.setText("Segments from that channel are not available!");
				} else {
					List<Segment> classifiedSegments = getCorrespondinClasifiedSegments(testSegm);
					if (clasificator == null) {
						System.out.println("classifier null");
						errorLabel.setText("Choose a classifier!");
					} else {
						SimpleSegmentLabeldViewSceneMaker sm = new SimpleSegmentLabeledBinaryViewSceneMaker(stage,
								classifiedSegments, 0, clasificator);
						stage.setScene(sm.makeScene());
					}
				}
			}
		});
	}

	private List<Segment> getCorrespondinClasifiedSegments(
			List<Segment> testSegm) {
		Classifier svm = new SvmBinaryClassifier();
		List<Segment> classifiedSegments;
		List<Segment> svmSegments = svm.classifySegments(testSegm);
		if (clasificator.equalsIgnoreCase("all")) {
			Classifier knn = new KnnClassifier();
			List<Segment> knnSegments = knn.classifySegments(testSegm);
			Classifier dt = new DecisionTreeClassifier();
			List<Segment> dtSegments = dt.classifySegments(testSegm);
			ResultsValidator validator = new ResultsValidator();
			classifiedSegments = validator.validateClassificationResults(
					svmSegments, knnSegments, dtSegments);
		} else
			classifiedSegments = svmSegments;
		return classifiedSegments;
	}

}
