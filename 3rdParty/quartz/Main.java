package com.khallware.poc.quartz;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.JobBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * 
 * for dep in org.quartz-scheduler:quartz:2.3.0 org.slf4j:slf4j-api:1.7.25; do
 *    mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *        org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *        -DrepoUrl=https://mvnrepository.com/ \
 *        -Dartifact=$dep
 * done
 * QUARTZ_JAR=$(find $POC_MAVEN_REPO -name \*quartz\*jar)
 * JAR1=$(find $POC_MAVEN_REPO -name \*slf4j-api\*jar)
 * javac -d /tmp -cp $QUARTZ_JAR 3rdParty/quartz/Main.java
 * echo 'Main-Class: com.khallware.poc.quartz.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/quartz-poc.jar -C /tmp com
 * export CLASSPATH=/tmp/quartz-poc.jar:$RUNNER_JAR
 * java -cp $QUARTZ_JAR:/tmp/quartz-poc.jar:$JAR1 com.khallware.poc.quartz.Main
 * rm -rf /tmp/com /tmp/manifest /tmp/quartz-poc.jar
 *
 */
public class Main
{
	public static class MyJob implements Job
	{
		public void execute(JobExecutionContext ctxt)
				throws JobExecutionException
		{
			System.out.println("executing MyJob...");
		}
	}

	public static void main(String... args) throws Exception
	{
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(
			JobBuilder.newJob(MyJob.class)
				.withIdentity("myjob1", "mygroup1")
				.build(),
			TriggerBuilder.newTrigger()
				.withIdentity("mytrigger1", "mygroup1")
				.withSchedule(
					CronScheduleBuilder.cronSchedule(
						"0/5 * * * *  ?"))
				.build()
		);
	}
}
