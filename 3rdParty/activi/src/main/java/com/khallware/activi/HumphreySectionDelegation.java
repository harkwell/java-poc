package com.khallware.activi;

import com.khallware.activi.bag.HumphreyBear;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class HumphreySectionDelegation implements JavaDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(
		HumphreySectionDelegation.class);

	@Override
	public void execute(DelegateExecution execution)
	{
		HumphreyBear humphrey = Main.park.getHumphreyBear();
		logger.info("{} stuffs all of the trash into his bag.",
			humphrey.getName());
		humphrey.pickupAllLitter(Main.park);
	}
}
