package com.khallware.cdi;

public class ShoeOnEvent
{
	private String side = "left";

	public ShoeOnEvent(String side)
	{
		this.side = side;
	}

	public String getSide()
	{
		return(side);
	}
}
