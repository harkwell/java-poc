package com.khallware.poc.url;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;

/**
 * Proof of Concept (PoC) to connect and read from a socket expressed as a URL.
 *
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.poc.url.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/url-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -jar /tmp/url-poc.jar https://www.google.com/
 * rm /tmp/url-poc.jar
 *
 */
public class Main
{
	public static void main(String... args) throws Exception
	{
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(
			new URL(args[0]).openConnection().getInputStream()));

		while (reader.ready()) {
			System.out.printf("%s\n", reader.readLine());
		}
		reader.close();
	}
}
