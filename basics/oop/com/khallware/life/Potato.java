package com.khallware.life;

import static java.lang.Boolean.TRUE; // allows symbol "TRUE" to be used

public class Potato extends Solanum implements Main.Species
{
	private static Integer foo;

	// static initialization blocks are allowed in java
	static {
		foo = new Integer(1);
	};

	public Potato()
	{
		this.scientific = "Solanum tuberosum";
		setCommonName("Potato");
	}

	// all objects have a toString() method
	// casts like ""+myobject will call this method...
	@Override
	public String toString()
	{
		return(Util.lifeToString(this));
	}

	// * when two primitives are compared with ==, then their values are
	//   evaluated for equality
	// * when a primitive and an object that can be unboxed to a primitive
	//   are compared, those values are also evaluated for equality
	// * when two objects are evaluated, their reference is evaluated
	// * to compare two objects, best to call obj1.equals(obj2);
	@Override
	public boolean equals(Object obj)
	{
		return(obj.hashCode() == hashCode());
	}

	@Override
	public int hashCode()
	{
		return((""+TRUE).length());
	}
}
