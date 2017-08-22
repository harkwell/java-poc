package com.khallware.batch;

import com.khallware.batch.HardwareFactory.Hardware;
import com.khallware.batch.HardwareFactory.Washer;
import com.khallware.batch.HardwareFactory.Bolt;
import com.khallware.batch.HardwareFactory.Nut;
import javax.batch.api.chunk.ItemProcessor;
import java.util.List;

public class HardwareProcessor implements ItemProcessor
{
	public Object processItem(Object input)
	{
		List<Hardware> list = (List<Hardware>)input;
		Bolt bolt = null;
		Washer washer = null;
		Nut nut = null;

		for (Hardware piece : list) {
			if (bolt == null && piece instanceof Bolt) {
				bolt = (Bolt)piece;
			}
			else if (washer == null && piece instanceof Washer) {
				washer = (Washer)piece;
			}
			else if (nut == null && piece instanceof Nut) {
				nut = (Nut)piece;
			}
		}
		return(new Assembly(bolt,washer,nut));
	}
}
