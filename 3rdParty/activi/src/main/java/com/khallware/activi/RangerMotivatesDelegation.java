package com.khallware.activi;

import com.khallware.activi.bag.BossRanger;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class RangerMotivatesDelegation implements JavaDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(
		RangerMotivatesDelegation.class);

	@Override
	public void execute(DelegateExecution execution)
	{
		((BossRanger)Main.park.getRanger()).motivate();
	}
}
