package com.khallware.patterns.command;

import java.util.List;

/**
 * The command pattern facilitates undo, redo and operation history
 * features by encapsulating a request class.
 *
 * Build:
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.patterns.command.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/command-pattern.jar -C /tmp com/
 * java -jar /tmp/command-pattern.jar
 * rm -rf /tmp/com /tmp/manifest /tmp/command-pattern.jar
 */
public class Main
{
	public static interface Command
	{
		public boolean exec();
	}

	public static final class Control
	{
		private List<Command> history = new java.util.ArrayList<>();
		private int idx = 0;

		public void perform(Command command)
		{
			if (command.exec()) {
				history.add(idx++, command);
			}
		}

		public boolean redo()
		{
			boolean retval = false;
			Command command = null;

			if ((command = history.get(idx)) != null) {
				if ((retval = command.exec())) {
					idx++;
				}
			}
			return(retval);
		}

		public void undo()
		{
			idx = Math.max(--idx, 0);
		}

		public String history()
		{
			StringBuilder retval = new StringBuilder();

			for (Command command : history) {
				retval.append("\t"+command+"\n");
			}
			return(String.format("history (%d items):\n%s\n",
				history.size(), ""+retval));
		}
	}

	public static void main(String... args) throws Exception
	{
		Control ctrl = new Control();
		ctrl.perform(() -> {
			System.out.println("command one");
			return(true);
		});
		ctrl.perform(() -> {
			System.out.println("command two");
			return(true);
		});
		ctrl.perform(() -> {
			System.out.println("command three fails...");
			return(false);
		});
		ctrl.perform(() -> {
			System.out.println("command four");
			return(true);
		});
		ctrl.undo();
		ctrl.undo();
		ctrl.redo();
		ctrl.perform(() -> {
			System.out.println("command FOUR");
			return(true);
		});
		System.out.println(ctrl.history());
	}
}
