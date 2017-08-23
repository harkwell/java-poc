package com.khallware.batch;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import java.util.List;

@Named        // CDI required for mapping batchfile.xml ref to this class
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
