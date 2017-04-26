package edu.utcn.eeg.artifactdetection.view.scenemaker;

import javafx.stage.Stage;
import javafx.scene.Scene;

public abstract class AbstractSceneMaker {

	protected final static int LENGTH_STAGE = 1000;
	protected final static int HIGH_STAGE = 800;
	protected Stage stage;

	public AbstractSceneMaker(Stage stage) {
		this.stage = stage;
	}

	public abstract Scene makeScene();

	public static int getLengthStage() {
		return LENGTH_STAGE;
	}

	public static int getHighStage() {
		return HIGH_STAGE;
	}

	
	
}
