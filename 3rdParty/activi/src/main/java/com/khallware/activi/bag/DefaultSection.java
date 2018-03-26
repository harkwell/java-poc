package com.khallware.activi.bag;

import java.util.List;

public class DefaultSection implements Section
{
	private List<String> litter = null;

	public DefaultSection(SeasonEndEvent event)
	{
		this.litter = event.getLitter();
	}

	@Override
	public List<String> getLitter()
	{
		return(litter);
	}
}
