package com.khallware.life;  // creates a namespace for our program

public class Main
{
	public interface Life { };  // a marker interface has no methods

	public interface Domain extends Life  // interfaces can inherit others
	{
		// all methods in an interface are considered abstract
		public String getScientificName();
		public String getCommonName();
		public String getDomain();
	}

	// notice that interfaces extend each other (multiple allowed),
	// also classes extend other classes (though only one is allowed)
	// classes do not extend interfaces, they implement them
	public interface Kingdom extends Domain
	{
		public String getKingdom();
	}

	public interface Phylum extends Kingdom
	{
		public String getPhylum();
	}

	public interface Clazz extends Phylum  // "class", "Class" are keywords 
	{
		public String getClazz(); // ea class inherits java.lang.Object
	}

	public interface Order extends Clazz
	{
		public String getOrder();
	}

	public interface Family extends Order
	{
		public String getFamily();
	}

	public interface Genus extends Family
	{
		public String getGenus();
	}

	public interface Species extends Genus
	{
	}

	// static nested classes can be used like regular classes,
	// inner classes (without "static"), are only useful within eg Main
	public static class AllLife implements Domain
	{
		String scientific = "unknown"; // package scope by default
		private String common = "unknown";

		@Override
		public String getScientificName() // this is a getter method
		{
			return(scientific);
		}

		@Override
		public String getCommonName()
		{
			return(common);
		}

		@Override
		public String getDomain()
		{
			return(null);
		}

		public void setCommonName(String common) // this is a setter
		{
			this.common = common;
		}
	}

	public static class Eukaryote extends AllLife implements Domain
	{
		// must be defined per "Domain" interface
		@Override
		public String getDomain()
		{
			return("Eukaryote");
		}
	}

	private static void foo()
	{
		// static methods can be called like: Main.foo();, without
		// calling new() first: ie new Main().foo();
		// if a subclass tries to override this method, then it is
		// only hidden (not overridden) because it is static.
		// that means that calling Main.foo() will call this method
		// and Subclass.foo() will call the other.
	}

	// the ... operator is called "varargs" and is essentially an array
	public static void main(String... args) throws Exception
	{
		Life[] life = new Life[] {
			new Tomato(), // create an instance of a class (object)
			new Potato()
		};
		// these items are stored in memory (heap)
		// a reference to them is stored in the stack
		// when all refs are gone, the garbage collector deletes them

		for (Life item : life) {
			// static methods can be called without a new()
			System.out.println(""+item); // Object.toString()
		}
	}
}
