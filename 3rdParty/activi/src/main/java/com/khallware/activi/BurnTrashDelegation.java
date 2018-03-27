package com.khallware.activi;

import com.khallware.activi.bag.SmokeyBear;
import com.khallware.activi.bag.HumphreyBear;
import com.khallware.activi.bag.BurnTrashEvent;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class BurnTrashDelegation implements JavaDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(
		BurnTrashDelegation.class);

	@Override
	public void execute(DelegateExecution execution)
	{
		SmokeyBear smokey = Main.park.getSmokeyBear();
		HumphreyBear humphrey = Main.park.getHumphreyBear();
		logger.info("{} tries to burn the trash.",
			humphrey.getName());
		smokey.handle(new BurnTrashEvent());
	}
}
