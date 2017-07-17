package com.khallware.life;

import com.khallware.life.Main.Genus;

public class Angiosperm extends Plant implements Main.Phylum
{
	public static abstract class Eudicot
			extends Angiosperm implements Main.Clazz
	{
		@Override
		public String getClazz()
		{
			return("Eudicot");
		}

		// getPhylum() is required, but adding "abstract" passes it down
	}

	public static abstract class Asterid
			extends Eudicot implements Main.Order
	{
		public String getOrder()
		{
			return("Asterid");
		}
	}

	public static abstract class Lamiid
			extends Asterid implements Main.Family
	{
		public String getFamily()
		{
			return("Lamiid");
		}
	}

	// abstract classes can also be inner nested classes
	public static abstract class Solanale extends Lamiid implements Genus
	{
		public String getGenus()
		{
			return("Solanale");
		}

		protected abstract void anotherMethod();
	}

	public static class Solanaceae extends Solanale
	{
		@Override
		protected void anotherMethod()
		{
			// scoping on method must match the abstract class
		}
	}

	public Angiosperm()
	{
		System.out.println("Angiosperm constructor being called...");
	}

	@Override
	public void myAbstractMethod()
	{
		// this is required to be overriden from Plant.java
	}

	@Override
	public String getPhylum()
	{
		// this is required to be overriden from Phylum
		return("Angiosperm");
	}
}
