/* CRITTERS <Main.java>
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Jonathan Walsh
 * jlw4699
 * 16450
 * Tim Yoder
 * tjy263
 * 16450
 * Slip days used: <0>
 * Fall 2016
 */
package assignment5;


import java.util.Iterator;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.*;

public class Main extends Application {

	public static void main(String[] args) throws InvalidCritterException {
		Critter.makeCritter("Craig");
		for(int i=0;i<20;i++)
			Critter.makeCritter("Critter1");
		launch(args);
	}
	
	
	 public int counter=1;
	 public int totalcount=0;
	 public TextField userTextField;
	 public AnchorPane anchorPane = new AnchorPane();
	 public AnchorPane grid = new AnchorPane();
	 
	 
	 @SuppressWarnings("static-access")
	public void start(Stage primaryStage) {
		 
//Sets the color of the anchorPane to burnt orange
	anchorPane.setStyle("-fx-background-color: #cf5300");
//Initialize all of the buttons
	Button btn0 = new Button();
	Button btn1 = new Button();
	Button btn2 = new Button();
	Button btn3 = new Button();
	Button btn4 = new Button();
	Button btn5 = new Button();
	TextField stepText = new TextField();
	stepText.setPromptText("Number of time steps (Default: 1)");

//Create grid
	GridWorld.drawLines(grid);
	anchorPane.setBottomAnchor(grid, 25.0);
	anchorPane.setLeftAnchor(grid, 25.0);
	
	btn0.setText("Click to Increment");
    btn0.setOnAction(new EventHandler<ActionEvent>() {
   	 
        @Override
        public void handle(ActionEvent event) {
            counter++;
        }
    });
    

    btn1.setText("Quit");
    btn1.setOnAction(new EventHandler<ActionEvent>() {
    	public void handle(ActionEvent event) {
    		System.exit(0);
    	}
    });
    
 
    btn2.setText("Show");
    btn2.setOnAction(new EventHandler<ActionEvent>(){
    	
    	public void handle(ActionEvent event){
    		GridWorld.drawCritters(anchorPane);
    	}
    });
    
 
    btn3.setText("Step");
    btn3.setOnAction(new EventHandler<ActionEvent>(){
    	public void handle(ActionEvent event){
    		anchorPane.getChildren().clear();
    		anchorPane.getChildren().addAll(btn1,btn2,btn3,btn4,btn5,btn0,stepText,grid);
    		int steps = 1;
    		try {
    			steps = Integer.parseInt(stepText.getText());
    		}
    		catch (NumberFormatException e) {
    			//Do nothing
    		}
    		catch (NullPointerException e2) {
    			//Do nothing
    		}
    		for(int i=0;i<steps;i++){
    			Critter.worldTimeStep();
    		}
    		GridWorld.drawCritters(anchorPane);
    		counter=1;
            totalcount++;
    	}
    });
    

    btn4.setText("Seed");

    btn5.setText("Make");
    
//    btn0.setOnAction(new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent event) {
//            counter++;
//            userTextField = new TextField(counter+ " ");
//            anchorPane.getChildren().get(6);
//            = new TextField(counter+ " ");
//        }
//    });
    
    anchorPane.getChildren().addAll(btn1,btn2,btn3,btn4,btn5,btn0,stepText, grid);

	GridWorld.drawCritters(anchorPane);
    anchorPane.setBottomAnchor(btn1,100.0);
    anchorPane.setBottomAnchor(btn2,200.0);
    anchorPane.setBottomAnchor(btn3,300.0);
    anchorPane.setBottomAnchor(btn4,400.0);
    anchorPane.setBottomAnchor(btn5,500.0);
    anchorPane.setRightAnchor(btn1,200.0);
    anchorPane.setRightAnchor(btn2,200.0);
    anchorPane.setRightAnchor(btn3,200.0);
    anchorPane.setRightAnchor(btn4,200.0);
    anchorPane.setRightAnchor(btn5,200.0);
    anchorPane.setBottomAnchor(btn0,300.0);
    anchorPane.setRightAnchor(btn0,50.0);
    anchorPane.setBottomAnchor(stepText,250.0);
    anchorPane.setRightAnchor(stepText,75.0);
   
   primaryStage.setScene(new Scene(anchorPane, 1000, 650));
   primaryStage.show();

	 }

	 
}
