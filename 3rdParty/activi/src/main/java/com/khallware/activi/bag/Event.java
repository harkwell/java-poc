package com.khallware.activi.bag;

import java.util.Date;

public interface Event<T>
{
	default Date getOccurrence()
	{
		return(new Date());
	}
}
