package com.khallware.batch;

import com.khallware.batch.HardwareFactory.Hardware;
import javax.batch.api.chunk.AbstractItemReader;
import java.util.List;

public class HardwareReader extends AbstractItemReader
{
	public Object readItem()
	{
		List<Hardware> retval = new java.util.ArrayList<>();

		for (HardwareFactory.Type t : HardwareFactory.Type.values()) {
			retval.add(HardwareFactory.make(t));
		}
		return(retval);
	}
}
