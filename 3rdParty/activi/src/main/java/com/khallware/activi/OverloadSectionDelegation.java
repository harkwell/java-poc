package com.khallware.activi;

import com.khallware.activi.bag.Util;
import com.khallware.activi.bag.HumphreyBear;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class OverloadSectionDelegation implements JavaDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(
		OverloadSectionDelegation.class);

	@Override
	public void execute(DelegateExecution execution)
	{
		logger.info("The bears move all litter to a single section of "
			+"the park, Humphrey's and collect their reward.");
		Util.moveAllLitterToRandomSection(Main.park);
		// Main.park.getBears().clear();
	}
}
