package com.khallware.poc.apache.cli;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;

/**
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=url \
 *     -Dartifact=commons-cli:commons-cli:1.4
 * CLI_JAR=$(find $POC_MAVEN_REPO -name \*cli-1.4\*jar)
 * javac -cp $CLI_JAR -d /tmp Main.java
 * echo 'Main-Class: com.khallware.poc.apache.cli.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/commons-cli-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -cp $CLI_JAR:/tmp/commons-cli-poc.jar com.khallware.poc.apache.cli.Main
 * rm -rf /tmp/commons-cli-poc.jar $POC_MAVEN_REPO
 *
 */
public class Main
{
	private static final Options opts = new Options();
	private static final String VERSION = "0.1.0.00001";
	private static boolean verbose = false;
	private static boolean debug = false;

	public static interface Handler
	{
		public void handle(Option opt, String val, Properties props);
	}

	private static Handler usageHandler = (opt, val, props) -> {
		new HelpFormatter().printHelp(
			"Main", "this is a commons-cli example",
			opts, "http://www.khallware.com/", true);
		System.exit(1);
	};

	public static Map<Option, Handler> getOptions()
	{
		Map<Option, Handler> retval = new HashMap<>();
		retval.put(
			Option.builder("h")
				.argName("help")
				.longOpt("help")
				.desc("help")
				.required(false)
				.build(),
			usageHandler
		);
		retval.put(
			Option.builder("d")
				.argName("debug")
				.longOpt("debug")
				.desc("debug")
				.required(false)
				.build(),
			(opt, val, props) -> {
				System.out.println("processing debug");
				Main.debug = true;
			}
		);
		retval.put(
			Option.builder("v")
				.argName("verbose")
				.longOpt("verbose")
				.desc("verbose")
				.required(false)
				.build(),
			(opt, val, props) -> {
				System.out.println("processing verbose");
				Main.verbose = true;
			}
		);
		retval.put(
			Option.builder("V")
				.argName("version")
				.longOpt("version")
				.desc("version")
				.required(false)
				.build(),
			(opt, val, props) -> {
				System.out.println("version: "+VERSION);
			}
		);
		retval.put(
			Option.builder("n")
				.argName("name")
				.longOpt("name")
				.desc("name")
				.required(false)
				.build(),
			(opt, val, props) -> {
				System.out.println("name: "+val+" "+props);
			}
		);
		retval.put(
			Option.builder("l")
				.argName("list")
				.longOpt("list")
				.desc("list of options")
				.required(false)
				.type(List.class)
				.numberOfArgs(2)
				.valueSeparator(',')
				.build(),
			(opt, val, props) -> {
				System.out.println("name: "+val+" "+props);
			}
		);
		return(retval);
	}

	public static void main(String... args) throws Exception
	{
		DefaultParser parser = new DefaultParser();
		Map<Option, Handler> map = getOptions();
		CommandLine cmdline = null;

		for (Option opt : map.keySet()) {
			opts.addOption(opt);
		}
		try {
			cmdline = parser.parse(opts, args);
		}
		catch (UnrecognizedOptionException e) {
			System.out.println(""+e);
			usageHandler.handle(null, null, null);
		}
		for (Option opt : map.keySet()) {
			String optname = opt.getOpt();

			if (optname == null || !cmdline.hasOption(optname)) {
				// System.out.println("no option "+optname);
				continue;
			}
			map.get(opt).handle(
				opt,
				cmdline.getOptionValue(optname),
				cmdline.getOptionProperties(optname));
		}
		System.out.printf("debug is %son, verbose is %son\n",
			(debug) ? "" : "not ", (verbose) ? "" : "not ");
	}
}
