package com.khallware.activi;

import com.khallware.activi.bag.HumphreyBear;
import com.khallware.activi.bag.BrownstonePark;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class BushConcealDelegation implements JavaDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(
		BushConcealDelegation.class);

	@Override
	public void execute(DelegateExecution execution)
	{
		HumphreyBear humphrey = Main.park.getHumphreyBear();
		logger.info("{} tries to conceal his trash under a bush.",
			humphrey.getName());
		humphrey.pickupAllLitter(Main.park);
		humphrey.conceal(BrownstonePark.Item.BUSH);
	}
}
