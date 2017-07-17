package com.khallware.life;

// A POJO (plain old java object) is a simple, stand-alone data structure
public class Solanum extends Angiosperm.Solanaceae implements Main.Genus
{
	// it is not required to define an empty default constructor
	// with subclasses "new Solanum()" would first call the constructor
	// for "Solanaceae()", unless super(parm1) is called with parameters and
	// a parameterized constructor for Solanaceae exists.
	//
	// public Solanum() { }

	// this would be a parameterized constructor
	// if no default constructor is written, but a parameterized constructor
	// is, then calling new Solanum() would fail.  If both are written,
	// then "new Solanum();" would work as well as "new Solanum(1);".
	//
	// public Solanum(int foo) { }

	// this annotation requires that the method be defined in superclass
	@Override
	public String getGenus()
	{
		// "this" keyword accesses getGenus() methods instance reference
		return(this.getClass().getSimpleName());
	}

	protected void myMethod()
	{
		int i=0;
	}
}
