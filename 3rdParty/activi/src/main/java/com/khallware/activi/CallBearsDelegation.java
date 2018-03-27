package com.khallware.activi;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class CallBearsDelegation implements JavaDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(
		CallBearsDelegation.class);

	@Override
	public void execute(DelegateExecution execution)
	{
		logger.info("Calling all bears...");
		logger.debug("Then, {} bears show up.",
			Main.park.getBears().size());
	}
}
