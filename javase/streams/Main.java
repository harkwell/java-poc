package com.khallware.streams;

import java.util.stream.Stream;
import java.util.Arrays;

/**
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.streams.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/streams-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -jar /tmp/streams-poc.jar terminator codeinator zoo eat-a-tator
 *
 */
public class Main
{
	public static boolean matches(String content, String... tokens)
	{
		boolean retval = true;

		for (String token : tokens) {
			retval &= content.contains(token);
		}
		return(retval);
	}

	public static String myFilter(String token)
	{
		String retval = token;
		byte[] buffer = token.getBytes();
		retval = retval.toLowerCase();
		int len = 0;

		for (int idx=0; idx<retval.length(); idx++) {
			if (idx % 2 == 0) {
				buffer[len++] = retval.getBytes()[idx];
			}
		}
		retval = new String(buffer).substring(0,len);
		return(retval);
	}

	public static void main(String... args) throws Exception
	{
		Stream<String> stream1 = Stream.<String>generate(() -> {
			return(""+((int)(100000 * Math.random()))); }).limit(2);
		Stream<String> stream2 = Arrays.asList(args).stream()
			.filter((arg) -> { return(matches(arg, "a","e","o")); })
			.sorted()
			.distinct();
		Stream.<String>concat(stream1, stream2)
			.sorted()
			.distinct()
			.skip(1)
			.map((s) -> { return(myFilter(s)); })
			.forEach(System.out::println);
	}
}
