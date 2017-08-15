package com.khallware.patterns.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * The observer pattern (or Hollywood Callback -Don't call us, we'll call you)
 * describes the mechanism where all observing clients are called upon state
 * change.
 *
 * Build:
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.patterns.observer.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/observer-pattern.jar -C /tmp com/
 * java -jar /tmp/observer-pattern.jar
 * rm -rf /tmp/com /tmp/manifest /tmp/observer-pattern.jar
 */
public class Main
{
	public static interface Handler
	{
		public void handle(String item);
	}

	public static class Actor
	{
		List<Handler> observers = new ArrayList<>();
		String token = "foo";

		public void changeState(String token)
		{
			for (Handler handler : observers) {
				handler.handle(token);
			}
			this.token = token;
		}

		public void register(Handler handler)
		{
			observers.add(handler);
		}
	}

	public static void main(String... args) throws Exception
	{
		Actor actor = new Actor();
		actor.register((token) -> {
			System.out.printf("observer1 handles \"%s\"\n", token);
		});
		actor.register((token) -> {
			System.out.printf("observer2 observed \"%s\"\n", token);
		});
		actor.changeState("foo");
		actor.changeState("bar");
	}
}
