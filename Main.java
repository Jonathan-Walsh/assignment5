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

import com.sun.org.apache.bcel.internal.util.ClassPath;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.DragEvent;
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
		for(int i=0;i<12;i++) {
			Critter.makeCritter("Critter1");
			Critter.makeCritter("Critter2");
			Critter.makeCritter("Critter3");
			Critter.makeCritter("Critter4");
		}
		launch(args);
	}
	
	
	 public int counter=1;
	 public int totalcount=0;
	 public static AnchorPane anchorPane = new AnchorPane();

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
	final ComboBox<String> comboBox = new ComboBox<String>();
	Class<?>[] types = Critter.class.getClasses();
	for (Class c: types) {
		System.out.println(c.toString());
	}
	comboBox.getItems().addAll(
			"Craig",
			"Algae",
			"Critter1",
			"Critter2",
			"Critter3",
			"Critter4",
			"AlgaephobicCritter");
	comboBox.setPromptText("Critter Type");
    comboBox.setEditable(true);
    comboBox.setPrefWidth(225);
    comboBox.setMinWidth(50);
    comboBox.setMaxWidth(400);
    comboBox.setEditable(false);
	components.add(comboBox);
	Label anTxt = new Label();
	anTxt.setText("Animation Speed :");
	anTxt.setMinWidth(50);
	anTxt.setPrefWidth(225);
	anTxt.setMaxWidth(400);
	anTxt.setTextFill(Color.WHITE);
	components.add(anTxt);
	TextField makeTxtN = new TextField();
	makeTxtN.setMinWidth(50);
	makeTxtN.setPrefWidth(225);
	makeTxtN.setMaxWidth(400);
	components.add(makeTxtN);
	Label makeTxtNL = new Label();
	makeTxtNL.setText("Enter number of Critters : ");
	makeTxtNL.setMinWidth(50);
	makeTxtNL.setPrefWidth(225);
	makeTxtNL.setMaxWidth(400);
	makeTxtNL.setTextFill(Color.WHITE);
	components.add(makeTxtNL);
	TextField stepTxt = new TextField();
	stepTxt.setMinWidth(50);
	stepTxt.setPrefWidth(225);
	stepTxt.setMaxWidth(400);
	components.add(stepTxt);
	Label stepTxtL = new Label();
	stepTxtL.setText("Enter number of time steps :");
	stepTxtL.setMinWidth(50);
	stepTxtL.setPrefWidth(225);
	stepTxtL.setMaxWidth(400);
	stepTxtL.setTextFill(Color.WHITE);
	components.add(stepTxtL);
	TextField seedTxt = new TextField();
	//seedTxt.setPromptText("Enter random seed");
	seedTxt.setMinWidth(50);
	seedTxt.setPrefWidth(225);
	seedTxt.setMaxWidth(400);
	components.add(seedTxt);
	Label seedTxtL = new Label();
	seedTxtL.setText("Enter random seed : ");
	seedTxtL.setMinWidth(50);
	seedTxtL.setPrefWidth(225);
	seedTxtL.setMaxWidth(400);
	seedTxtL.setTextFill(Color.WHITE);
	components.add(seedTxtL);
	final TextField runStatsTF = new TextField();
	runStatsTF.setMinWidth(50);
	runStatsTF.setPrefWidth(300);
	runStatsTF.setMaxWidth(400);
	String s = Critter.runStats(Critter.getPopulation());
	runStatsTF.setText(s);
	runStatsTF.setEditable(false);
	components.add(runStatsTF);
	Label runStatsTFL = new Label();
	runStatsTFL.setText("Critter Statistics : ");
	runStatsTFL.setMinWidth(50);
	runStatsTFL.setPrefWidth(225);
	runStatsTFL.setMaxWidth(400);
	runStatsTFL.setTextFill(Color.WHITE);
	components.add(runStatsTFL);
//Slider for animation
	Slider slider = new Slider(0, 10, 1);
	slider.setShowTickLabels(true);
	slider.setShowTickMarks(true);
	slider.setMajorTickUnit(5);
	slider.setMinorTickCount(4);
	slider.setBlockIncrement(1);
	slider.setSnapToTicks(true);
	slider.setStyle("-fx-base: rgb(0, 0, 0)");
	components.add(slider);
//Create grid
	AnchorPane grid = new AnchorPane();
	GridWorld.drawLines(grid);
	anchorPane.setBottomAnchor(grid, 0.0);
	anchorPane.setLeftAnchor(grid, 0.0);
    components.add(grid);

	
	ScheduledService<Void> animation = new ScheduledService<Void>() {;
		protected Task<Void> createTask(){
			return new Task<Void>() {
				@Override
				protected Void call(){
					Platform.runLater(new Runnable() {
						  @Override 
						  public void run() {
							anchorPane.getChildren().clear();
					    	anchorPane.getChildren().addAll(components);
					    	Critter.worldTimeStep();
					    	Critter.displayWorld();
					    	String s = Critter.runStats(Critter.getPopulation());
				    		runStatsTF.setText(s);
						  }
					});
		    		return null;
				}
			};
		}
	};
	animation.setPeriod(Duration.seconds(1));
	
    anBtn.setText("Animate");
    anBtn.setOnAction(new EventHandler<ActionEvent>(){
    	public void handle(ActionEvent event){
    		if (animation.getState() == State.READY){
    			animation.start();
    			for (Node n : components) {
    				if (n instanceof Control && n != anBtn && n != slider) {
    					n.setDisable(true);
    				}
    			}
    			anBtn.setText("Stop");
    		}
    		else if (animation.getState() == State.CANCELLED) {
    			animation.restart();
    			for (Node n : components) {
    				if (n instanceof Control && n != anBtn && n != slider) {
    					n.setDisable(true);
    				}
    			}
    			anBtn.setText("Stop");
    		}
    		else {
    			animation.cancel();
    			for (Node n : components) {
    				if (n instanceof Control && n != anBtn && n != slider) {
    					n.setDisable(false);
    				}
    			}
    			anBtn.setText("Animate");
    		}
    		
    	}
    });
    
    slider.valueProperty().addListener(new ChangeListener<Number>() {
        public void changed(ObservableValue<? extends Number> ov,
            Number old_val, Number new_val) {
        	if ((double) new_val < 1) {
        		animation.setPeriod(Duration.seconds(15));
        	}
        	else {
                animation.setPeriod(Duration.millis(3000.0/(double)new_val));
        	}
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
    stepTxt.setPromptText("Number of time steps : 1");
    stepBtn.setOnAction(new EventHandler<ActionEvent>(){
    	public void handle(ActionEvent event){
    		
    		anchorPane.getChildren().clear();
    		anchorPane.getChildren().addAll(components);
    		int steps = 1;
    		try {
    			steps = Integer.parseInt(stepTxt.getText());
    			stepTxt.clear();
    			stepTxt.setPromptText("Number of time steps : " + Integer.toString(steps));
    			if (steps < 0) {
    				stepTxt.clear();
    				stepTxt.setPromptText("Not a valid input!");
    				steps = 0;
    			}
    		}
    		catch (NumberFormatException e) {
    			if (!stepTxt.getText().isEmpty()) {
    				stepTxt.clear();
    				stepTxt.setPromptText("Not a valid input!");
    				steps = 0;
    			}
    			else {
    				if (stepTxt.getPromptText() == "Not a valid input!") { 
    					stepTxt.setPromptText("Number of time steps : 1");
    				}
    				else {
    					String[] txt = stepTxt.getPromptText().split(" ");
    					steps = Integer.parseInt(txt[txt.length - 1]);
    				}
    			}
    			
    		}
    		
    		for(int i=0;i<steps;i++){
    			Critter.worldTimeStep();
    		}
    		Critter.displayWorld();
    		counter=1;
            totalcount++;
            String s = Critter.runStats(Critter.getPopulation());
            runStatsTF.setText(s);
    	}
    });
    
    seedBtn.setText("Seed");
    seedTxt.setPromptText("Current seed : none");
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
    makeTxtN.setPromptText("Number of Critters : 1");
    makeBtn.setOnAction(new EventHandler<ActionEvent>(){
    	public void handle(ActionEvent event){
    		String s =comboBox.getSelectionModel().getSelectedItem();
    		int numCritters=0;
    		try{
    			numCritters = Integer.parseInt(makeTxtN.getText());
    			makeTxtN.clear();
    			makeTxtN.setPromptText("Number of Critters : " + Integer.toString(numCritters));
    			if (numCritters < 0) {
    				numCritters = 0;
    				makeTxtN.setPromptText("Not a valid input!");
    			}
    		}
    		catch(NumberFormatException e)
    		{
    			if (makeTxtN.getPromptText() == "Not a valid input!") { 
    				makeTxtN.setPromptText("Number of Critters : 1");
    			}
    			else {
    				String[] txt = makeTxtN.getPromptText().split(" ");
    				numCritters = Integer.parseInt(txt[txt.length - 1]);
    			}
    		}
    		for(int i=0;i<numCritters;i++)
    		{
    			try{
    				Critter.makeCritter(s);
    			}
    			catch(InvalidCritterException e)
    			{
    			}
    		}
    		anchorPane.getChildren().clear();
    		anchorPane.getChildren().addAll(components);
    		Critter.displayWorld();
    		String st = Critter.runStats(Critter.getPopulation());
            runStatsTF.setText(st);
    	}
    });
    anchorPane.getChildren().addAll(components);

    Critter.displayWorld();
    anchorPane.setBottomAnchor(quitBtn,25.0);
    anchorPane.setBottomAnchor(stepBtn,350.0);
    anchorPane.setBottomAnchor(seedBtn,500.0);
    anchorPane.setBottomAnchor(makeBtn,450.0);
    anchorPane.setBottomAnchor(anBtn,550.0);
    anchorPane.setRightAnchor(quitBtn,25.0);
    anchorPane.setRightAnchor(stepBtn,265.0);
    anchorPane.setRightAnchor(seedBtn,265.0);
    anchorPane.setRightAnchor(makeBtn,265.0);
    anchorPane.setRightAnchor(anBtn, 265.0);
    anchorPane.setBottomAnchor(anTxt,575.0);
    anchorPane.setRightAnchor(anTxt,25.0);
    anchorPane.setBottomAnchor(slider, 535.0);
    anchorPane.setRightAnchor(slider, 110.0);
    anchorPane.setBottomAnchor(stepTxt,350.0);
    anchorPane.setRightAnchor(stepTxt,25.0);
    anchorPane.setBottomAnchor(stepTxtL,375.0);
    anchorPane.setRightAnchor(stepTxtL,25.0);
    anchorPane.setBottomAnchor(seedTxt, 500.0);
    anchorPane.setRightAnchor(seedTxt,25.0);
    anchorPane.setBottomAnchor(seedTxtL, 525.0);
    anchorPane.setRightAnchor(seedTxtL,25.0);
    anchorPane.setBottomAnchor(comboBox, 450.0);
    anchorPane.setRightAnchor(comboBox, 25.0);
    anchorPane.setBottomAnchor(makeTxtNL, 425.0);
    anchorPane.setRightAnchor(makeTxtNL,25.0);
    anchorPane.setBottomAnchor(makeTxtN, 400.0);
    anchorPane.setRightAnchor(makeTxtN,25.0);
    anchorPane.setLeftAnchor(runStatsTF, 680.0);
    anchorPane.setBottomAnchor(runStatsTF, 200.0);
    anchorPane.setRightAnchor(runStatsTFL, 100.0);
    anchorPane.setBottomAnchor(runStatsTFL, 225.0);


   primaryStage.setScene(new Scene(anchorPane, 1000, 650));
   primaryStage.show();

	 }

	public String getStats() {
		String stats = new String();
		stats+=("" + Critter.getPopulation().size() + " critters as follows -- \n");
		return stats;
	}
	 
}