package com.khallware.btree;

/**
 *
 * javac -d /tmp algorithms/btree/Main.java
 * echo 'Main-Class: com.khallware.btree.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/btree.jar -C /tmp com
 *
 * java -jar /tmp/btree.jar key1:val1 key2:val2 key3:val3 key4:val4 key5:val5
 * java -jar /tmp/btree.jar key1:val1 key5:val5 key4:val4 key3:val3 key2:val2
 * rm -rf /tmp/com /tmp/manifest /tmp/btree.jar
 *
 */
public class Main
{
	public static class BTree<K extends Comparable<K>, V>
	{
		public static final int ORDER = 4;

		private static final class Item<T>
		{
			public Node next = null;
			public Comparable key = null;
			public T val = null;

			public Item(Comparable key, T val, Node next)
			{
				this.key = key;
				this.val = val;
				this.next = next;
			}
		}

		private static final class Node
		{
			private int idx = 0;
			private Item[] items = new Item[ORDER];

			private Node(int idx)
			{
				this.idx = idx;
			}
		}

		private Node root = new Node(0);
		private int height = 0;
		private int size = 0;

		public V get(K key)
		{
			return(find(root, key, height));
		}

		public void put(K key, V val)
		{
			Node ptr = insert(root, key, val, height);
			size++;

			if (ptr != null) {
				Node node = new Node(2);
				node.items[0] = new Item(
					root.items[0].key, null, root);
				node.items[1] = new Item(
					ptr.items[0].key, null, ptr);
				root = node;
				height++;
			}
		}

		public V find(Node node, K key, int height)
		{
			V retval = null;
			Item[] list = node.items;

			if (height == 0) {
				for (int ptr = 0; ptr < node.idx; ptr++) {
					if (key.compareTo((K)
							list[ptr].key) == 0) {
						retval = (V)list[ptr].val;
					}
				}
			}
			else {
				for (int ptr = 0; ptr < node.idx; ptr++) {
					boolean b = (ptr+1 == node.idx);
					b = (b || key.compareTo(
						(K)(list[ptr+1].key)) < 0);

					if (b) {
						retval = find(
							list[ptr].next, key,
							height-1);
						break;
					}
				}
			}
			return(retval);
		}

		private Node insert(Node node, K key, V val, int height)
		{
			Node retval = null;
			Item item = new Item(key, val, null);
			int ptr = 0;

			if (height == 0) {
				for (ptr = 0; ptr < node.idx; ptr++) {
					Item i = node.items[ptr];
					boolean b = (i == null);
					b = (b || i.key == null);
					b = (b || key.compareTo((K)i.key) < 0);

					if (b) {
						break;
					}
				}
			}
			else {
				for (ptr = 0; ptr < node.idx; ptr++) {
					boolean b = (ptr+1 == node.idx);
					b = (b || key.compareTo((K)
						node.items[ptr+1].key) < 0);

					if (b) {
						Node rslt = insert(
							node.items[ptr++].next,
							key, val, height -1);

						if (rslt == null) {
							return(null);
						}
						item.key = rslt.items[0].key;
						item.next = rslt;
						break;
					}
				}
			}
			for (int i = node.idx; i > ptr; i--) {
				node.items[i] = node.items[i-1];
			}
			node.items[ptr] = item;
			// System.out.printf("inserted (%s,%s) at %d\n",
			// 	key, val, ptr);
			node.idx++;
			retval = (node.idx < ORDER) ? null : grow(node);
			return(retval);
		}

		private Node grow(Node node)
		{
			Node retval = null;
			int idx = (int)(ORDER/2);
			retval = new Node(idx);
			node.idx = idx;

			for (int x = 0; x < idx; x++) {
				retval.items[x] = node.items[idx+x];
			}
			return(retval);
		}
	}

	public static void main(String... args) throws Exception
	{
		BTree<String, String> btree = new BTree<>();

		for (String arg : args) {
			String[] data = arg.split(":");
			btree.put(data[0], data[1]);
		}
		for (String arg : args) {
			String[] data = arg.split(":");
			System.out.printf("key=\"%s\", value=\"%s\"\n",
				data[0], btree.get(data[0]));
		}
	}
}
