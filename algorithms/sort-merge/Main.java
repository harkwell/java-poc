package com.khallware.sort.merge;

/**
 *
 * javac -d /tmp algorithms/sort-merge/Main.java
 * echo 'Main-Class: com.khallware.sort.merge.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/merge-sort.jar -C /tmp com
 *
 * java -jar /tmp/merge-sort.jar val1 val5 val4 val3 val2 val9 val7 val6 val8
 * rm -rf /tmp/com /tmp/manifest /tmp/merge-sort.jar
 *
 */
public class Main
{
	private static Comparable[] sort(Comparable[] list)
	{
		Comparable[] retval = null;
		int mid = (int)(list.length/2);
		Comparable[] one = new Comparable[mid];
		Comparable[] two = new Comparable[list.length-mid];

		if (list.length <= 1) {
			retval = list;
		}
		else {
			int idx = 0;

			for (Comparable item : list) {
				if (idx < mid) {
					one[idx] = item;
				}
				else {
					two[idx-mid] = item;
				}
				idx++;
			}
			one = sort(one);
			two = sort(two);
			retval = merge(one, two);
		}
		return(retval);
	}

	private static Comparable[] merge(Comparable[] one, Comparable[] two)
	{
		Comparable[] retval = new Comparable[one.length+two.length];
		int idx = 0;
		int idx1 = 0;
		int idx2 = 0;

		while (idx1 < one.length && idx2 < two.length) {
			if (one[idx1].compareTo(two[idx2]) < 0) {
				retval[idx++] = one[idx1++];
			}
			else {
				retval[idx++] = two[idx2++];
			}
		}
		while (idx1 < one.length) {
			retval[idx++] = one[idx1++];
		}
		while (idx2 < two.length) {
			retval[idx++] = two[idx2++];
		}
		return(retval);
	}

	public static void main(String... args) throws Exception
	{
		for (Comparable rslt : sort(args)) {
			System.out.println(""+rslt);
		}
	}
}
