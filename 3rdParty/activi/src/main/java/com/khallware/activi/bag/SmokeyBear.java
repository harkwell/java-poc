package com.khallware.activi.bag;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class SmokeyBear implements Bear, EventHandler<BurnTrashEvent>
{
	private static final Logger logger = LoggerFactory.getLogger(
		SmokeyBear.class);

	@Override
	public String getName()
	{
		return("Smokey the Bear");
	}

	@Override
	public void pickupLitter(Section section)
	{
		logger.info("{} picks up no litter", getName());
	}

	@Override
	public Bag getBag()
	{
		logger.warn("{} has no bag!", getName());
		return((Bag)null);
	}

	@Override
	public void handle(BurnTrashEvent event)
	{
		logger.info("Remember, only you can prevent forest fires.");
	}
}
