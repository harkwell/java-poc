package com.khallware.patterns.singleton;

/**
 * The singleton pattern enforces that only one instance of a class per
 * JVM is allowed.  Its over-use is considered an anti-pattern, so use
 * sparingly.  It's akin to global variables.
 *
 * Build:
 * javac -d /tmp Main.java MySingleton.java
 * echo 'Main-Class: com.khallware.patterns.singleton.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/singleton-pattern.jar -C /tmp com/
 * java -jar /tmp/singleton-pattern.jar
 * rm -rf /tmp/com /tmp/manifest /tmp/singleton-pattern.jar
 */
public class Main
{
	public static void main(String... args) throws Exception
	{
		MySingleton singleton = MySingleton.getMySingleton();
		System.out.println(singleton.doStuff());
		singleton = MySingleton.getMySingleton();
		// singleton = new MySingleton();  // compile time failure
	}
}
