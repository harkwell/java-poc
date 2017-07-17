package com.khallware.life;

public class Util
{
	public static String lifeToString(Main.Life item)
	{
		Main.Species species = (Main.Species)item;  // this is a cast
		return(String.format("Name: \"%s\"\n"
			+"Domain: \"%s\"\n"
			+"Kingdom: \"%s\"\n"
			+"Phylum: \"%s\"\n"
			+"Class: \"%s\"\n"
			+"Order: \"%s\"\n"
			+"Family: \"%s\"\n"
			+"Genus: \"%s\"\n"
			+"Species: \"%s\"\n",

			// call methods via an object reference (ie species)
			species.getCommonName(),
			species.getDomain(),
			species.getKingdom(),
			species.getPhylum(),
			species.getClazz(),
			species.getOrder(),
			species.getFamily(),
			species.getGenus(),
			species.getScientificName()
		));
	}
}
