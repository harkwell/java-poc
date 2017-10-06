package com.khallware.poc.apache.lang;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.ArrayUtils;
import java.util.stream.IntStream;

/**
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=https://mvnrepository.com/ \
 *     -Dartifact=org.apache.commons:commons-lang3:3.6
 * APACHE_JAR=$(find $POC_MAVEN_REPO -name \*lang\*jar)
 * javac -cp $APACHE_JAR -d /tmp Main.java
 * echo 'Main-Class: com.khallware.poc.apache.lang.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/commons-lang-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -cp $APACHE_JAR:/tmp/commons-lang-poc.jar \
 *      com.khallware.poc.apache.lang.Main
 * rm -rf /tmp/commons-lang-poc.jar $POC_MAVEN_REPO
 *
 */
public class Main
{
	private static enum MyEnum { VAL1, VAL2, VAL3, VAL4, VAL5 };

	public static void showcaseEnumUtils()
	{
		long mask = EnumUtils.<MyEnum>generateBitVector(
			MyEnum.class, MyEnum.VAL1, MyEnum.VAL1, MyEnum.VAL2,
			MyEnum.VAL5, MyEnum.VAL3, MyEnum.VAL4);
		System.out.printf("mask: %s\n", String.format("0b%16s",
			Long.toBinaryString(mask)).replace(' ', '0'));
		mask = 0b00101;  // ie VAL1, VAL3

		for (MyEnum e : EnumUtils.<MyEnum>processBitVector(
				MyEnum.class, mask)) {
			System.out.printf("found %s\n", ""+e);
		}
	}

	public static void showcaseArrayUtils()
	{
		int[] list1 = new int[] { 5, 4, 8, 2, 3, 0, 7, 9 };
		int[] list2 = IntStream.range(0,8).toArray();
		System.out.printf("add to an array: %s\n",
			ArrayUtils.toString(ArrayUtils.add(list1, 2, 4)));
		System.out.printf("remove elements in an array: %s\n",
			ArrayUtils.toString(ArrayUtils.removeElements(
				list1, 3, 0, 2)));
		System.out.printf("list1 and list2 are %sof the same length\n",
			ArrayUtils.isSameLength(list1, list2) ? "" : "not ");
		System.out.printf("list1 and list2 are %sof the same type\n",
			ArrayUtils.isSameType(list1, list2) ? "" : "not ");
		System.out.printf("list2 is %ssorted\n",
			ArrayUtils.isSorted(list2) ? "" : "not ");
		System.out.printf("list2 clone %s\n",
			ArrayUtils.toString(ArrayUtils.clone(list2)));
		ArrayUtils.reverse(list2, 2, 4);
		System.out.printf("reverse (from 2 to 4): %s\n",
			ArrayUtils.toString(list2));
		ArrayUtils.reverse(list2);
		System.out.printf("reversed: %s\n", ArrayUtils.toString(list2));
		ArrayUtils.shift(list2, 3);
		System.out.printf("shifted: %s\n", ArrayUtils.toString(list2));
		ArrayUtils.shuffle(list2);
		System.out.printf("shuffled: %s\n", ArrayUtils.toString(list2));
		ArrayUtils.swap(list2, 2, 5);
		System.out.printf("swaped 2 and 5: %s\n",
			ArrayUtils.toString(list2));
	}

	public static void main(String... args) throws Exception
	{
		showcaseEnumUtils();
		showcaseArrayUtils();
	}
}
