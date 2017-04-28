package edu.utcn.eeg.artifactdetection.view.scenemaker;

import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public abstract class AbstractSceneMaker {

	protected final static int LENGTH_STAGE = 950;
	protected final static int HIGH_STAGE = 650;
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

	protected MenuBar createMenuBar() {
		MenuBar menuBar = new MenuBar();

		Menu menu1 = new Menu("Load file");
		Menu menu2 = new Menu("Vizualize EEG");
		Menu menu3 = new Menu("Extract artefact from EEG");

		MenuItem menuItem11 = new MenuItem("Load from D://EEG//Data");
		menuItem11.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				System.out.println("load a file from D");
			}
		});

		MenuItem menuItem12 = new MenuItem("Load from specified path");
		menuItem12.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				System.out.println("load from specified path");
			}
		});
		menu1.getItems().add(menuItem11);
		menu1.getItems().add(menuItem12);

		MenuItem menuItem21 = new MenuItem("Single channel processing");
		menuItem21.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				ListOfChannelsSceneMaker sm = new ListOfChannelsSceneMaker(stage);
				stage.setScene(sm.makeScene());
			}
		});

		MenuItem menuItem22 = new MenuItem("Multiple channel processing");
		menuItem22.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				System.out.println("Vizualize Multiple channel processing");
				ListOfRegionsSceneMaker sm = new ListOfRegionsSceneMaker(stage);
				stage.setScene(sm.makeScene());
			}
		});
		menu2.getItems().add(menuItem21);
		menu2.getItems().add(menuItem22);

		Menu menu31 = new Menu("Single channel processing");
		MenuItem menuItem311 = new MenuItem("Binary classification");
		menuItem311.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				ListOfChannelsBinaryClassificationSceneMaker sm = new ListOfChannelsBinaryClassificationSceneMaker(
						stage);
				stage.setScene(sm.makeScene());
			}
		});
		Menu menu312 = new Menu("Multiclass classification");
		MenuItem menuItem3121 = new MenuItem("KNN");
		menuItem3121.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
						stage);
				stage.setScene(sm.makeScene());
				System.out.println("Knn");
			}
		});
		MenuItem menuItem3122 = new MenuItem("Decizion tree");
		menuItem3122.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
						stage);
				stage.setScene(sm.makeScene());
				System.out.println("DT");
			}
		});
		menu312.getItems().addAll(menuItem3121, menuItem3122);
		menu31.getItems().addAll(menuItem311, menu312);

		Menu menu32 = new Menu("Multiple channel processing");
		MenuItem menuItem321 = new MenuItem("Binary classification");
		menuItem321.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				ListOfRegionsBinaryClassificationSceneMaker sm = new ListOfRegionsBinaryClassificationSceneMaker(
						stage);
				stage.setScene(sm.makeScene());
			}
		});
		Menu menu322 = new Menu("Multiclass clasification");
		MenuItem menuItem3221 = new MenuItem("KNN");
		menuItem3221.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				System.out.println("multi multi knn");
				ListOfRegionMulticlassClassificationSceneMaker sm = new ListOfRegionMulticlassClassificationSceneMaker(
						stage);
				stage.setScene(sm.makeScene());
			}
		});
		MenuItem menuItem3222 = new MenuItem("Decizion tree");
		menuItem3222.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				ListOfRegionMulticlassClassificationSceneMaker sm = new ListOfRegionMulticlassClassificationSceneMaker(
						stage);
				stage.setScene(sm.makeScene());
				System.out.println("multi DT");
			}
		});
		menu322.getItems().addAll(menuItem3221, menuItem3222);
		menu32.getItems().addAll(menuItem321, menu322);

		menu3.getItems().addAll(menu31, menu32);

		menuBar.getMenus().addAll(menu1, menu2, menu3);
		menuBar.prefWidthProperty().bind(stage.widthProperty());
		menuBar.prefHeight(HIGH_STAGE / 20);
		menuBar.setMaxHeight(HIGH_STAGE / 10);
		return menuBar;
	}

}
