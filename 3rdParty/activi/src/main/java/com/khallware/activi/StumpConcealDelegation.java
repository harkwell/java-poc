package com.khallware.activi;

import com.khallware.activi.bag.HumphreyBear;
import com.khallware.activi.bag.BrownstonePark;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class StumpConcealDelegation implements JavaDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(
		StumpConcealDelegation.class);

	@Override
	public void execute(DelegateExecution execution)
	{
		HumphreyBear humphrey = Main.park.getHumphreyBear();
		logger.info("{} tries to conceal his trash in a hollow stump.",
			humphrey.getName());
		humphrey.pickupAllLitter(Main.park);
		humphrey.conceal(BrownstonePark.Item.STUMP);
	}
}
