package com.khallware.patterns.singleton;

public final class MySingleton
{
	private static MySingleton instance = null;

	private MySingleton()
	{
		System.out.println("constructor called");
	} 

	public static MySingleton getMySingleton()
	{
		if (instance == null) {
			instance = new MySingleton();
		}
		return(instance);
	}

	public String doStuff()
	{
		return("doing stuff");
	}
}
