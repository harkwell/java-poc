package com.khallware.activi;

import com.khallware.activi.bag.Util;
import com.khallware.activi.bag.BrownstonePark;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Main
{
	public static String URL = "jdbc:h2:mem:activiti;DB_CLOSE_DELAY=1000";
	public static String USER = "app";
	public static String PASSWD = "activi";
	public static String DRIVER = "org.h2.Driver";
	// public static String DRIVER = "org.hsqldb.jdbcDriver";
	private static final Logger logger = LoggerFactory.getLogger(
		Main.class);

	public static ProcessEngineConfiguration makePsEngineConfig()
	{
		return(new StandaloneProcessEngineConfiguration()
			.setJdbcUrl(URL)
			.setJdbcUsername(USER)
			.setJdbcPassword(PASSWD)
			.setJdbcDriver(DRIVER)
			.setDatabaseSchemaUpdate(
				ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE
			)
		);
	}

	public static void main(String... args) throws Exception
	{
		BrownstonePark park = new BrownstonePark(Util.generateBear());
		ProcessEngineConfiguration cfg = makePsEngineConfig();
		ProcessEngine psengine = cfg.buildProcessEngine();
		logger.info("ProcessEngine [{}] Version: [{}]",
			psengine.getName(), ProcessEngine.VERSION);
	}
}
