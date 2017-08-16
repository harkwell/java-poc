package com.khallware.jsf;

import javax.faces.bean.ManagedBean;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@ManagedBean
public class PingPong
{
	private static final Logger logger = LoggerFactory.getLogger(
		PingPong.class);

	public String ping()
	{
		logger.info("ping...");
		return("pong");
	}
}
