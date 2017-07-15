package edu.utcn.eeg.artifactdetection.view.scenemaker;

import java.util.List;

import org.apache.log4j.Logger;

import edu.utcn.eeg.artifactdetection.classifier.Classifier;
import edu.utcn.eeg.artifactdetection.helpers.LoggerUtil;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.view.provider.SimpleChannelSegmentProvider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ListOfChannelsMulticlassClassificationSceneMaker extends
		ListOfChannelsSceneMaker {

	private Classifier clasiffier;
	Logger logger = LoggerUtil.logger(getClass());


	public ListOfChannelsMulticlassClassificationSceneMaker(Stage stage,
			Classifier clasiffier) {
		super(stage);
		this.clasiffier = clasiffier;

	}

	public Classifier getClasiffier() {
		return clasiffier;
	}

	public void setClasiffier(Classifier clasiffier) {
		this.clasiffier = clasiffier;
	}

	@Override
	@SuppressWarnings("restriction")
	protected void addActionHandlerForButtonVizualize(Button btn) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				logger.info("vizualize classify with MULTICLASS");

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
					if (testSegm == null) {
						logger.error("list of segments null");
						errorLabel
								.setText("Channel not available!");
					} else {

						List<Segment> classifiedSegments = clasiffier
								.classifySegments(testSegm);
						if (clasiffier == null) {
							logger.error("classifier null");
							errorLabel.setText("Choose a classifier!");
						} else {
							SimpleSegmentLabeldViewSceneMaker sm = new SimpleSegmentLabeldViewSceneMaker(
									stage, clasiffier, classifiedSegments, 0, 1);
							stage.setScene(sm.makeScene());
						}
					}
				}
				else errorLabel.setText("Channel not available!");
			}
		});
	}

}
