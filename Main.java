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

import javax.swing.JTextArea;

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
		for(int i=0;i<12;i++)
			Critter.makeCritter("Critter1");
			Critter.makeCritter("Critter2");
			Critter.makeCritter("Critter3");
			Critter.makeCritter("Critter4");
		launch(args);
	}
	
	
	 public int counter=1;
	 public int totalcount=0;
	 public AnchorPane anchorPane = new AnchorPane();

	 public boolean anFlag=false;
	
	 
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
	Button anBtn= new Button();
	components.add(anBtn);

	TextField anTxt = new TextField();
	anTxt.setPromptText("Number of time steps per second: 1");
	anTxt.setMinWidth(50);
	anTxt.setPrefWidth(250);
	anTxt.setMaxWidth(400);
	components.add(anTxt);
	TextField stepTxt = new TextField();
	stepTxt.setPromptText("Number of time steps : 1");
	stepTxt.setMinWidth(50);
	stepTxt.setPrefWidth(250);
	stepTxt.setMaxWidth(400);
	components.add(stepTxt);
	TextField seedTxt = new TextField();
	seedTxt.setPromptText("Enter random seed");
	seedTxt.setMinWidth(50);
	seedTxt.setPrefWidth(250);
	seedTxt.setMaxWidth(400);
	components.add(seedTxt);
	final TextField runStatsTF = new TextField();
	runStatsTF.setPromptText("This is where stats will be displayed");
	runStatsTF.setMinWidth(50);
	runStatsTF.setPrefWidth(250);
	runStatsTF.setMaxWidth(400);
	components.add(runStatsTF);
	Button runStatsBtn = new Button();
	components.add(runStatsBtn);
	//Text runStatsText = new Text();
	//runStatsText.setEditable(false);
	//name.setPrefColumnCount(10);
//Create grid
	AnchorPane grid = new AnchorPane();
	GridWorld.drawLines(grid);
	anchorPane.setBottomAnchor(grid, 0.0);
	anchorPane.setLeftAnchor(grid, 0.0);
    components.add(grid);

    anBtn.setText("Animation");
    anBtn.setOnAction(new EventHandler<ActionEvent>(){
    	public void handle(ActionEvent event){
    		int sPS = 1;
    		try {
    			sPS = Integer.parseInt(stepTxt.getText());
    			if (sPS < 0) {
    				stepTxt.clear();
    				stepTxt.setPromptText("Not a valid input!");
    				sPS = 0;
    			}
    		}
    		catch (NumberFormatException e) {
    			if (!stepTxt.getText().isEmpty()) {
    				stepTxt.clear();
    				stepTxt.setText("Not a valid input!");
    				sPS = 0;
    			}
    			else {
    				stepTxt.setPromptText("Number of time steps : 1");
    			}
    			
    		}
    		System.out.println(sPS);
    		if(!anFlag)
    			anFlag=true;
    		else
    			anFlag=false;
    		int steps=1;
    		int count=1;
    		while(anFlag&&count<10&&sPS>0)
    		{
    			for(int i=0;i<steps;i++){
    				Critter.worldTimeStep();
    			}

    			anchorPane.getChildren().clear();
    			anchorPane.getChildren().addAll(components);
    			GridWorld.drawCritters(anchorPane);
    			counter=1;
    			totalcount++;
                String s = Critter.runStats(Critter.getPopulation());
                runStatsTF.setPromptText(s);
    			try {
					Thread.sleep(1000/sPS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			count++;
    		}
    	}
    });
    
    runStatsBtn.setText("Run Stats");
    runStatsBtn.setOnAction(new EventHandler<ActionEvent>(){
    	public void handle(ActionEvent event){
    		String s = Critter.runStats(Critter.getPopulation());
    		runStatsTF.setPromptText(s);
    	//	runStatsText.setText(s);
    	}
    });
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
            String s = Critter.runStats(Critter.getPopulation());
            runStatsTF.setPromptText(s);
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
    anchorPane.setBottomAnchor(quitBtn,200.0);
    anchorPane.setBottomAnchor(stepBtn,300.0);
    anchorPane.setBottomAnchor(seedBtn,500.0);
    anchorPane.setBottomAnchor(makeBtn,400.0);
    anchorPane.setBottomAnchor(anBtn,600.0);
    anchorPane.setRightAnchor(quitBtn,300.0);
    anchorPane.setRightAnchor(stepBtn,300.0);
    anchorPane.setRightAnchor(seedBtn,300.0);
    anchorPane.setRightAnchor(makeBtn,300.0);
    anchorPane.setRightAnchor(anBtn, 300.0);
    anchorPane.setBottomAnchor(anTxt,600.0);
    anchorPane.setRightAnchor(anTxt,25.0);
    anchorPane.setBottomAnchor(stepTxt,300.0);
    anchorPane.setRightAnchor(stepTxt,25.0);
    anchorPane.setBottomAnchor(seedTxt, 500.0);
    anchorPane.setRightAnchor(seedTxt,25.0);
    anchorPane.setRightAnchor(runStatsTF, 25.0);
    anchorPane.setBottomAnchor(runStatsTF, 350.0);
    anchorPane.setRightAnchor(runStatsBtn,300.0);
    anchorPane.setBottomAnchor(runStatsBtn,350.0);
   // anchorPane.setRightAnchor(runStatsText, 200.0);
   // anchorPane.setBottomAnchor(runStatsText, 200.0);
    Scene scene = new Scene(anchorPane, 1000, 650);
   primaryStage.setScene(scene);
   primaryStage.show();

	 }

	 
}
