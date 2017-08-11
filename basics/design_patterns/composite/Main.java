package com.khallware.patterns.composite;

import java.util.List;
import java.util.stream.IntStream;

/**
 * The composite pattern is the application of functionality to a single
 * object as it is to a group of objects.  It allows one to treat each
 * instance in a group by calling the composite head and traversing to
 * every instance grouped therein.  All items look alike (common interface).
 *
 * Build:
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.patterns.composite.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/composite-pattern.jar -C /tmp com/
 * java -jar /tmp/composite-pattern.jar
 * rm -rf /tmp/com /tmp/manifest /tmp/composite-pattern.jar
 */
public class Main
{
	public static interface Sum
	{
		public void add(int value);
		public int getTotal();
	}

	public static class CompositeSum implements Sum
	{
		private List<Sum> list = new java.util.ArrayList<>();

		public void add(Sum sum)
		{
			list.add(sum);
		}

		public void add(int value)
		{
			for (Sum sum : list) {
				sum.add(value);
			}
		}

		public int getTotal()
		{
			int retval = 0;

			for (Sum sum : list) {
				retval += sum.getTotal();
			}
			return(retval);
		}
	}

	public static class MySum implements Sum
	{
		private int total = 0;

		public void add(int value)
		{
			total += value;
		}

		public int getTotal()
		{
			return(total);
		}
	}

	public static void main(String... args) throws Exception
	{
		Sum compositeSum = new CompositeSum();
		MySum mysum = new MySum();
		IntStream.range(0, 4).forEachOrdered(n -> {
			((CompositeSum)compositeSum).add(new MySum());
		});
		mysum.add(5);
		((CompositeSum)compositeSum).add(mysum);
		compositeSum.add(5);
		// 5 x 6 = 30
		System.out.printf("The answer = %d\n", compositeSum.getTotal());
	}
}
