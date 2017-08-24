package com.khallware.cdi;

import javax.inject.Inject;

public class TieService
{
	@Inject private Hands hands;

	public void tie(String side)
	{
		System.out.printf("The %s shoe has been tied by %s hands.",
			side, hands.getName());
	}
}
