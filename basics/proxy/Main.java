package com.khallware.proxy;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Proof of Concept (PoC) to pass data from one local IP port to destination.
 *
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.proxy.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/proxy-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -jar /tmp/proxy-poc.jar 4444 www.google.com 80
 * rm /tmp/proxy-poc.jar
 *
 */
public class Main
{
	public static void main(String... args) throws Exception
	{
		int srcPort = Integer.parseInt(args[0]);
		int destPort = Integer.parseInt(args[2]);
		String destHost = args[1];
		ServerSocket server = new ServerSocket(srcPort);
		Socket dest = new Socket(destHost, destPort);
		Socket src = server.accept();
		final InputStream is = src.getInputStream();
		final OutputStream os = dest.getOutputStream();

		new Thread(new Runnable() {
			public void run()
			{
				flow(is, os);
			}
		}).start();
		flow(dest.getInputStream(), src.getOutputStream());
	}

	private static void flow(InputStream is, OutputStream os)
	{
		try {
			for (int ch=0; (ch = is.read()) != -1;) {
				os.write(ch);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.printf("%s\n", ""+e);
			System.exit(1);
		}
	}
}
