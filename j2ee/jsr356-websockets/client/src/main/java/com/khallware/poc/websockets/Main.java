package com.khallware.poc.websockets;

import java.net.URI;
import java.io.IOException;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.Session;
import javax.websocket.OnMessage;
import javax.websocket.CloseReason;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason.CloseCodes;
import org.glassfish.tyrus.client.ClientManager;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@ClientEndpoint
public class Main
{
	private static final Logger logger = LoggerFactory.getLogger(
		Main.class);
	private int[] figures;
	private int sum = 0;
	private static Thread worker = null;

	/**
	 * NOTE: normally we wouldn't copy and paste sections of code
	 *       like we have here (use a library instead).
	 */
	@OnOpen
	public void onOpen(Session session)
	{
		int len = (int)(Math.random() * 1000);
		logger.info("connected [{}]", session.getId());
		figures = new int[len];
		sum = 0;

		for (int idx=0; idx<(len-1); idx++) {
			figures[idx] = (int)(Math.random() * 100);
		}
		figures[len-1] = 0;
		worker = new Thread(() -> {
			CloseCodes code = CloseCodes.NORMAL_CLOSURE;
			try {
				for (int figure : figures) {
					if (!session.isOpen()) {
						continue;
					}
					session.getBasicRemote().sendText(
						""+figure);
					Thread.sleep(200);
				}
				if (session.isOpen()) {
					session.close(new CloseReason(code,""));
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		worker.start();
	}

	/**
	 * NOTE: normally we wouldn't copy and paste sections of code
	 *       like we have here (use a library instead).
	 */
	@OnMessage
	public void onMessage(String content, Session session)
	{
		logger.debug("received \"{}\"", content);
		try {
			int number = Integer.parseInt(content);
			sum += number;

			if (number == 0) {
				CloseCodes code = CloseCodes.NORMAL_CLOSURE;
				logger.info("server, you win!");
				session.getBasicRemote().sendText("sum="+sum);
				session.close(new CloseReason(code,""));
			}
		}
		catch (IOException|NumberFormatException e) {
			logger.info(content);
		}
	}

	/**
	 * NOTE: normally we wouldn't copy and paste sections of code
	 *       like we have here (use a library instead).
	 */
	@OnClose
	public void onClose(Session session, CloseReason closeReason)
	{
		CloseCodes code = (CloseCodes)closeReason.getCloseCode();

		switch (code) {
		case NORMAL_CLOSURE:
			logger.info("game over");
			break;
		default:
			logger.error("closed because "+closeReason);
			break;
		}
	}

	public static void main(String... args) throws Exception
	{
		ClientManager client = ClientManager.createClient();
		client.connectToServer(Main.class, new URI(args[0]));
		worker.join();
	}
}
