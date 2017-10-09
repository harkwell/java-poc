package com.khallware.poc.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Yes, I know about libreadline-java (ubuntu 16.04).  This example shows
 * how jna could be used to leverage native C libraries without having to
 * "#include <jni.h>".
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * 
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *    org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *    -DrepoUrl=https://mvnrepository.com/ \
 *    -Dartifact=net.java.dev.jna:jna:4.5.0
 * JNA_JAR=$(find $POC_MAVEN_REPO -name \*jna\*jar)
 * javac -d /tmp -cp $JNA_JAR 3rdParty/jna/Main.java
 * echo 'Main-Class: com.khallware.poc.jna.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/jna-poc.jar -C /tmp com
 *
 * export CLASSPATH=/tmp/jna-poc.jar:$JNA_JAR
 * java -cp $CLASSPATH com.khallware.poc.jna.Main
 * rm -rf /tmp/com /tmp/manifest /tmp/jna-poc.jar
 *
 */
public class Main
{
	public static final String LIBNAME = "readline";

	public interface Readline extends Library
	{
		Readline instance = (Readline)Native.loadLibrary(
			LIBNAME, Readline.class);
			
		String readline(final String arg);
	}

	public static void main(String... args) throws Exception
	{
		System.out.println("Enter some text with libreadline.so");
		Readline.instance.readline(System.getenv("PS1"));
	}
}
