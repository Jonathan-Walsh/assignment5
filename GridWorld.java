/* CRITTERS <GridWorld.java>
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

import java.util.List;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * GridWorld.java
 * @author Jonathan Walsh, Tim Yoder
 * This class provides static methods that display the grid and the
 *    Critters on the grid
 */

public class GridWorld {

	/**
	 * This method draws the lines used as an outline for the 'world'
	 * @param anchorPane : the AnchorPane used in our Main.java
	 *    to hold all of the different items
	 */
	public static void drawLines(AnchorPane anchorPane) {
		Rectangle background = new Rectangle(600, 600);
		background.setFill(Color.WHITE);
		anchorPane.getChildren().add(background);
		AnchorPane.setBottomAnchor(background, 1.0);
		AnchorPane.setLeftAnchor(background, 0.0);
		for(int i=0;i<101;i++)
		{
			Line line = new Line();
			line.setStartX(0.0);
			line.setStartY(i*6);
			line.setEndX(600.0);
			line.setEndY(i*6);
			anchorPane.getChildren().add(line);
		}
		for(int i=0;i<101;i++)
		{
			Line line = new Line();
			line.setStartX(i*6);
			line.setStartY(0);
			line.setEndX(i*6);
			line.setEndY(600);
			anchorPane.getChildren().add(line);
		}
	}
	
	/**
	 * Redraws the grid so that the previous time step is erased
	 * @param anchorPane : the AnchorPane used in Main.java
	 */
	public static void drawCritters(AnchorPane anchorPane) {
		//Add the Critters
			List<Critter> pop = Critter.getPopulation();
    		System.out.println(pop.size());
    		
    		for(Critter c:pop) {
    			GridWorld.drawCritter(anchorPane, c);
    		}
	}
	
	/**
	 * Draws a Critter in one of five shapes
	 * @param anchorPane : the anchorPane used in our Main.java
	 * @param c : the current Critter that is being drawn
	 */
	private static void drawCritter(AnchorPane anchorPane, Critter c) {
		double xTBP=25;
		double yTBP=620;
		yTBP-=(c.getYCoord()*6);
		xTBP+=(c.getXCoord()*6);
		if(c.viewShape().toString().equalsIgnoreCase("circle")) {
			drawCircle(anchorPane, xTBP, yTBP, c);
		}
		else if(c.viewShape().toString().equalsIgnoreCase("square")) {
			drawSquare(anchorPane, xTBP, yTBP, c);
		}
		else if(c.viewShape().toString().equalsIgnoreCase("triangle")) {
			drawTriangle(anchorPane, xTBP, yTBP, c);
		}
		else if(c.viewShape().toString().equalsIgnoreCase("diamond")) {
			drawDiamond(anchorPane, xTBP, yTBP, c);
			
		}
		else if (c.viewShape().toString().equalsIgnoreCase("star")) {
			drawStar(anchorPane, xTBP, yTBP, c);
		}
		else
		{
		//Do nothing if shape is not correct, which shouldn't be possible
		}
	}
	
	/**
	 * Draws Critters that are represented by a circle
	 * @param anchorPane
	 * @param xTBP : x-coordinate (to be placed)
	 * @param yTBP : y-coordinate (to be placed)
	 * @param c : the Critter, allows access to fill and outline color
	 */
	@SuppressWarnings("static-access")
	private static void drawCircle(AnchorPane anchorPane, double xTBP, double yTBP, Critter c) {
		Circle circle = new Circle(2, c.viewColor());
		circle.setFill(c.viewFillColor());
		circle.setStroke(c.viewOutlineColor());
		anchorPane.getChildren().add(circle);
		anchorPane.setBottomAnchor(circle, yTBP + .5);
	 	anchorPane.setLeftAnchor(circle, xTBP + .5);
	}
	
	/**
	 * Draws Critters that are represented by a square
	 * @param anchorPane
	 * @param xTBP : x-coordinate (to be placed)
	 * @param yTBP : y-coordinate (to be placed)
	 * @param c : the Critter, allows access to fill and outline color
	 */
	@SuppressWarnings("static-access")
	private static void drawSquare(AnchorPane anchorPane, double xTBP, double yTBP, Critter c) {
		Rectangle square = new Rectangle(5.0,5.0,c.viewColor());
		square.setFill(c.viewFillColor());
		square.setStroke(c.viewOutlineColor());
		anchorPane.getChildren().add(square);
		anchorPane.setBottomAnchor(square, yTBP);
	 	anchorPane.setLeftAnchor(square, xTBP);
	}
	
	/**
	 * Draws Critters that are represented by a triangle
	 * @param anchorPane
	 * @param xTBP : x-coordinate (to be placed)
	 * @param yTBP : y-coordinate (to be placed)
	 * @param c : the Critter, allows access to fill and outline color
	 */
	@SuppressWarnings("static-access")
	private static void drawTriangle(AnchorPane anchorPane, double xTBP, double yTBP, Critter c) {
		Polygon triangle = new Polygon();
		triangle.setFill(c.viewFillColor());
		triangle.setStroke(c.viewOutlineColor());
		triangle.getPoints().addAll(new Double[]{
		    0.0, 0.0,
		    5.0, 0.0,
		    2.5, 5.0 });
		anchorPane.getChildren().add(triangle);
		anchorPane.setBottomAnchor(triangle, yTBP - 1.5);
	 	anchorPane.setLeftAnchor(triangle, xTBP - .75);
	}
	
	/**
	 * Draws Critters that are represented by a diamond
	 * @param anchorPane
	 * @param xTBP : x-coordinate (to be placed)
	 * @param yTBP : y-coordinate (to be placed)
	 * @param c : the Critter, allows access to fill and outline color
	 */
	@SuppressWarnings("static-access")
	private static void drawDiamond(AnchorPane anchorPane, double xTBP, double yTBP, Critter c) {
		Polygon diamond = new Polygon();
		diamond.setFill(c.viewFillColor());
		diamond.setStroke(c.viewOutlineColor());
		diamond.getPoints().addAll(new Double[]{
		    0.0, 2.5,
		    2.5, 5.0,
		    5.0, 2.5,
		    2.5, 0.0 });
		anchorPane.getChildren().add(diamond);
		anchorPane.setBottomAnchor(diamond, yTBP - 1);
	 	anchorPane.setLeftAnchor(diamond, xTBP - .5);
	}
	
	/**
	 * Draws Critters that are represented by a star
	 * @param anchorPane
	 * @param xTBP : x-coordinate (to be placed)
	 * @param yTBP : y-coordinate (to be placed)
	 * @param c : the Critter, allows access to fill and outline color
	 */
	@SuppressWarnings("static-access")
	private static void drawStar(AnchorPane anchorPane, double xTBP, double yTBP, Critter c) {
		Polygon star = new Polygon();
		star.setFill(c.viewFillColor());
		star.setStroke(c.viewOutlineColor());
		star.getPoints().addAll(new Double[] {
			2.5, 0.0,
			3.3, 2.0,
			5.0, 2.7,
			3.7, 3.2,
			4.0, 5.0,
			2.5, 3.8,
			1.0, 5.0,
			1.3, 3.2,
			0.0, 2.7,
			1.7, 2.0
		});
		anchorPane.getChildren().add(star);
		anchorPane.setBottomAnchor(star, yTBP - 1.5);
		anchorPane.setLeftAnchor(star, xTBP - 1.5);
	}
	
}
