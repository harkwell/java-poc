package com.khallware.cdi;

import javax.enterprise.event.Observes;

public class BusinessLogic
{
	public void onEvent(@Observes ShoeOnEvent event, TieService svc)
	{
		svc.tie(event.getSide());
	}
}
