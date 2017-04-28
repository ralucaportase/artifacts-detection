package edu.utcn.eeg.artifactdetection.view.scenemaker;

import edu.utcn.eeg.artifactdetection.view.provider.MultipleSegmentProvider;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WelcomeSceneMaker extends AbstractSceneMaker {

	public WelcomeSceneMaker(Stage stage) {
		super(stage);
	}

	private Button btnLoad;

	@Override
	public Scene makeScene() {

		
		VBox hBox = new VBox();
		//hBox.getChildren().addAll(createMenuBar(), getInitialPane());
		hBox.getChildren().addAll(createMenuBar());
		Scene scene = new Scene(hBox, LENGTH_STAGE, HIGH_STAGE);
		return scene;
	}

	private Pane getInitialPane() {

		btnLoad = new Button();
		btnLoad.setText("Load EEG ");
		addActionHandlerForButton();
		GridPane pane1 = new GridPane();
		pane1.setAlignment(Pos.TOP_CENTER);
		pane1.setHgap(50);
		pane1.setVgap(50);
		pane1.setPadding(new Insets(1, 1, 1, 1));
		pane1.add(btnLoad, 1, 1);
		return pane1;
	}

	private void addActionHandlerForButton() {
		btnLoad.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("load --- nothing here so far");
				//InitialSceneMaker sm = new InitialSceneMaker(stage);
			//	stage.setScene(sm.makeScene());

			}
		});
	}
	
	
}
