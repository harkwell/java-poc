package com.khallware.activi.bag;

import java.util.ArrayList;
import java.util.List;

public class Bag
{
	private List<String> litter = null;

	public Bag()
	{
		litter = new ArrayList<String>();
	}

	public List<String> getLitter()
	{
		return(litter);
	}

	public void putIn(String item)
	{
		getLitter().add(item);
	}

	public void empty()
	{
		litter.clear();
	}
}
