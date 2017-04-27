package edu.utcn.eeg.artifactdetection;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
 
/**
*
* @web http://java-buddy.blogspot.com/
*/
public class BoostrapExemple extends Application {
 
 /**
  * @param args the command line arguments
  */
 public static void main(String[] args) {
     launch(args);
 }
 
 @Override
 public void start(Stage primaryStage) {
     primaryStage.setTitle("java-buddy.blogspot.com");
     Group root = new Group();
     Scene scene = new Scene(root, 400, 300, Color.WHITE);
   
     //Top Menu Bar
     MenuBar menuBar = new MenuBar();
 
     Menu menu1 = new Menu("Menu");
   
     MenuItem menuItemA = new MenuItem("Item A");
     menuItemA.setOnAction(new EventHandler<ActionEvent>() {
       
         @Override public void handle(ActionEvent e) {
             System.out.println("Item A Clicked");
         }
     });
   
     MenuItem menuItemB = new MenuItem("Item B");
     menuItemB.setOnAction(new EventHandler<ActionEvent>() {
       
         @Override public void handle(ActionEvent e) {
             System.out.println("Item B Clicked");
         }
     });
   
     MenuItem menuItemC = new MenuItem("Item C");
     menuItemC.setOnAction(new EventHandler<ActionEvent>() {
       
         @Override public void handle(ActionEvent e) {
             System.out.println("Item C Clicked");
         }
     });
   
     //Sub-Menu
     Menu subMenu_s1 = new Menu("SubMenu 1");
     MenuItem subMenuItem1 = new MenuItem("Sub Menu Item 1");
     MenuItem subMenuItem2 = new MenuItem("Sub Menu Item 2");
     MenuItem subMenuItem3 = new MenuItem("Sub Menu Item 3");
     subMenu_s1.getItems().addAll(subMenuItem1, subMenuItem2, subMenuItem3);
   
     menu1.getItems().add(menuItemA);
     menu1.getItems().add(menuItemB);
     menu1.getItems().add(menuItemC);
     menu1.getItems().add(subMenu_s1);   //Add Sub-Menu
     menuBar.getMenus().add(menu1);
   
     menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
     root.getChildren().add(menuBar);
     primaryStage.setScene(scene);
     primaryStage.show();
 }
}