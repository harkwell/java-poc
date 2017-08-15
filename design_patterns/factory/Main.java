package com.khallware.patterns.factory;

/**
 * The factory pattern consolidates the construction of specific pojos grouping
 * them generically.
 *
 * Build:
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.patterns.factory.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/factory-pattern.jar -C /tmp com/
 * java -jar /tmp/factory-pattern.jar
 * rm -rf /tmp/com /tmp/manifest /tmp/factory-pattern.jar
 */
public class Main
{
	public static interface Coin
	{
		public double getValue();
	}

	public static class Quarter implements Coin
	{
		public double getValue()
		{
			return(0.25);
		}
	}

	public static class Dime implements Coin
	{
		public double getValue()
		{
			return(0.10);
		}
	}

	public static class Nickel implements Coin
	{
		public double getValue()
		{
			return(0.05);
		}
	}

	public static class Penny implements Coin
	{
		public double getValue()
		{
			return(0.01);
		}
	}

	public static class CoinFactory
	{
		public static enum Type { quarter, dime, nickel, penny };

		public Coin make(Type type)
		{
			Coin retval = null;

			switch (type) {
			case quarter: 
				retval = new Quarter();
				break;
			case dime: 
				retval = new Dime();
				break;
			case nickel: 
				retval = new Nickel();
				break;
			case penny: 
				retval = new Penny();
				break;
			}
			return(retval);
		}
	}

	public static void main(String... args) throws Exception
	{
		CoinFactory factory = new CoinFactory();
		Coin[] coins = new Coin[] {
			factory.make(CoinFactory.Type.quarter),
			factory.make(CoinFactory.Type.dime),
			factory.make(CoinFactory.Type.dime),
			factory.make(CoinFactory.Type.quarter),
			factory.make(CoinFactory.Type.penny),
			factory.make(CoinFactory.Type.nickel),
			factory.make(CoinFactory.Type.nickel)
		};
		double sum = 0.0;

		for (Coin coin : coins) {
			sum += coin.getValue();
		}
		System.out.printf("coins sum to: %.2f\n", sum);
	}
}
