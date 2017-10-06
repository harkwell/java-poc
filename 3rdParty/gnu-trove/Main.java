package com.khallware.poc.trove;

import gnu.trove.list.TLongList;
import gnu.trove.stack.TLongStack;
import java.util.stream.LongStream;
import java.util.Random;

/**
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=https://mvnrepository.com/ \
 *     -Dartifact=net.sf.trove4j:trove4j:3.0.3
 * TROVE_JAR=$(find $POC_MAVEN_REPO -name \*trove\*jar)
 * export CLASSPATH=$TROVE_JAR
 * javac -d /tmp 3rdParty/gnu-trove/Main.java
 * echo 'Main-Class: com.khallware.poc.trove.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/trove-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -cp $CLASSPATH:/tmp/trove-poc.jar com.khallware.poc.trove.Main
 */
public class Main
{
	private static void showcaseList()
	{
		TLongList list = new gnu.trove.list.array.TLongArrayList();
		long count = 0;
		list.add(LongStream.range(0,20).toArray());
		System.out.printf("largest number=%d\n", list.max());
		System.out.printf("smallest number=%d\n", list.min());
		list.shuffle(new Random());
		list.forEach((val) -> {
			System.out.printf("%d ", val);
			return(true);
		});
		System.out.printf("\nsum of (%s)=%d\n", ""+list.subList(5,10),
			list.subList(5,10).sum());
		System.out.printf("sublist evens: %s\n",
				""+list.subList(5,10).grep((val) -> {
			return(Math.floorMod(val, 2) == 0);
		}));
		list.clear();
		list.fill(0, 100, 0);
	}

	private static void showcaseStack()
	{
		TLongStack stack = new gnu.trove.stack.array.TLongArrayStack();
		LongStream.range(1,21).forEach((val) -> { stack.push(val); });
		System.out.printf("created stack of size %d\n", stack.size());
		System.out.printf("top of stack contains %d\n", stack.peek());

		for (int i=0; i<5; i++) {
			System.out.printf("popped %d\n", stack.pop());
		}
		System.out.printf("stack size is now %d\n", stack.size());
		stack.clear();
	}

	public static void main(String... args) throws Exception
	{
		Main.showcaseList();
		Main.showcaseStack();
	}
}
