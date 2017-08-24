package com.khallware.cdi;

import javax.inject.Inject;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TieService
{
	@Inject private Hands hands;

	public void tie(String side)
	{
		System.out.printf("The %s shoe has been tied by %s hands.\n",
			side, hands.getName());
	}
}
