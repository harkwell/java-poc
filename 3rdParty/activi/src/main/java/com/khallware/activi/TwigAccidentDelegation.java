package com.khallware.activi;

import com.khallware.activi.bag.Util;
import com.khallware.activi.bag.HumphreyBear;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class TwigAccidentDelegation implements JavaDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(
		TwigAccidentDelegation.class);

	@Override
	public void execute(DelegateExecution execution)
	{
		HumphreyBear humphrey = Main.park.getHumphreyBear();
		logger.info("{} catches his bag on a twig and it explodes.",
			humphrey.getName());
		Util.scatterLitter(humphrey.getBag().getLitter(), Main.park);
	}
}
