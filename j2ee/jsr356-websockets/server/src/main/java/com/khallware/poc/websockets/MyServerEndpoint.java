package com.khallware.poc.websockets;

import java.io.IOException;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.Session;
import javax.websocket.OnMessage;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@ServerEndpoint(value = "/accumulator")
public class MyServerEndpoint
{
	private static final Logger logger = LoggerFactory.getLogger(
		MyServerEndpoint.class);
	private int[] figures;
	private int sum = 0;

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
		new Thread(() -> {
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
		}).start();
	}

	@OnMessage
	public void onMessage(String content, Session session)
	{
		logger.debug("received \"{}\"", content);
		try {
			int number = Integer.parseInt(content);
			sum += number;

			if (number == 0) {
				CloseCodes code = CloseCodes.NORMAL_CLOSURE;
				logger.info("client, you win!");
				session.getBasicRemote().sendText("sum="+sum);
				session.close(new CloseReason(code,""));
			}
		}
		catch (IOException|NumberFormatException e) {
			logger.info(content);
		}
	}

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
}
