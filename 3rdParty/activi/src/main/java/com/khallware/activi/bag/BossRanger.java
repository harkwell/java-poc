package com.khallware.activi.bag;

import java.util.List;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class BossRanger implements Ranger
{
	private Park park = null;
	private static final Logger logger = LoggerFactory.getLogger(
		BossRanger.class);

	public BossRanger(Park park)
	{
		this.park = park;
	}

	@Override
	public String getName()
	{
		return(park.getName()+" Ranger");
	}

	@Override
	public String sing()
	{
		String retval = "";

		switch (park.getName()) {
		case "Brownstone National Park":
			retval = HumphreyHop.getLyrics(
				locateValidSection(park.getSections()));
			break;
		}
		return(retval);
	}

	@Override
	public String getMotivation()
	{
		return("Chicken Cacciatore");
	}

	public void pickupLitter()
	{
		String msg = "Wait just a minute, I'm the boss";
		int count = 0;

		LOOP: for (Section section : park.getSections()) {
			logger.info("{} picks up:", this.getName());

			for (String item : section.getLitter()) {
				logger.info(item);

				if (count++ > 3) {
					logger.info(msg);
					break LOOP;
				}
			}
		}
	}

	public void motivate()
	{
		logger.info("You {} bears can have some {} if you clean up "
			+"your section of {}!", park.getBears().size(),
			getMotivation(), park.getName());
	}

	private static Section locateValidSection(List<Section> sections)
	{
		Section retval = null;

		for (Section section : sections) {
			if (section.getLitter().size() > 2) {
				retval = section;
				break;
			}
		}
		String msg = "no section has at least two items of litter";

		if (retval == null) {
			logger.error(msg);
			throw new RuntimeException(msg);
		}
		return(retval);
	}
}
