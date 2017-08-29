package com.khallware.poc.echosvr;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Proof of Concept (PoC) to implement a single-threaded echo server in java.
 *
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.poc.echosvr.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/echosvr-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -jar /tmp/echosvr-poc.jar 7777 2>/dev/null
 * echo hello world |nc localhost 7777
 * rm /tmp/echosvr-poc.jar
 */
public class Main
{
	public static final int PORT = 7;

	public static void main(String... args) throws Exception
	{
		int port = (args.length > 0) ? Integer.parseInt(args[0]) : PORT;
		ServerSocket socket = new ServerSocket(port);
		System.err.println("Started echo server on port "+port);

		while (true) {
			String input = null;
			Socket client = socket.accept();
			System.err.println("Accepted connection from client");
			BufferedReader reader = new BufferedReader(
				new InputStreamReader(client.getInputStream()));

			while ((input = reader.readLine()) != null) {
				System.out.println(input);
				client.getOutputStream().write(
					(input+"\n").getBytes());
			}
			System.err.println("Closing connection with client");
			reader.close();
			client.close();
		}
	}
}
