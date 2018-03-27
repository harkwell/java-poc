package com.khallware.activi;

import com.khallware.activi.bag.Util;
import com.khallware.activi.bag.HumphreyBear;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class CliffAccidentDelegation implements JavaDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(
		CliffAccidentDelegation.class);

	@Override
	public void execute(DelegateExecution execution)
	{
		HumphreyBear humphrey = Main.park.getHumphreyBear();
		logger.info("{} receives a new bag and follows garbage to a "
			+"cliff, but falls off just as he picks up the last "
			+"piece.", humphrey.getName());
		humphrey.pickupAllLitter(Main.park);
		humphrey.fallsOffCliff(Main.park);
	}
}
