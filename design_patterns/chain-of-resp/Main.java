package com.khallware.patterns.chain;

/**
 * The chain of responsibility pattern decouples senders and receivers of
 * messages by passing the message along a chain of receivers until an
 * appropriate handler is found.
 *
 * Build:
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.patterns.chain.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/chain-pattern.jar -C /tmp com/
 * java -jar /tmp/chain-pattern.jar banana
 * rm -rf /tmp/com /tmp/manifest /tmp/chain-pattern.jar
 */
public class Main
{
	public static interface Message
	{
		public String getBody();
	}

	public static interface Receiver
	{
		public boolean receive(Message message);
	}

	public static class Receiver1 implements Receiver
	{
		public boolean receive(Message message)
		{
			boolean retval = ("apple".equals(message.getBody()));

			if (retval) {
				System.out.println("got an apple!");
			}
			return(retval);
		}
	}

	public static class Receiver2 implements Receiver
	{
		public boolean receive(Message message)
		{
			boolean retval = ("grape".equals(message.getBody()));

			if (retval) {
				System.out.println("I like grapes!");
			}
			return(retval);
		}
	}

	public static class Receiver3 implements Receiver
	{
		public boolean receive(Message message)
		{
			boolean retval = ("banana".equals(message.getBody()));

			if (retval) {
				System.out.println("Bananas!");
			}
			return(retval);
		}
	}

	public static void main(String... args) throws Exception
	{
		Receiver[] chain = new Receiver[] {
			new Receiver1(),
			new Receiver2(),
			new Receiver3()
		};
		for (Receiver receiver : chain) {
			if (receiver.receive(() -> { return(args[0]); })) {
				break;
			}
		}
	}
}
