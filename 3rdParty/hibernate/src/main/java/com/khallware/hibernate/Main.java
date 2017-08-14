package com.khallware.hibernate;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Main
{
	private static final Logger logger = LoggerFactory.getLogger(
		Main.class);

	public static void main(String[] args) throws Exception
	{
		logger.info("creating new item...");
		Item item = new Item(args[0], args[1]);
		logger.debug("created ({})", item);
		new Repo().persist(item);
	}
}
