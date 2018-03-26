package com.khallware.activi.bag;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.khallware.activi.bag.BrownstonePark.Item.*;

public class HumphreyBear implements Bear
{
	private Bag bag = new Bag();
	private static final Logger logger = LoggerFactory.getLogger(
		HumphreyBear.class);

	@Override
	public String getName()
	{
		return("Humphrey");
	}

	@Override
	public void pickupLitter(Section section)
	{
		logger.info("{} picks up litter while bouncing: {}",
			getName(), section.getLitter());

		for (String litter : section.getLitter()) {
			bag.putIn(litter);
		}
		section.getLitter().clear();
	}

	@Override
	public Bag getBag()
	{
		return(bag);
	}

	public void pickupAllLitter(Park park)
	{
		for (Section section : park.getSections()) {
			pickupLitter(section);
		}
	}

	public void fallsOffCliff(Park park)
	{
		dumpLitter(park);
	}

	public void conceal(BrownstonePark.Item location)
	{
		switch (location) {
		case BUSH:
			logger.info("Rabbit says this is my home!");
			break;
		case STUMP:
			logger.info("This is Ol' Fateful!");
			break;
		}
	}
}
