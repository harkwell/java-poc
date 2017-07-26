import java.util.Collections;
import java.util.Collection;
import java.util.Queue;
import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * cp Main.java /tmp
 * javac /tmp/Main.java
 * java -cp /tmp Main
 *
 */
public class Main
{
	/**
	 * collection - a group of objects.
	 */
	public static void collections()
	{
		Collection<String> c = new java.util.ArrayList<>();
		c.add("string1");
		c.add("string2");
		c.add("string3");
		System.out.printf("collection: %s\n", c);

		for (String item : c) {
			System.out.printf("collection item: %s\n", item);
		}
		System.out.printf("collection does %scontain item \"%s\"\n",
			(c.contains("string2")) ? "" : "not ", c.toArray()[1]);
		System.out.printf("collection contains %d items\n", c.size());
	}

	/**
	 * set - sets contain no pair of elements e1 and e2 such that
	 *       e1.equals(e2), and at most one null element. As implied by its
	 *       name, this interface models the mathematical set abstraction.
	 */
	public static void setCollection()
	{
		Set<String> set1 = new java.util.HashSet<>();
		Set<String> set2 = null;
		set1.add("string1");
		set1.add("string2");
		set1.add("string3");
		set1.add("string3");
		set1.add("string4");
		set1.remove("string4");
		set2 = (Set<String>)((java.util.HashSet)set1).clone();

		for (String item : set2) {
			System.out.printf("set item: %s\n", item);
		}
		System.out.printf("sorted set: %s\n",
			new java.util.TreeSet(set2));
	}

	/**
	 * list - An ordered collection (also known as a sequence).
	 *        The user of this interface has precise control over where in
	 *        the list each element is inserted. The user can access
	 *        elements by their integer index (position in the list), and
	 *        search for elements in the list.
	 */
	public static void listCollection()
	{
		List<String> list = new java.util.ArrayList<>();
		list.add(0, "string1");
		list.add(1, "string2");
		list.add(2, "string4");
		list.add(3, "string3");
		list.remove("string4");

		for (String item : list) {
			System.out.printf("list item: %s\n", item);
		}
		list.set(1, "mystring2");
		System.out.printf("item at index 1: \"%s\"\n", list.get(1));
		list.removeAll(Collections.<String>singleton("string1"));
		System.out.printf("list: %s\n", list);
		Collections.sort(list);
		System.out.printf("sorted list: %s\n", list);
		list.clear();
		System.out.printf("empy list: %s\n", list);
	}

	/**
	 * map - An object that maps keys to values. A map cannot contain
	 *       duplicate keys; each key can map to at most one value.
	 */
	public static void maps()
	{
		Map<String,String> map = new java.util.HashMap<>();
		map.put("key1","value1");
		map.put("key2","value2");
		System.out.printf("map: %s\n", map);

		for (String key : map.keySet()) {
			System.out.printf("map item (%s): %s\n",
				key, map.get(key));
		}
		map.clear();
		System.out.printf("%smap: %s\n",
			(map.isEmpty()) ? "empty " : "", map);
	}

	/**
	 * queue - A collection designed for holding elements prior to
	 *         processing. 
	 */
	public static void queues()
	{
		Queue<String> queue = new java.util.LinkedList<>();
		queue.add("item1");
		queue.add("item2");
		System.out.printf("queue: %s\n", queue);

		for (String item : queue) {
			System.out.printf("queue item: %s\n", item);
		}
		for (String item; ((item = queue.poll()) != null);) {
			System.out.printf("consumed queue item: %s\n", item);
		}
		System.out.printf("empty queue: %s\n", queue);
	}

	public static void main(String... args) throws Exception
	{
		Main.collections();
		Main.setCollection();
		Main.listCollection();
		Main.maps();
		Main.queues();
	}
}
