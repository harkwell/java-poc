package com.khallware.poc.spring;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class MyBean
{
	private static final Logger logger = LoggerFactory.getLogger(
		MyBean.class);

	private String content;

	public void setContent(String content)
	{
		this.content = content;
	}

	public void logContent()
	{
		logger.info(content);
	}
}
