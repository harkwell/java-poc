package com.khallware.batch;

import com.khallware.batch.HardwareFactory.Bolt;
import com.khallware.batch.HardwareFactory.Washer;
import com.khallware.batch.HardwareFactory.Nut;

public class Assembly
{
	private Bolt bolt = null;
	private Washer washer = null;
	private Nut nut = null;

	public Assembly(Bolt bolt, Washer washer, Nut nut)
	{
		this.bolt = bolt;
		this.washer = washer;
		this.nut = nut;
	}

	@Override
	public String toString()
	{
		return(String.format("Insert washer %d onto bolt %d "
			+"and screw nut %d onto bolt.\n", washer.hashCode(),
			bolt.hashCode(), nut.hashCode()));
	}
}
