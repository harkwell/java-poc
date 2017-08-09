package com.khallware.patterns.adapter;

/**
 * The adapter pattern solves the problem of one or more disparate
 * interfaces being used interchangably through a unifying component.
 * This pattern is also known as: wrapper and translator.
 *
 * Build:
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.patterns.adapter.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/adapter-pattern.jar -C /tmp com/
 * java -jar /tmp/adapter-pattern.jar
 * rm -rf /tmp/com /tmp/manifest /tmp/adapter-pattern.jar
 */
public class Main
{
	public static interface Adapter
	{
		public void action1(String item);
		public String action2();
		public String action3(int value);
	}

	public static class Incompatible1
	{
		public String myAction1(Object foo)
		{
			return(null);
		}

		public void myAction2(Object foo)
		{
		}

		public int myAction3()
		{
			return(42);
		}
	}

	public static class Incompatible1Adapter implements Adapter
	{
		private Incompatible1 instance = null;

		public Incompatible1Adapter(Incompatible1 instance)
		{
			this.instance = instance;
		}

		public void action1(String item)
		{
			instance.myAction1(item);
		}

		public String action2()
		{
			instance.myAction2(null);
			return("foo");
		}

		public String action3(int value)
		{
			return((instance.myAction3() == value) ? "yes" : "no");
		}
	}

	public static class Incompatible2
	{
		public String myAction1()
		{
			return("foo");
		}
	}

	public static class Incompatible2Adapter implements Adapter
	{
		private Incompatible2 instance = null;

		public Incompatible2Adapter(Incompatible2 instance)
		{
			this.instance = instance;
		}

		public void action1(String item)
		{
			instance.myAction1();
		}

		public String action2()
		{
			throw new IllegalStateException("not yet implemented");
		}

		public String action3(int value)
		{
			throw new IllegalStateException("not yet implemented");
		}
	}

	public static void main(String... args) throws Exception
	{
		Incompatible1 engine1 = new Incompatible1();
		Incompatible2 engine2 = new Incompatible2();
		Adapter adapter1 = new Incompatible1Adapter(engine1);
		Adapter adapter2 = new Incompatible2Adapter(engine2);
		adapter1.action1("foo");
		adapter2.action1("foo");
		adapter1.action2();
		// adapter2.action2();
		adapter1.action3(42);
		adapter2.action3(42);
	}
}
