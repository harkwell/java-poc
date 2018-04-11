package com.khallware.agent;

import java.lang.management.ManagementFactory;

/**
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.agent.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/mypoc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -jar /tmp/mypoc.jar
 * java -javaagent:/tmp/myagent.jar -jar /tmp/mypoc.jar
 * rm /tmp/mypoc.jar /tmp/myagent.jar
 *
 */
public class Main
{
	public static void main(String... args) throws Exception
	{
		for (String jvmarg : ManagementFactory.getRuntimeMXBean()
				.getInputArguments()) {
			System.out.printf("jvm arg: \"%s\"\n", jvmarg);
		}
		System.out.println("main() completes...");
	}
}
