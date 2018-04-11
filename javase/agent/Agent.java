package com.khallware.agent;

import java.lang.instrument.Instrumentation;

/**
 * javac -d /tmp Agent.java
 * jar cmvf manifest /tmp/myagent.jar -C /tmp com
 * rm -rf /tmp/com
 *
 */
public class Agent
{
	public static void premain(String args, Instrumentation instrumentation)
	{
		instrumentation.addTransformer(
			(loader, name, clazz, domain, buffer) -> {
				System.out.printf("class=\"%s\", "
					+"size=\"%d bytes\"\n",
					name, buffer.length);
				return(buffer);
			}
		);
	}
}
