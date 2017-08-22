package com.khallware.batch;

public class HardwareFactory
{
	public static enum Type { BOLT, WASHER, NUT };

	public static interface Hardware
	{
	}

	public static class Bolt implements Hardware
	{
	}

	public static class Washer implements Hardware
	{
	}

	public static class Nut implements Hardware
	{
	}

	public static Hardware make(Type type)
	{
		Hardware retval = null;

		switch (type) {
		case BOLT:
			retval = new Bolt();
			break;
		case WASHER:
			retval = new Washer();
			break;
		case NUT:
			retval = new Nut();
			break;
		}
		return(retval);
	}
}
