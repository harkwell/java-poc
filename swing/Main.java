package com.khallware.swing.biff;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Color;
import java.io.File;

/**
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.swing.biff.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest myswingapp.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -jar /tmp/myswingapp.jar "$MAIL" 5
 */
public class Main extends JFrame
{
	public static final String FLAG_UP = "#ffffff";
	public static final String FLAG_DOWN = "#51748e";
	public static final int DEF_WAIT_SEC = 15;
	private static final long serialVersionUID = 1L;
	private static final JButton button = new JButton("");
	private static final String fmt = "You have %s mail";
	private static long lastModified = -1;
	private static File mbox = null;
	private static long wait = -1;

	public Main(String filespec, int wait)
	{
		mbox = new File(filespec);
		lastModified = mbox.lastModified();
		this.wait = wait;
		this.getContentPane().setLayout(new FlowLayout());
		this.setTitle("Biff");

		button.setBackground(makeColor(FLAG_DOWN));
		button.setText(String.format(fmt, "no unread"));
		this.add(button);
		button.addActionListener((ActionEvent event) -> {
			button.setBackground(makeColor(FLAG_DOWN));
			button.setText(String.format(fmt,"no unread"));
		});
	}

	public void refresh()
	{
		if (mbox.lastModified() > lastModified) {
			lastModified = mbox.lastModified();
			button.setBackground(makeColor(FLAG_UP));
			button.setText(String.format(fmt,"new"));
		}
	}

	private static Color makeColor(String hex)
	{
		return(new Color(
			Integer.valueOf(hex.substring(1,3), 16),
			Integer.valueOf(hex.substring(3,5), 16),
			Integer.valueOf(hex.substring(5,7), 16)));
	}

	private static void startGUI(String filespec, int wait)
	{
		JFrame frame = new Main(filespec, wait);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Thread(() -> {
			try {
				while (true) {
					Thread.sleep(wait * 1000);
					((Main)frame).refresh();
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).start();
	}

	public static void main(String... args) throws Exception
	{
		int secs = (args.length > 1)
				? Integer.parseInt(args[1])
				: DEF_WAIT_SEC;
		SwingUtilities.invokeLater(() -> Main.startGUI(args[0], secs));
	}
}
