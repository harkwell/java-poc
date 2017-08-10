package com.khallware.patterns.marker;

/**
 * The marker pattern is simply marking a class with an empty interface.
 *
 * Build:
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.patterns.marker.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/marker-pattern.jar -C /tmp com/
 * java -jar /tmp/marker-pattern.jar
 * rm -rf /tmp/com /tmp/manifest /tmp/marker-pattern.jar
 */
public class Main
{
	public static interface MyMarker {}

	public static class MyClass1
	{
		public MyClass1() {}
	}

	public static class MyClass2 implements MyMarker
	{
		public MyClass2() {}
	}

	public static class MyClass3 implements MyMarker
	{
		public MyClass3() {}
	}

	public static void main(String... args) throws Exception
	{
		Class[] classes = new Class[] {
			MyClass1.class, MyClass2.class, MyClass3.class
		};
		for (Class clazz : classes) {
			if (clazz.newInstance() instanceof MyMarker) {
				System.out.printf("%s is marked...\n",
					clazz.getSimpleName());
			}
		}
	}
}
