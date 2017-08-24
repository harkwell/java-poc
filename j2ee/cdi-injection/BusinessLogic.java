package com.khallware.cdi;

import javax.enterprise.event.Observes;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BusinessLogic
{
	public void onEvent(@Observes ShoeOnEvent event, TieService svc)
	{
		svc.tie(event.getSide());
	}
}
