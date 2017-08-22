package com.khallware.batch;

import javax.batch.api.chunk.AbstractItemWriter;
import java.util.List;

public class AssemblyWriter extends AbstractItemWriter
{
	public void writeItems(List<Object> list)
	{
		for (Object obj : list) {
			Assembly assembly = (Assembly)obj;
			System.out.println(""+assembly);
		}
	}
}
