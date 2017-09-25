package com.khallware.poc.classloader;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.zip.ZipFile;
import java.util.zip.ZipException;
import java.util.zip.ZipEntry;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;

public class MyClassLoader extends ClassLoader
{
	protected Map<String, Class<?>> classes = new HashMap<>();
	protected File pluginsDir = null;

	public MyClassLoader(String dirname)
	{
		super(MyClassLoader.class.getClassLoader());
		try {
			pluginsDir = new File(dirname);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Class loadClass(String className) throws ClassNotFoundException
	{
		Class retval = null;

		if ((retval = findClass(className)) == null) {
			throw new ClassNotFoundException();
		}
		return(retval);
	}

	@Override
	public Class findClass(String className)
	{
		try {
			return(findClassUnwrapped(className));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> listClasses()
	{
		List<String> retval = new ArrayList<>();

		for (File file : pluginsDir.listFiles((dir, name) -> {
					return(name.endsWith(".jar"));
				})) {
			System.out.printf("reading classes from %s\n", file);
			retval.addAll(listJarClasses(file));
		}
		return(retval);
	}

	public static List<String> listJarClasses(File jar)
	{
		try {
			return(listJarEntriesWithExtension(jar, ".class"));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String> listJarEntriesWithExtension(	
			File jar, String ext)
	{
		List<String> retval = new ArrayList<String>();

		for (String entry : listJar(jar)) {
			if (entry.endsWith(ext)) {
				retval.add(entry);
			}
		}
		return(retval);
	}

	public static List<String> listJar(File jar)
	{
		List<String> retval = new ArrayList<>();

		try (ZipFile zip = new ZipFile(jar)) {
			for (ZipEntry zipEntry : Collections.list(
					zip.entries())) {
				retval.add(zipEntry.getName());
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		return(retval);
	}

	public Class findClassInJar(String fqcn, File jar)
	{
		boolean cached = classes.containsKey(fqcn);
		cached = (cached && classes.get(fqcn) != null);
		try {
			return((cached)
				? classes.get(fqcn)
				: findClassInJarUnwrapped(fqcn, jar));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static String normalizeClassname(String classname)
	{
		String retval = (classname.endsWith(".class"))
			? classname
			: classname+".class";
		retval = retval.replaceAll("/", ".");
		return(retval);
	}

	private Class findClassUnwrapped(String classname)
			throws IOException
	{
		Class retval = null;
		try {
			retval = (classes.containsKey(classname))
				? classes.get(classname)
				: findSystemClass(classname);
		}
		catch (Exception e) {
		}
		if (retval != null) {
			return(retval); // found in our caches or from system
		}
		for (File file : pluginsDir.listFiles((dir, name) -> {
					return(name.endsWith(".jar"));
				})) {
			if (retval != null) {
				break;
			}
			retval = findClassInJar(classname, file);
		}
		if (retval != null) {
			classes.put(classname, retval);
		}
		return(retval); // found by our code
	}

	private Class findClassInJarUnwrapped(String fqcn, File jar)
			throws ZipException, IllegalArgumentException,
			IllegalStateException, IOException, SecurityException
	{
		Class retval = null;
		String given = normalizeClassname(fqcn);

		try (ZipFile zip = new ZipFile(jar)) {
			for (ZipEntry zipEntry : Collections.list(
					zip.entries())) {
				String found = normalizeClassname(
					zipEntry.getName());

				if (!found.equals(given)) {
					continue;
				}
				byte[] buff = new byte[(int)zipEntry.getSize()];

				try (InputStream is = zip.getInputStream(
						zipEntry)) {
					is.read(buff, 0, buff.length);
					// retval = defineClass(fqcn, buff, 0,
					retval = defineClass(null, buff, 0,
						buff.length, null);
				}
				finally {
					System.out.printf("failed: %s\n", fqcn);
				}
				break;
			}
		}
		return(retval);
	}
}
