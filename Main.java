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
			Critter.makeCritter("Critter2");
		launch(args);
		
		
	}
	
	 public int counter=1;
	 public int totalcount=0;
	 public TextField userTextField;
	 public AnchorPane anchorPane = new AnchorPane();
	 @SuppressWarnings("static-access")
	public void start(Stage primaryStage) {

	userTextField = new TextField(counter+ " ");
   Button btn0 = new Button();
	btn0.setText("Click to Increment");
    btn0.setOnAction(new EventHandler<ActionEvent>() {
   	 
        @Override
        public void handle(ActionEvent event) {
            counter++;
            userTextField = new TextField(counter+ " ");
            anchorPane.getChildren().remove(6);
            anchorPane.getChildren().add(6,userTextField);
            anchorPane.setBottomAnchor(userTextField,20.0);
            anchorPane.setRightAnchor(userTextField,400.0);
        }
    });
    
	Button btn1 = new Button();
    btn1.setText("Quit");
    btn1.setOnAction(new EventHandler<ActionEvent>() {
    	public void handle(ActionEvent event) {
    		System.exit(0);
    	}
    });
    
    Button btn2 = new Button();
    btn2.setText("Show");
    btn2.setOnAction(new EventHandler<ActionEvent>(){
    	public void handle(ActionEvent event){

    		List<Critter> pop = Critter.getPopulation();
    		System.out.println(pop.size());
    		for(Critter c:pop)
    		{
    			double xTBP=100;
    			double yTBP=750;
    			yTBP-=(c.getYCoord()*6);
    			xTBP+=(c.getXCoord()*4);
    			if(c.viewShape().toString().equalsIgnoreCase("circle"))
    			{
    				Circle circle = new Circle(5, c.viewColor());
    				circle.setFill(c.viewFillColor());
    				circle.setStroke(c.viewOutlineColor());
    				anchorPane.getChildren().add(circle);
    				anchorPane.setBottomAnchor(circle, yTBP);
    			 	anchorPane.setLeftAnchor(circle, xTBP);
    			}
    			else if(c.viewShape().toString().equalsIgnoreCase("square"))
    			{
    				Rectangle square = new Rectangle(5.0,5.0,c.viewColor());
    				square.setFill(c.viewFillColor());
    				square.setStroke(c.viewOutlineColor());
    				anchorPane.getChildren().add(square);
    				anchorPane.setBottomAnchor(square, yTBP);
    			 	anchorPane.setLeftAnchor(square, xTBP);
    			}
    			else if(c.viewShape().toString().equalsIgnoreCase("triangle"))
    			{
    				Polygon triangle = new Polygon();
    				triangle.setFill(c.viewFillColor());
    				triangle.setStroke(c.viewOutlineColor());
    				triangle.getPoints().addAll(new Double[]{
    				    0.0, 0.0,
    				    5.0, 0.0,
    				    2.5, 5.0 });
    				anchorPane.getChildren().add(triangle);
    				anchorPane.setBottomAnchor(triangle, yTBP);
    			 	anchorPane.setLeftAnchor(triangle, xTBP);
    			}
    			else if(c.viewShape().toString().equalsIgnoreCase("diamond"))
    			{
    				Polygon diamond = new Polygon();
    				diamond.setFill(c.viewFillColor());
    				diamond.setStroke(c.viewOutlineColor());
    				diamond.getPoints().addAll(new Double[]{
    				    0.0, 2.5,
    				    2.5, 5.0,
    				    5.0, 2.5,
    				    2.5, 0.0 });
    				anchorPane.getChildren().add(diamond);
    				anchorPane.setBottomAnchor(diamond, yTBP);
    			 	anchorPane.setLeftAnchor(diamond, xTBP);
    			}
    			else
    			{
    				//TODO: Implement star shape
    			}
    		}
    		
    	}
    });
    
    Button btn3 = new Button();
    btn3.setText("Step");
    btn3.setOnAction(new EventHandler<ActionEvent>(){
    	public void handle(ActionEvent event){
    		for(int i=0;i<counter;i++)
    		{Critter.worldTimeStep();}
    		counter=1;
            userTextField = new TextField(counter+ " ");
            anchorPane.getChildren().remove(6);
            anchorPane.getChildren().add(6,userTextField);
            anchorPane.setBottomAnchor(userTextField,20.0);
            anchorPane.setRightAnchor(userTextField,400.0);
            totalcount++;
    	}
    });
    
    Button btn4 = new Button();
    btn4.setText("Seed");
    Button btn5 = new Button();
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
	GridPane gridPane = new GridPane();
	gridPane.setGridLinesVisible(true);
	gridPane.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");

    anchorPane.getChildren().addAll(btn1,btn2,btn3,btn4,btn5,btn0,userTextField);
	for(int i=0;i<100;i++)
	{
		Line line = new Line();
		line.setStartX(100.0);
		line.setStartY(i*6);
		line.setEndX(700.0);
		line.setEndY(i*6);
		anchorPane.getChildren().add(line);
	}
	for(int i=0;i<100;i++)
	{
		Line line = new Line();
		line.setStartX(100.0+i*6);
		line.setStartY(0);
		line.setEndX(100+i*6);
		line.setEndY(600);
		anchorPane.getChildren().add(line);
	}
    anchorPane.setBottomAnchor(btn1,100.0);
    anchorPane.setBottomAnchor(btn2,100.0);
    anchorPane.setBottomAnchor(btn3,100.0);
    anchorPane.setBottomAnchor(btn4,100.0);
    anchorPane.setBottomAnchor(btn5,100.0);
    anchorPane.setRightAnchor(btn1,200.0);
    anchorPane.setRightAnchor(btn2,300.0);
    anchorPane.setRightAnchor(btn3,400.0);
    anchorPane.setRightAnchor(btn4,500.0);
    anchorPane.setRightAnchor(btn5,600.0);
    anchorPane.setBottomAnchor(btn0,50.0);
    anchorPane.setRightAnchor(btn0,400.0);
    anchorPane.setBottomAnchor(userTextField,20.0);
    anchorPane.setRightAnchor(userTextField,400.0);
    anchorPane.setBottomAnchor(gridPane, 200.0);
    anchorPane.setRightAnchor(gridPane, 700.0);
   primaryStage.setScene(new Scene(anchorPane, 800, 800));
   primaryStage.show();

	 }

}
