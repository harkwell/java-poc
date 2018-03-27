package com.khallware.activi;

import com.khallware.activi.bag.Util;
import com.khallware.activi.bag.BrownstonePark;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Main
{
	public static BrownstonePark park = null;
	public static String URL = "jdbc:h2:mem:activiti;DB_CLOSE_DELAY=1000";
	public static String USER = "app";
	public static String PASSWD = "activi";
	public static String DRIVER = "org.h2.Driver";
	public static String XML_FILE = "put-it-in-the-bag.bpmn20.xml";
	public static String PROCESS_NAME = "touristsLeave";
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

	public static ProcessDefinition makePsDefinition(
			ProcessEngine psengine, String xmlFile)
	{
		RepositoryService svc = psengine.getRepositoryService();
		Deployment deployment = svc.createDeployment()
			.addClasspathResource(xmlFile).deploy();
		return(svc.createProcessDefinitionQuery()
		        .deploymentId(deployment.getId()).singleResult());
	}

	public static ProcessInstance makePsInstance(ProcessEngine psengine)
	{
		return(psengine.getRuntimeService().startProcessInstanceByKey(
			PROCESS_NAME));
	}

	public static void main(String... args) throws Exception
	{
		Main.park = new BrownstonePark(Util.generateBear());
		ProcessEngineConfiguration cfg = makePsEngineConfig();
		ProcessEngine psengine = cfg.buildProcessEngine();
		logger.info("ProcessEngine [{}] Version: [{}]",
			psengine.getName(), ProcessEngine.VERSION);
		ProcessDefinition psdef = makePsDefinition(psengine, XML_FILE);
		logger.info("ProcessDefinition [{}] [{}]", psdef.getName(),
			psdef.getId());

		ProcessInstance psinst = makePsInstance(psengine);

		for (Task task : psengine.getTaskService()
				.createTaskQuery().list()) {
			logger.info("Processing task: {}", task.getName());
			psengine.getTaskService().complete(task.getId(), null);
		}
	}
}
