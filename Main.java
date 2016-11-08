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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
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
	 public AnchorPane anchorPane = new AnchorPane();
	
	 
	 @SuppressWarnings("static-access")
	public void start(Stage primaryStage) {
		 
//Sets the color of the anchorPane to burnt orange
	anchorPane.setStyle("-fx-background-color: #cf5300");
	
//Initialize all of the buttons and other components
	ArrayList<Node> components = new ArrayList<Node>();
	Button quitBtn = new Button();
	components.add(quitBtn);
	Button stepBtn = new Button();
	components.add(stepBtn);
	Button seedBtn = new Button();
	components.add(seedBtn);
	Button makeBtn = new Button();
	components.add(makeBtn);
	TextField stepTxt = new TextField();
	stepTxt.setPromptText("Number of time steps : 1");
	components.add(stepTxt);
	TextField seedTxt = new TextField();
	seedTxt.setPromptText("Enter random seed");
	components.add(seedTxt);
//Create grid
	AnchorPane grid = new AnchorPane();
	GridWorld.drawLines(grid);
	anchorPane.setBottomAnchor(grid, 25.0);
	anchorPane.setLeftAnchor(grid, 25.0);
    components.add(grid);

	quitBtn.setText("Quit");
	quitBtn.setOnAction(new EventHandler<ActionEvent>() {
    	public void handle(ActionEvent event) {
    		System.exit(0);
    	}
    });
    
	
	/*
	 * The step button takes one step by default
	 * The user can use the stepTxt field to change the number of time steps
	 * If their entry is < 0, the user is notified of an invalid input and time is not stepped forward
	 * If their entry is not a number, he user is notified of an invalid input and time is not stepped forward
	 */
    stepBtn.setText("Step");
    stepBtn.setOnAction(new EventHandler<ActionEvent>(){
    	public void handle(ActionEvent event){
    		anchorPane.getChildren().clear();
    		anchorPane.getChildren().addAll(components);
    		int steps = 1;
    		try {
    			steps = Integer.parseInt(stepTxt.getText());
    			if (steps < 0) {
    				stepTxt.clear();
    				stepTxt.setPromptText("Not a valid input!");
    				steps = 0;
    			}
    		}
    		catch (NumberFormatException e) {
    			if (!stepTxt.getText().isEmpty()) {
    				stepTxt.clear();
    				stepTxt.setText("Not a valid input!");
    				steps = 0;
    			}
    			else {
    				stepTxt.setPromptText("Number of time steps : 1");
    			}
    			
    		}
    		
    		for(int i=0;i<steps;i++){
    			Critter.worldTimeStep();
    		}
    		GridWorld.drawCritters(anchorPane);
    		counter=1;
            totalcount++;
    	}
    });
    
    seedBtn.setText("Seed");
    seedBtn.setOnAction(new EventHandler<ActionEvent>(){
    	public void handle(ActionEvent event){
    		try {
    			long seed = Long.parseLong(seedTxt.getText());
    			Critter.setSeed(seed);
    			seedTxt.setPromptText("Current seed : " + Long.toString(seed));
    			seedTxt.clear();
    		}
    		catch (NumberFormatException e) {
    			if (!seedTxt.getText().isEmpty()) {
    				seedTxt.clear();
    				seedTxt.setPromptText("Invalid: only #s");
    			}

    		}
    	}
    });
    makeBtn.setText("Make");
    
//    btn0.setOnAction(new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent event) {
//            counter++;
//            userTextField = new TextField(counter+ " ");
//            anchorPane.getChildren().get(6);
//            = new TextField(counter+ " ");
//        }
//    });
    
    anchorPane.getChildren().addAll(components);

	GridWorld.drawCritters(anchorPane);
    anchorPane.setBottomAnchor(quitBtn,100.0);
    anchorPane.setBottomAnchor(stepBtn,300.0);
    anchorPane.setBottomAnchor(seedBtn,500.0);
    anchorPane.setBottomAnchor(makeBtn,400.0);
    anchorPane.setRightAnchor(quitBtn,200.0);
    anchorPane.setRightAnchor(stepBtn,200.0);
    anchorPane.setRightAnchor(seedBtn,200.0);
    anchorPane.setRightAnchor(makeBtn,200.0);
    anchorPane.setBottomAnchor(stepTxt,300.0);
    anchorPane.setRightAnchor(stepTxt,35.0);
    anchorPane.setBottomAnchor(seedTxt, 500.0);
    anchorPane.setRightAnchor(seedTxt,35.0);
   primaryStage.setScene(new Scene(anchorPane, 1000, 650));
   primaryStage.show();

	 }

	 
}
