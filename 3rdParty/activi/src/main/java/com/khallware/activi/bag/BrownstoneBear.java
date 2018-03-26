package com.khallware.activi.bag;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class BrownstoneBear implements Bear
{
	private String name = "";
	private Bag bag = new Bag();
	private static final Logger logger = LoggerFactory.getLogger(
		BrownstoneBear.class);

	public BrownstoneBear(String name)
	{
		this.name = name;
	}

	@Override
	public Bag getBag()
	{
		return(bag);
	}

	@Override
	public String getName()
	{
		return(name);
	}

	@Override
	public void pickupLitter(Section section)
	{
		logger.info("{} picks up litter while jouncing: {}",
			getName(), section.getLitter());

		for (String litter : section.getLitter()) {
			bag.putIn(litter);
		}
		section.getLitter().clear();
	}
}
