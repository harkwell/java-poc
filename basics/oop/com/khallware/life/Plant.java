package com.khallware.life;

import com.khallware.life.Main.Eukaryote;  // let me access Main's nested static
import com.khallware.life.Main.Kingdom;

// classes should be defined in a file of the same name
// they also live in a directory that reflects the package
// the "abstract" keyword notes that this class cannot be used with new()
public abstract class Plant extends Eukaryote implements Kingdom
{
	// this is an instance variable, this one is static (not unchangeable)
	// private scope says that only methods in this class can access them
	private static String KINGDOM_NAME = "MyPlant";

	public Plant() // default constructor: called by new Plant();
	{
		Plant.KINGDOM_NAME = "Plant";
	}

	// abstract method must be implemented by those extending this class
	public abstract void myAbstractMethod();

	public String getKingdom() // must be defined per "Kingdom" interface
	{
		return(KINGDOM_NAME);
	}
}
