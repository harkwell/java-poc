package com.khallware.ejb;

import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Proof of Concept (PoC) to manipulate enterprise java beans (ejb).
 * See README.md for details.
 *
 * - EJB code lives on the server side and is written in java
 * - they contain only business logic (no boilerplate code required)
 * - platform is: distributed, scalable, secure, transactional and portable
 * - components may be run under any ejb server on any OS
 * - serializable on the wire (any transport can carry it)
 */
public class MyBeans
{
	@Remote
	public static interface MyBean
	{
		public String doStuff();
	}

	@Stateless
	public static class Bean1 implements MyBean
	{
		public String doStuff()
		{
			return(String.format("BEAN: %s",
				this.getClass().getSimpleName()));
		}
	}

	@Stateless
	public static class Bean2 implements MyBean
	{
		public String doStuff()
		{
			return(String.format("BEAN: %s",
				this.getClass().getSimpleName()));
		}
	}
}
