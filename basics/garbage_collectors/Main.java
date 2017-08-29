package com.khallware.poc.gc;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;

/**
 * This PoC shows how to show information for the garbage collectors.
 *
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.poc.gc.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/gc-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -jar /tmp/gc-poc.jar
 * java -jar /tmp/gc-poc.jar -XX:+UseConcMarkSweepGC
 * java -jar /tmp/gc-poc.jar -XX:+UseG1GC
 * rm /tmp/gc-poc.jar
 */
public class Main
{
	public static void main(String... args) throws Exception
	{
		for (GarbageCollectorMXBean bean : ManagementFactory
				.getGarbageCollectorMXBeans()) {
			System.out.println("Name: "+bean.getName());
			System.out.println("Num Collections: "
				+bean.getCollectionCount());
			System.out.println("Collection time: "
				+bean.getCollectionTime()+"ms");
			System.out.println("Pool names: ");

			for (String name : bean.getMemoryPoolNames()) {
				System.out.println("\t"+name);
			}
			System.out.println();
		}
	}
}
