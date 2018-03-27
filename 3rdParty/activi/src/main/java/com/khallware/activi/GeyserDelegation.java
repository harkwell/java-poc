package com.khallware.activi;

import com.khallware.activi.bag.Util;
import com.khallware.activi.bag.HumphreyBear;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class GeyserDelegation implements JavaDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(
		GeyserDelegation.class);

	@Override
	public void execute(DelegateExecution execution)
	{
		HumphreyBear humphrey = Main.park.getHumphreyBear();
		logger.info("The geyser erupts and {} has to start all over.",
			humphrey.getName());
		humphrey.pickupAllLitter(Main.park);
		Util.scatterLitter(humphrey.getBag().getLitter(), Main.park);
		humphrey.pickupAllLitter(Main.park);
	}
}
