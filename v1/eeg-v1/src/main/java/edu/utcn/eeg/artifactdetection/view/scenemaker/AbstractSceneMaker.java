package edu.utcn.eeg.artifactdetection.view.scenemaker;

import org.apache.log4j.Logger;

import edu.utcn.eeg.artifactdetection.classifier.Classifier;
import edu.utcn.eeg.artifactdetection.classifier.decisiontree.DecisionTreeClassifier;
import edu.utcn.eeg.artifactdetection.classifier.knn.KnnClassifier;
import edu.utcn.eeg.artifactdetection.classifier.svm.SvmClassesClassifier;
import edu.utcn.eeg.artifactdetection.helpers.LoggerUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public abstract class AbstractSceneMaker {

	protected final static int LENGTH_STAGE = 950;
	protected final static int HIGH_STAGE = 650;
	protected Stage stage;
	Logger logger = LoggerUtil.logger(getClass());

	
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
		Menu menu2 = new Menu("Visualize EEG");
		Menu menu3 = new Menu("Extract artefact from EEG");

		MenuItem menuItem11 = new MenuItem("Load from D://EEG//Data");
		menuItem11.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				logger.info("load a file from D");
			}
		});

		menu1.getItems().add(menuItem11);

		MenuItem menuItem21 = new MenuItem("Single channel processing");
		menuItem21.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				ListOfChannelsSceneMaker sm = new ListOfChannelsSceneMaker(
						stage);
				stage.setScene(sm.makeScene());
			}
		});

		MenuItem menuItem22 = new MenuItem("Multiple channel processing");
		menuItem22.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				logger.info("Visualize Multiple channel processing");
				ListOfRegionsSceneMaker sm = new ListOfRegionsSceneMaker(stage);
				stage.setScene(sm.makeScene());
			}
		});
		menu2.getItems().add(menuItem21);
		menu2.getItems().add(menuItem22);

		Menu menu31 = new Menu("Single channel processing");
		Menu menuItem311 = new Menu("Binary classification");

		MenuItem menuItem3111 = new MenuItem("SVM");
		menuItem3111.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				ListOfChannelsBinaryClassificationSceneMaker sm = new ListOfChannelsBinaryClassificationSceneMaker(
						stage, "svm");
				stage.setScene(sm.makeScene());
			}
		});
		MenuItem menuItem3112 = new MenuItem("Error correction clasificators");
		menuItem3112.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				ListOfChannelsBinaryClassificationSceneMaker sm = new ListOfChannelsBinaryClassificationSceneMaker(
						stage, "all");
				stage.setScene(sm.makeScene());
			}
		});
		menuItem311.getItems().addAll(menuItem3111, menuItem3112);

		Menu menu312 = new Menu("Multiclass classification");
		MenuItem menuItem3121 = new MenuItem("KNN");
		menuItem3121.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Classifier knn = new KnnClassifier();
				ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
						stage, knn);
				stage.setScene(sm.makeScene());
				logger.info("Knn");
			}
		});
		MenuItem menuItem3122 = new MenuItem("Decision tree");
		menuItem3122.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Classifier dt = new DecisionTreeClassifier();
				ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
						stage, dt);
				stage.setScene(sm.makeScene());
				logger.info("DT");
			}
		});
		MenuItem menuItem3123 = new MenuItem("SVM");
		menuItem3123.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Classifier svm = new SvmClassesClassifier();
				ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
						stage, svm);
				stage.setScene(sm.makeScene());
				logger.info("SVM");
			}
		});
		menu312.getItems().addAll(menuItem3121, menuItem3122, menuItem3123);
		menu31.getItems().addAll(menuItem311, menu312);

		menu3.getItems().addAll(menu31);

		menuBar.getMenus().addAll(menu1, menu2, menu3);
		menuBar.prefWidthProperty().bind(stage.widthProperty());
		menuBar.prefHeight(HIGH_STAGE / 20);
		menuBar.setMaxHeight(HIGH_STAGE / 10);
		return menuBar;
	}

}
