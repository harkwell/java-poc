package com.khallware.cdi;

public class ShoeOnEvent
{
	private String side = "left";

	public ShoeOnEvent(String side)
	{
		System.out.printf("%s shoe event constructed...\n", side);
		this.side = side;
	}

	public String getSide()
	{
		return(side);
	}
}
