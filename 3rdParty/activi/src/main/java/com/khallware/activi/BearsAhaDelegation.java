package com.khallware.activi;

import com.khallware.activi.bag.Bear;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class BearsAhaDelegation implements JavaDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(
		BearsAhaDelegation.class);

	@Override
	public void execute(DelegateExecution execution)
	{
		logger.info("The bears realize they've been duped!");

		for (Bear bear : Main.park.getBears()) {
			logger.info("{} scatters his litter", bear.getName());
			bear.dumpLitter(Main.park);
		}
	}
}
