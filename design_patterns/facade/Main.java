package com.khallware.patterns.facade;

/**
 * The facade pattern constitutes generic interfaces.  It unifies a set of
 * api calls from a larger subsystem (eg java.util.List vs. ArrayList).
 *
 * Build:
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.patterns.facade.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/facade-pattern.jar -C /tmp com/
 * java -jar /tmp/facade-pattern.jar
 * rm -rf /tmp/com /tmp/manifest /tmp/facade-pattern.jar
 */
public class Main
{
	public static interface MyFacade
	{
		public String getString();
		public void performSomething();
		public int getInt();
	}

	public static class MyClass implements MyFacade
	{
		public String getString()
		{
			return("What is the answer to everything?");
		}

		public void performSomething()
		{
			// well, do something...
		}

		public int getInt()
		{
			return(42); // Hitchhiker's "The Answer"
		}
	}

	public static void main(String... args) throws Exception
	{
		MyFacade generic = new MyClass();
		System.out.printf("%s answer: %d\n", generic.getString(),
			generic.getInt());
	}
}
