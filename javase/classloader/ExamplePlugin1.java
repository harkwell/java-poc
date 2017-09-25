package foo.plugins;

import com.khallware.poc.classloader.api.Plugin;

public class ExamplePlugin1 implements Plugin
{
	public boolean execute(String... parms)
	{
		for (String parm : parms) {
			System.out.printf("Hello %s\n", parm);
		}
		return(true);
	}
}
