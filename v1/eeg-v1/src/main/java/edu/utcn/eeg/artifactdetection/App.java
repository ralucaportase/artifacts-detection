package edu.utcn.eeg.artifactdetection;

import edu.utcn.eeg.artifactdetection.view.scenemaker.InitialSceneMaker;
import javafx.application.Application;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class App extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("EEG Processor");
		InitialSceneMaker sm = new InitialSceneMaker(stage);
		stage.setScene(sm.makeScene());
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

