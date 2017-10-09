package com.khallware.poc.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.FileDialog;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.AnimatedLabel;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import java.io.File;

/**
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * 
 * for dep in com.googlecode.lanterna:lanterna:3.0.0 \
 *       ; do
 *    mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *        org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *        -DrepoUrl=https://mvnrepository.com/ \
 *        -Dartifact=$dep
 * done
 * LANTERNA_JAR=$(find $POC_MAVEN_REPO -name \*lanterna\*jar)
 * JAR2=$(find $POC_MAVEN_REPO -name \*okhttp\*jar)
 * javac -d /tmp -cp $LANTERNA_JAR 3rdParty/lanterna/Main.java
 * echo 'Main-Class: com.khallware.poc.lanterna.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/lanterna-poc.jar -C /tmp com
 *
 * export CLASSPATH=/tmp/lanterna-poc.jar:$LANTERNA_JAR
 * java -cp $CLASSPATH com.khallware.poc.lanterna.Main basic "hello world"
 * java -cp $CLASSPATH com.khallware.poc.lanterna.Main window "hello world"
 * rm -rf /tmp/com /tmp/manifest /tmp/lanterna-poc.jar
 *
 */
public class Main
{
	public static void showcaseBasic(String title) throws IOException
	{
		Terminal term = new DefaultTerminalFactory().createTerminal();
		Screen screen = new TerminalScreen(term);
		TextGraphics tg = screen.newTextGraphics();
		int width = screen.getTerminalSize().getColumns();
		int height = screen.getTerminalSize().getRows();
		screen.startScreen();
		screen.clear();
		tg.drawRectangle(
			new TerminalPosition(0,0),
			new TerminalSize(width, height),
			'*');
		tg.putString((int)((width-title.length())/2), 3, title);
		screen.refresh();
		screen.readInput();
		screen.clear();
		screen.refresh();
		screen.stopScreen();
	}

	public static void showcaseWindow(String title) throws IOException
	{
		Terminal term = new DefaultTerminalFactory().createTerminal();
		Screen screen = new TerminalScreen(term);
		final MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);
		final BasicWindow window = new BasicWindow(title);
		Panel panel = new Panel();
		panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		panel.addComponent(new Button("Choice 1", () -> {
			MessageDialog dialog = new MessageDialogBuilder()
				.setTitle("Press Escape")
				.setText("choice 1")
				.build();
			dialog.setCloseWindowWithEscape(true);
			dialog.showDialog(gui);
		}));
		panel.addComponent(new Button("Choice 2", () -> {
			FileDialog dialog = new FileDialogBuilder()
				.setTitle("Choose a File")
				.setSelectedFile(new File("/etc/services"))
				.build();
			dialog.setCloseWindowWithEscape(true);
			dialog.showDialog(gui);
		}));
		panel.addComponent(new Button("Done", () -> {
			screen.clear();
			window.close();
			try {
				screen.stopScreen();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}));
		screen.startScreen();
		gui.setEOFWhenNoWindows(true);
		window.setComponent(panel);
		gui.addWindowAndWait(window);
	}

	public static void main(String... args) throws Exception
	{
		switch (args[0]) {
		case "window":
			Main.showcaseWindow((args.length > 1) 
				? args[1] 
				: "hello world");
			break;
		case "basic":
		default:
			Main.showcaseBasic((args.length > 1)
				? args[1]
				: "hello world");
			break;
		}
	}
}
