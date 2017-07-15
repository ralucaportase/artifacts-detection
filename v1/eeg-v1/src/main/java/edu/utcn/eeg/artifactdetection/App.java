package edu.utcn.eeg.artifactdetection;

import edu.utcn.eeg.artifactdetection.helpers.LoggerUtil;
import edu.utcn.eeg.artifactdetection.view.scenemaker.WelcomeSceneMaker;
import javafx.application.Application;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class App extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("EEG Processor");
		WelcomeSceneMaker sm = new WelcomeSceneMaker(stage);
		stage.setScene(sm.makeScene());
		stage.show();
	}

	public static void main(String[] args) throws Exception {
		LoggerUtil.configure();
		launch(args);
	}
}

