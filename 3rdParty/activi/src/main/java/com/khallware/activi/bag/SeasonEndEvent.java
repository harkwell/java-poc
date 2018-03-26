package com.khallware.activi.bag;

import java.util.ArrayList;
import java.util.List;

public class SeasonEndEvent implements Event<String>
{
	private static final List<String> litter = new ArrayList<>();

	static {
		litter.add("rag");
		litter.add("papers");
	};

	public static final List<String> getLitter()
	{
		return(litter);
	}
}
