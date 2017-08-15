package com.khallware.patterns.strategy;

import java.util.Map;

/**
 * The strategy pattern facilitates the dynamic changing of algorithms that
 * are applied to a given instance.
 *
 * Build:
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.patterns.strategy.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/strategy-pattern.jar -C /tmp com/
 * java -jar /tmp/strategy-pattern.jar
 * rm -rf /tmp/com /tmp/manifest /tmp/strategy-pattern.jar
 */
public class Main
{
	public static enum Genre { rock, country, bluegrass, classical };
	public static Map<Genre,String[]> playlist = new java.util.HashMap<>();

	static {
		playlist.put(Genre.rock, new String[] {
			"Shout, by Tears For Fears",
			"Sweet Child of Mine, Guns-N-Roses",
			"Who's Cryin' Now, Journey",
			"Old Man, ZZ Top"
		});
		playlist.put(Genre.country, new String[] {
			"Smokey Mt.Rain, Ronnie Millsap",
			"Rhinestone Cowboy, Glen Campbell"
		});
		playlist.put(Genre.bluegrass, new String[] {
			"Oh Death, Ralph Stanley",
			"Man of Constant Sorrow, Alison Krauss & Union Station"
		});
		playlist.put(Genre.classical, new String[] {
			"Eine Kleine Nachtmusik, Mozart",
			"Requiem, Mozart"
		});
	};

	public static interface OrderStrategy
	{
		public String getSong(Genre genre);
	}

	public static class ShuffleStrategy implements OrderStrategy
	{
		public static final Genre DEFAULT = Genre.rock;

		public String getSong(Genre request)
		{
			String retval = "";
			Genre genre = (request == null) ? DEFAULT : request;
			int len = playlist.get(genre).length;
			int idx = (int)(Math.random() * len);
			retval = playlist.get(genre)[idx];
			return(retval);
		}
	}

	public static class SequentialStrategy implements OrderStrategy
	{
		private Genre lastGenre = null;
		private int lastIdx = -1;

		public String getSong(Genre request)
		{
			String retval = "";
			Genre genre = (request == null) ? Genre.rock : request;
			int len = playlist.get(genre).length;
			int idx = (lastGenre == genre) ? (++lastIdx % len) : 0;
			retval = playlist.get(genre)[idx];
			lastGenre = genre;
			lastIdx = idx;
			return(retval);
		}
	}

	public static class MusicPlayer
	{
		private OrderStrategy strategy = new SequentialStrategy();

		public void setStrategy(OrderStrategy strategy)
		{
			this.strategy = strategy;
		}

		public void playSong(Genre genre)
		{
			System.out.println(strategy.getSong(genre));
		}
	}

	public static void main(String... args) throws Exception
	{
		MusicPlayer player = new MusicPlayer();
		player.playSong(Genre.classical);
		player.setStrategy(new ShuffleStrategy());
		player.playSong(null);
		player.playSong(Genre.rock);
		player.playSong(Genre.rock);
		player.setStrategy(new SequentialStrategy());
		player.playSong(null);
		player.playSong(Genre.country);
	}
}
