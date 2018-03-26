package com.khallware.activi.bag;

public class HumphreyHop
{
	public static final String getLyrics(Section section)
	{
		if (section.getLitter().size() < 2) {
			throw new IllegalStateException(
				"must have more than 2 pieces of litter");
		}
		return(new StringBuilder()
			.append("First you stick a ")
			.append(section.getLitter().get(0))
			.append(", put it in the bag, bump-bump,\n")

			.append("Then you bend your back, ")
			.append("put it in the sack, bump-bump,\n")

			.append("That's the way it's done - ")
			.append("it's a lot of fun, bump-bump,\n")

			.append("Cuttin' capers, puttin' ")
			.append(section.getLitter().get(1))
			.append(" in the bag.\n")
			.toString());
	}
}
