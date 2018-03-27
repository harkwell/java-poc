package com.khallware.activi;

import com.khallware.activi.bag.BossRanger;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class RangerActionDelegation implements JavaDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(
		RangerActionDelegation.class);

	@Override
	public void execute(DelegateExecution execution)
	{
		logger.debug("{} is execute()ing...",
			this.getClass().getSimpleName());
		((BossRanger)Main.park.getRanger()).pickupLitter();
	}
}
