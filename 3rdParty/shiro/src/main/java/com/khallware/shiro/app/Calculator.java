package com.khallware.shiro.app;

import java.util.List;

public class Calculator
{
	public int add(List<Integer> values) throws IllegalStateException
	{
		int retval = 0;

		for (int value : values) {
			if (value < 0) {
				throw new IllegalStateException(
					"negative values are not allowed");
			}
			retval += value;
		}
		return(retval);
	}

	public int subtract(List<Integer> values) throws IllegalStateException
	{
		int retval = 0;

		for (int value : values) {
			if (value < 0) {
				throw new IllegalStateException(
					"positive values are not allowed");
			}
			retval -= value;
		}
		return(retval);
	}

	public int multiply(List<Integer> values) throws IllegalStateException
	{
		int retval = 1;

		for (int value : values) {
			if (value < 0) {
				throw new IllegalStateException(
					"only positive values > 0 are allowed");
			}
			retval *= value;
		}
		return(retval);
	}

	public int divide(List<Integer> values) throws IllegalStateException
	{
		int retval = 1;

		for (int value : values) {
			if (value < 0) {
				throw new IllegalStateException(
					"only positive values > 0 are allowed");
			}
			retval /= value;
		}
		return(retval);
	}
}
