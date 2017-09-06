package com.khallware.poc.websockets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Map;
import org.glassfish.tyrus.server.Server;

public class Main
{
	public static void main(String... args) throws Exception
	{
		Server svr = new Server(args[0], Integer.parseInt(args[1]),
			(args.length > 2) ? args[2] : "/khallware",
			(Map<String,Object>)null, MyServerEndpoint.class);
		svr.start();
		System.out.print("press enter to quit...");
		new BufferedReader(new InputStreamReader(System.in)).readLine();
		svr.stop();
	}
}
