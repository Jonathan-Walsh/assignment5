package assignment5;

import java.util.Iterator;
import java.util.List;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	protected String look(int direction, boolean steps) {
		energy-=Params.look_energy_cost;
		int xToCheck=x_coord;
		int yToCheck=y_coord;
		int xMult=1;
		int yMult=1;
		if(steps)
		{
			xMult++;
			yMult++;
		}
		
		xToCheck += (x_directions[direction]*xMult);
		xToCheck += Params.world_width;
		xToCheck %= Params.world_width;
		yToCheck += (y_directions[direction]*yMult);
		yToCheck += Params.world_height;
		yToCheck %= Params.world_height;
		
		for (Critter c: population)
		{
			if ((c.energy > 0) && (c.x_coord == xToCheck) && (c.y_coord == yToCheck))
				//TODO: Note in README that we don't remove Critters during fight, so we 
				//      instead just check if they are dead
					return c.toString();
		}
		return null;
		}
	
	/* rest is unchanged from Project 4 */
	
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	protected int getYCoord()
	{
		return y_coord;
	}
	protected int getXCoord()
	{
		return x_coord;
	}
	
	private int[] x_directions = {1,1,0,-1,-1,-1,0,1};
	private int[] y_directions = {0,-1,-1,-1,0,1,1,1};
	
	//Used to determine if a critter has already moved during a time step
	private boolean hasMoved = false; 
	//Used for specific walking/running during fight()
	private static boolean fightMode = false;	
	
	protected final void walk(int direction) {
		int temp_x = x_coord;
		int temp_y = y_coord;
	//Update location	
		if (!hasMoved) {
			temp_x += x_directions[direction];
			temp_x += Params.world_width;
			temp_x %= Params.world_width;
			temp_y += y_directions[direction];
			temp_y += Params.world_height;
			temp_y %= Params.world_height;
		}	
	//Specific to walking during fight
		if (fightMode) {
			boolean critterInLocation = false;
			for (Critter c: population) {
				if ((c.x_coord == temp_x) && (c.y_coord == temp_y)) {
					critterInLocation = true;
				}
			}
			if (!critterInLocation) {
				x_coord = temp_x;
				y_coord = temp_y;
			}
		}
		else {					//Specific to walking in time step
			x_coord = temp_x;
			y_coord = temp_y;
		}
	//Update energy
		energy -= Params.walk_energy_cost;
	//Update hasMoved
		hasMoved = true;
	}
	
	/**
	 * Moves a critter two steps in a specified direction
	 * @params direction. An integer representing the direction to move
	 */
	protected final void run(int direction) {
		int temp_x = x_coord;
		int temp_y = y_coord;
	//Update location (*2 because we move twice in same direction)	
		if (!hasMoved) {
			temp_x += (x_directions[direction] * 2);
			temp_x += Params.world_width;
			temp_x %= Params.world_width;
			temp_y += (y_directions[direction] * 2);
			temp_y += Params.world_height;
			temp_y %= Params.world_height;
		}
	//Specific to running during fight
		if (fightMode) {
			boolean critterInLocation = false;
			for (Critter c: population) {
				if ((c.x_coord == temp_x) && (c.y_coord == temp_y)) {
					critterInLocation = true;
				}
			}
			if (!critterInLocation) {
				x_coord = temp_x;
				y_coord = temp_y;
			}
		}
		else {					//Specific to walking in time step
			x_coord = temp_x;
			y_coord = temp_y;
		}
	//Update energy
		energy -= Params.run_energy_cost;
	//Update hasMoved
		hasMoved = true;
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		//Return immediately if parent does not have enough energy to reproduce
			if (energy < Params.min_reproduce_energy) {
				return;
			}
		//If parent does have enough energy
			//Divide energy between offspring and parent
			offspring.energy = this.energy / 2;
			if (this.energy % 2 == 0) {
				this.energy = this.energy / 2;
			}
			else {								//Make sure to round up (only causes problem when energy is odd)
				this.energy = (this.energy / 2) + 1;
			}
			//Set location of offspring
			offspring.x_coord = this.x_coord + x_directions[direction];
			offspring.x_coord += Params.world_width;
			offspring.x_coord %= Params.world_width;
			offspring.y_coord = this.y_coord + y_directions[direction];
			offspring.y_coord += Params.world_height;
			offspring.y_coord %= Params.world_height;
		//Add to babies
			babies.add(offspring);
		}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	
	
	public static void displayWorld() {}
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		Critter critter;
	//Create the critter	
		try {
			Class<?> critter_class = Class.forName(myPackage + "." + critter_class_name);
			critter = (Critter) critter_class.newInstance();
		}
		catch (ClassNotFoundException e1) {		//Class.forName() exception
			throw new InvalidCritterException(critter_class_name);
		}
		catch (IllegalAccessException e2) {		//Class.newInstance() exception
			throw new InvalidCritterException(critter_class_name);
		}
		catch (InstantiationException e3) { 	//Class.newInstance() exception
			throw new InvalidCritterException(critter_class_name);
		}
	//Initialize the location/energy of the critter
		critter.energy = Params.start_energy;
		critter.x_coord = getRandomInt(Params.world_width);
		critter.y_coord = getRandomInt(Params.world_height);
	//Add the critter to the world
		population.add(critter);
	}
	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		Critter critter;
	//Get the correct critter class and throw exception if the class doesn't exist
		try {
			Class<?> critter_class = Class.forName(myPackage + "." + critter_class_name);
			critter = (Critter) critter_class.newInstance();
		}
		catch (ClassNotFoundException e1){
			throw new InvalidCritterException(critter_class_name);
		}
		catch (IllegalAccessException e2) {		//Class.newInstance() exception
			throw new InvalidCritterException(critter_class_name);
		}
		catch (InstantiationException e3) { 	//Class.newInstance() exception
			throw new InvalidCritterException(critter_class_name);
		}
	//Make the list of critters of type critter_class_name
		for (Critter c: population) {
			if (c.getClass() == critter.getClass()) {
				result.add(c);
			}
		}
		return result;
	}
	
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
	}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	
	
	public static void worldTimeStep() {
		
		//Do time step for all critters
			List<Critter> tempPop = new java.util.ArrayList<Critter>();
			tempPop.addAll(population);
			//TODO: I don't think that this actually makes copies of the Critters, need to do that
			for (Critter c: tempPop) {
				c.doTimeStep();			
			}
			population = tempPop;
			
		//Remove dead critters
			Iterator<Critter> it0 = population.iterator();
			while(it0.hasNext())
			{
				if(it0.next().energy<=0)
					it0.remove();
			}
			
		//Resolve encounters
			Iterator<Critter> it1 = population.iterator();
			fightMode = true;
			while(it1.hasNext())
			{
				Critter c = it1.next();
				Iterator<Critter> it2 = population.iterator();
				while(it2.hasNext()&&(c.energy > 0)) 
				{	
					Critter a =it2.next();
					if((c != a)&&(a.x_coord==c.x_coord)&&(a.y_coord==c.y_coord))
					{
						boolean cFighting = c.fight(a.toString());
						boolean aFighting = a.fight(c.toString());
					//If they are both still in the same location and alive, then fight
						if ((a.x_coord == c.x_coord) && (a.y_coord == c.y_coord) && (a.energy > 0) && (c.energy > 0)) {		
							int cFight=0;
							int aFight=0;
							if(cFighting)
							{
								cFight = getRandomInt(100);
								cFight++;
							}
							if(aFighting)
							{
								aFight =getRandomInt(100);
								aFight++;
							}
							if(cFight>aFight)
							{
								c.energy+=(a.energy/2);
								//it2.remove();
								a.energy=0;
							}
							else if(aFight>cFight)
							{
								a.energy+=(c.energy/2);
								//it1.remove()
								c.energy=0;						
							}
							else
							{
								if(aFight>50)
								{
									a.energy+=(c.energy/2);
									//it1.remove();
									c.energy=0;
								}
								else
								{
									c.energy+=(a.energy);
									//it2.remove();
									a.energy=0;
								}
							}
						}
					}
				}
			}
			fightMode = false;
			
		//Update rest energy and reset hasMoved
			for (Critter c2: population) {
				c2.hasMoved = false;
				c2.energy -= Params.rest_energy_cost;
			}
			
		//Spawn offspring and add to population
			population.addAll(babies);
			babies.clear();
		
		//Remove dead critters
			Iterator<Critter> it3 = population.iterator();
			while(it3.hasNext())
			{
				if(it3.next().energy<=0)
					it3.remove();
			}
			
		//Create new algae
			for (int i = 0; i < Params.refresh_algae_count; i++) {
				try {
					makeCritter("Algae");
				} catch (InvalidCritterException e) {
					e.printStackTrace();
				}
			}
		}
	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
	}
	
	
}
