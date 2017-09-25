package bar.plugins;

import com.khallware.poc.classloader.api.Plugin;

public class ExamplePlugin2 implements Plugin
{
	public boolean execute(String... parms)
	{
		for (String parm : parms) {
			System.out.printf("Greetings %s\n", parm);
		}
		return(true);
	}
}
