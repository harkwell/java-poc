package com.khallware.poc.generics;

import java.util.List;

/**
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.poc.generics.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/generics-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -jar /tmp/generics-poc.jar
 * rm /tmp/generics-poc.jar
 */
public class Main
{
	/**
	 * This is a generic class.  Type T qualifier is a placeholder symbol.
	 * When the API user instantiates this class, they define a specific
	 * type in place of T or use Object as the default.  If, instead, we
	 * had "<T extends MyBaseClass>", then the type specified must be a
	 * subclass of MyBaseClass, or MyBaseClass itself.
	 */
	public static class MyGenericClass<T>
	{
		private List<T> list = null; // T can be use generically here

		public MyGenericClass(List<T> list)
		{
			this.list = list;
		}

		public List<T> getMyList()
		{
			return(list);
		}
	}

	public static class Util
	{
		// a generic method can be defined like so:
		public static <T> void fill(MyGenericClass<T> mgc, T... items)
		{
			for (T item : items) {
				mgc.getMyList().add(item);
			}
		}

		public static void dump(MyGenericClass<?> item)
		{
			System.out.printf("len=%d %s\n",
				item.getMyList().size(),
				item.getMyList().toString());
		}
	}

	// this illustrates the use of a generic class...
	public static void genericClass()
	{
		List<MyGenericClass<?>> list = new java.util.ArrayList<>();
		list.add(new MyGenericClass<String>(
			new java.util.ArrayList<String>()));
		list.add(new MyGenericClass<Integer>(
			new java.util.ArrayList<Integer>()));

		System.out.printf("generic list contains %d items\n",
			list.size());
	}

	public static void genericMethod()
	{
		MyGenericClass<String> item = new MyGenericClass<>(
			new java.util.ArrayList<String>());
		Util.<String>fill(item, "foo","bar","baz");
		Util.dump(item);
	}

	public static void typeErasure()
	{
		List<String> list1 = new java.util.ArrayList<>();
		List list2 = list1; // an example of type erasure by javac
		list1.add("foo");
		System.out.printf("%s\n", list2); // uses Object.toString()
	}

	public static void main(String... args) throws Exception
	{
		Main.genericClass();
		Main.genericMethod();
		Main.typeErasure();
	}
}
