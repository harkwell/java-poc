package com.khallware.activi;

import com.khallware.activi.bag.EventHandler;
import com.khallware.activi.bag.SeasonEndEvent;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class TouristsLeaveDelegation implements JavaDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(
		TouristsLeaveDelegation.class);

	@Override
	public void execute(DelegateExecution execution)
	{
		logger.debug("DelegateExecution for {} is: {}",
			this.getClass().getSimpleName(), ""+execution);

		if (Main.park != null) {
			((EventHandler<SeasonEndEvent>)Main.park).handle(
				new SeasonEndEvent());
		}
		else {
			logger.error("Main.park is not set!");
		}
	}
}
