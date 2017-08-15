package com.khallware.patterns.iterator;

import java.util.Set;
import java.util.Arrays;
import java.util.Iterator;

/**
 * The iterator pattern empowers one to observe each element sequentially
 * without exposing its inner workings.
 *
 * Build:
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.patterns.iterator.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/iterator-pattern.jar -C /tmp com/
 * java -jar /tmp/iterator-pattern.jar
 * rm -rf /tmp/com /tmp/manifest /tmp/iterator-pattern.jar
 */
public class Main
{
	public static class MySet<T> implements Iterable<T>
	{
		private Set<T> set = null;

		public MySet(T... items)
		{
			set = new java.util.HashSet<>(Arrays.asList(items));
		}

		public Iterator<T> iterator()
		{
			return(set.iterator());
		}
	}

	public static class MyItem
	{
		private String item = null;

		public MyItem(String item)
		{
			this.item = item;
		}

		public String getItem()
		{
			return(item);
		}
	}

	public static void main(String... args) throws Exception
	{
		MyItem[] items = new MyItem[] {
			new MyItem("foo"), new MyItem("bar"), new MyItem("baz"),
			new MyItem("doh"), new MyItem("stuff")
		};
		MySet<MyItem> myset = new MySet<>(items);

		for (MyItem item : myset) {
			System.out.println(item.getItem());
		}
	}
}
