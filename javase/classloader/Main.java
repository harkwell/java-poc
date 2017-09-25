package com.khallware.poc.classloader;

import com.khallware.poc.classloader.api.Plugin;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.File;
import java.io.FileInputStream;

/**
 * javac -d /tmp Main.java Plugin.java MyClassLoader.java
 * echo 'Main-Class: com.khallware.poc.classloader.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/cl-plugins-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 *
 * mkdir -p /tmp/plugins/
 * java -jar /tmp/cl-plugins-poc.jar /tmp/plugins "Kevin Hall:khallware.com"
 *
 * javac -d /tmp Plugin.java ExamplePlugin1.java
 * jar cvf /tmp/plugins/foo-plugins.jar -C /tmp foo
 * rm -rf /tmp/com /tmp/foo
 * # <hit enter to re-run plugins>
 *
 * javac -d /tmp Plugin.java ExamplePlugin2.java
 * jar cvf /tmp/plugins/bar-plugins.jar -C /tmp bar
 * rm -rf /tmp/com /tmp/bar
 * # <hit enter to re-run plugins>
 */
public class Main
{
	public static Set<Plugin> getPlugins(MyClassLoader cl)
	{
		Set<Plugin> retval = new HashSet<>();
		Class<? extends Plugin> clazz = null;
		System.out.println("reading plugins...");

		for (String classname : cl.listClasses()) {
			try {
				Class found = cl.loadClass(classname);

				if (Plugin.class.isAssignableFrom(found)) {
					clazz = (Class<? extends Plugin>)
						cl.loadClass(classname);
					retval.add((Plugin)clazz.newInstance());
				}
			}
			catch (Exception e) {
				continue;
			}
		}
		return(retval);
	}

	public static void main(String... args) throws Exception
	{
		MyClassLoader cl = new MyClassLoader(args[0]);

		while (true) {
			for (Plugin plugin : Main.getPlugins(cl)) {
				System.out.printf("executing plugin: [%s]\n",
					plugin.getClass().getName());
				plugin.execute(args[1].split(":"));
			}
			System.out.println("hit enter to re-run plugins...");
			System.in.read();
		}
	}
}
