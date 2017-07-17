package com.khallware.life;

// OOD/OOP is about inheritance and abstraction.  This Tomato class inherits
// methods and attributes from its parent (super) class, Solanum.
public class Tomato extends Solanum implements Main.Species
{
	private String info = ""; // this data is encapsulated within the class

	public Tomato()
	{
		super(); // this calls the parents constructor, the default
		// AllLife.scientific must be in "protected" scope to set here
		this.scientific = "Solanum lycopersicum";
		setCommonName("Tomato");
	}

	// this is polymorphism, subclass myMethod() overrides its super
	// one cannot override a method within the same class
	// if an exception is thrown, must be same/more specific than its super
	// also, scope decorator must not be more restrictive than its super
	// private, static and final methods cannot be overridden
	// the return types of overridden methods must be the same
	@Override
	public void myMethod()
	{
		int i=1;
	}

	// this is also polymorphism, subclass myMethod() overloads its super
	// just changing the return type is not allowed in method overloading
	public void myMethod(int val)
	{
		int i=val;
	}

	@Override
	public String toString()
	{
		return(Util.lifeToString(this));
	}
}
