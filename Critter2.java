/* CRITTERS <Critter2.java>
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

import assignment5.Critter;
import assignment5.Critter.CritterShape;

/**
 * Critter2 (Eddy) is known as the lover.
 * He is always looking to reproduce
 * And never wants to fight
 * He even refuses to move because it might lead to more encounters
 * He can't look for food for fear of other Critters,
 *     and thus can only gain energy if he wins an (unwanted) fight
 *     or food grows near him (same space)
 * The baby is also always placed below him, so that he can keep an eye on him
*/
public class Critter2 extends Critter
{
	
	
	public String toString(){return "2";}
	
	public boolean fight(String not_used){
		int dir = Critter.getRandomInt(8);
		if (this.look(dir, true) == null) {		//checks to make sure no other Critters are in the direction he is heading
			run(dir);
		}
		return false;
	}
	
	public void doTimeStep()
	{
		Critter2 baby = new Critter2();
		reproduce(baby, 6);
	}
	
	@Override
	public CritterShape viewShape() { return CritterShape.DIAMOND; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BLACK; }
	
	@Override
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.SKYBLUE; }
}
