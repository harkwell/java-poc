package com.khallware.activi.bag;

public interface Bear
{
	public Bag getBag();
	public String getName();
	public void pickupLitter(Section section);

	default void dumpLitter(Park park)
	{
		Util.scatterLitter(getBag().getLitter(), park);
		getBag().empty();
	}
}
