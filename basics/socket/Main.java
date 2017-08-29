package com.khallware.poc.socket;

import java.net.Socket;

/**
 * Proof of Concept (PoC) to check availability of a port on a server.
 *
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.poc.socket.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/socket-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -jar /tmp/socket-poc.jar www.google.com 80
 * rm /tmp/socket-poc.jar
 *
 */
public class Main
{
	public static void main(String... args) throws Exception
	{
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		Socket socket = new Socket(host, port);
		System.out.printf("The socket is %sconnected.",
			(socket.isConnected()) ? "" : "not ");
		socket.close();
	}
}
