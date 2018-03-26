package com.khallware.activi.bag;

import java.util.ArrayList;
import java.util.List;

public class Util
{
	public static void moveAllLitterToRandomSection(Park park)
	{
		int numSections = park.getSections().size();
		int choice = (int)(Math.random() * numSections);

		for (int idx = 0; idx < numSections; idx++) {
			if (idx == choice) {
				continue;
			}
			Section section = park.getSections().get(idx-1);
			park.getSections().get(choice).getLitter().addAll(
				section.getLitter());
			section.getLitter().clear();
		}
	}

	public static void scatterLitter(List<String> litter, Park park)
	{
		int numSections = park.getSections().size();

		for (String item : litter) {
			int choice = (int)(Math.random() * numSections);
			Section section = park.getSections().get(choice);
			section.getLitter().add(item);
		}
	}

	public static List<Bear> generateBear()
	{
		List<Bear> retval = new ArrayList<>();
		retval.add(new HumphreyBear());
		retval.add(new SmokeyBear());
		retval.add(new BrownstoneBear("George"));
		retval.add(new BrownstoneBear("Suzie"));
		retval.add(new BrownstoneBear("Dan"));
		retval.add(new BrownstoneBear("Leohold"));
		retval.add(new BrownstoneBear("Frank"));
		return(retval);
	}
}
