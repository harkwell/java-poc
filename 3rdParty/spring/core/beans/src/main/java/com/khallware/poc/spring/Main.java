package com.khallware.poc.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO && mkdir -p $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO package assembly:single
 * ls -ld target/spring-poc-0.0.1-jar-with-dependencies.jar
 * 
 * java -jar target/spring-poc-0.0.1-jar-with-dependencies.jar spring.xml mybean
 * rm -rf $POC_MAVEN_REPO target/
 *
 */
public class Main
{
	public static void main(String... args) throws Exception
	{
		ApplicationContext ctxt = new ClassPathXmlApplicationContext(
			args[0]);
		MyBean bean = (MyBean)ctxt.getBean(args[1]);
		bean.logContent();
	}
}
