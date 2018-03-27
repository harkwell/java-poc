package com.khallware.activi;

import com.khallware.activi.bag.Section;
import com.khallware.activi.bag.Bear;
import java.util.List;
import java.util.ArrayList;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class CollectTrashDelegation implements JavaDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(
		CollectTrashDelegation.class);

	@Override
	public void execute(DelegateExecution execution)
	{
		logger.info("The bears happily collect the trash...");
		int numBears = Main.park.getBears().size();
		int count = 0;
		List<Thread> threads = new ArrayList<>();
		logger.info(Main.park.getRanger().sing());

		for (Section section : Main.park.getSections()) {
			final Bear bear = Main.park.getBears().get(
				(count % numBears));
			final Section s = section;
			Thread thread = new Thread(() -> {
				bear.pickupLitter(s);
			});
			thread.start();
			threads.add(thread);
			count++;
		}

		for (Thread thread : threads) {
			try {
				thread.join(1000);

				if (thread.isAlive()) {
					thread.interrupt();
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
