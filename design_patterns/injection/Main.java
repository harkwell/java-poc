package com.khallware.patterns.injection;

/**
 * The injection pattern facilitates better separation of concerns, and
 * testability by requiring that objects be passed in instead of creating them
 * directly.
 *
 * Build:
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.patterns.injection.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/injection-pattern.jar -C /tmp com/
 * java -jar /tmp/injection-pattern.jar
 * rm -rf /tmp/com /tmp/manifest /tmp/injection-pattern.jar
 */
public class Main
{
	public static interface Service
	{
		public void serve();
	}

	public static interface Consumer
	{
		public void consume();
	}

	public static interface Injector
	{
		public Consumer getConsumer();
	}

	public static class BlockToyService implements Service
	{
		public void serve()
		{
			System.out.println("Some blocks to play with.");
		}
	}

	public static class TinkerToyService implements Service
	{
		public void serve()
		{
			System.out.println("Some tinker toys to play with.");
		}
	}

	public static class BlockToyConsumer implements Consumer
	{
		private BlockToyService svc = null;

		public BlockToyConsumer(BlockToyService svc)
		{
			this.svc = svc;
		}

		public void consume()
		{
			svc.serve();
		}
	}

	public static class TinkerToyConsumer implements Consumer
	{
		private TinkerToyService svc = null;

		public TinkerToyConsumer(TinkerToyService svc)
		{
			this.svc = svc;
		}

		public void consume()
		{
			svc.serve();
		}
	}

	public static class BlockToyInjector implements Injector
	{
		public Consumer getConsumer()
		{
			return(new BlockToyConsumer(new BlockToyService()));
		}
	}

	public static class TinkerToyInjector implements Injector
	{
		public Consumer getConsumer()
		{
			return(new TinkerToyConsumer(new TinkerToyService()));
		}
	}

	public static void main(String... args) throws Exception
	{
		Injector injector = new BlockToyInjector();
		injector.getConsumer().consume();

		injector = new TinkerToyInjector();
		injector.getConsumer().consume();
	}
}
