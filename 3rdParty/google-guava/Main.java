package com.khallware.poc.guava;

// import com.google.common.graph.Graphs;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.eventbus.Subscribe;
import com.google.common.eventbus.EventBus;
import com.google.common.net.InternetDomainName;
import com.google.common.net.HostAndPort;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.base.Splitter;
import com.google.common.base.Optional;
import com.google.common.reflect.ClassPath;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Set;
import java.io.File;
import java.io.IOException;

/**
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=https://mvnrepository.com/ \
 *     -Dartifact=com.google.guava:guava:23.0
 * GUAVA_JAR=$(find $POC_MAVEN_REPO -name \*guava\*jar)
 * javac -cp $GUAVA_JAR -d /tmp Main.java
 * echo 'Main-Class: com.khallware.poc.guava.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/guava-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -cp $GUAVA_JAR:/tmp/guava-poc.jar com.khallware.poc.guava.Main
 * rm -rf /tmp/guava-poc.jar $POC_MAVEN_REPO
 *
 */
public class Main
{
	private static enum State { ZERO, CHECK, PENNY, NICKEL, DIME, QUARTER,
		DONE };

	public static void showcaseGraph()
	{
		MutableGraph<State> graph = GraphBuilder
			.directed()
			.allowsSelfLoops(false)
			.build();
		graph.putEdge(State.ZERO, State.CHECK);
		graph.putEdge(State.CHECK, State.PENNY);
		graph.putEdge(State.CHECK, State.NICKEL);
		graph.putEdge(State.CHECK, State.DIME);
		graph.putEdge(State.CHECK, State.QUARTER);
		graph.putEdge(State.PENNY, State.CHECK);
		graph.putEdge(State.NICKEL, State.CHECK);
		graph.putEdge(State.DIME, State.CHECK);
		graph.putEdge(State.QUARTER, State.CHECK);
		graph.putEdge(State.CHECK, State.DONE);
		traverse(graph, State.ZERO, 0);
	}

	public static void showcaseHostAndPort()
	{
		HostAndPort pair = null;
		String[] data = new String[] {
			"localhost:8080",
			"www.google.com:80",
			"127.0.0.1:443",
			"[2001:db8::1]:80"
		};
		for (String token : data) {
			pair = HostAndPort.fromString(token);
			System.out.printf("%s is host %s and port %d\n",
				token, pair.getHost(), pair.getPort());
		}
	}

	public static class Event1 {}
	public static class Event2 {}

	public static class Handler
	{
		@Subscribe
		public void handle(Event1 event)
		{
			System.out.println("handling event 1");
		}

		@Subscribe
		public void handle(Event2 event)
		{
			System.out.println("handling event 2");
		}
	}

	public static void showcaseEventBus()
	{
		EventBus eventBus = new EventBus("test");
		eventBus.register(new Handler());
		eventBus.post(new Event2());
		eventBus.post(new Event1());
	}

	public static void showcaseHashing()
	{
		String msg = "this is to be hashed";
		System.out.printf("sha256 hash of \"%s\" is \"%s\"\n",
			msg, Hashing.sha256().hashBytes(msg.getBytes()));
	}

	public static void showcaseOptional()
	{
		System.out.printf("non-existent optional is %spresent, "
			+"existing item is %spresent\n",
			Optional.absent().isPresent() ? "" : "not ",
			Optional.of(new Object()).isPresent() ? "" : "not ");
	}

	public static void showcaseSplitter()
	{
		String data = "foo::bar:baz  :  doh:test";
		System.out.printf("\"%s\" splits on ':' to:\n", data);

		for (String token : Splitter.on(":")
				.trimResults()
				.omitEmptyStrings()
				.split(data)) {
			System.out.println("\""+token+"\"");
		}
	}

	public static void showcaseFiles() throws IOException
	{
		String fname = "/etc/passwd";
		System.out.printf("%s is %d bytes\n",
			fname, Files.asByteSource(new File(fname)).size());
	}

	public static void showcaseUnsignedInteger()
	{
		UnsignedInteger myint = UnsignedInteger.fromIntBits(
			Integer.MAX_VALUE);
		myint = myint.plus(UnsignedInteger.fromIntBits(353));
		System.out.printf("max java.lang.Integer %d plus 353 is %s\n",
			Integer.MAX_VALUE, ""+myint);
		System.out.printf("max int is now (2^32-1) %s\n",
			""+UnsignedInteger.MAX_VALUE);
	}

	public static void showcaseInternetDomainName()
	{
		InternetDomainName name = InternetDomainName
			.from("Khallware.com")
			.child("www");
		System.out.printf("domain name %s is%s valid\n",
			""+name,
			InternetDomainName.isValid(""+name) ? "" : "not ");
		System.out.printf("it has these parts: %s\n", name.parts());
		System.out.printf("this is the public part: %s\n",
			""+name.publicSuffix());
	}

	public static void showcaseClassPath() throws IOException
	{
		ClassPath cp = ClassPath.from(Main.class.getClassLoader());
		// String pkg = "com.khallware.poc.guava";
		String pkg = "com.google.common.base";
		System.out.println("classloaders are dynamic, so this is not "
			+"included in java.lang.relect.* or elsewhere, but "
			+"guava enables this (for "+pkg+"): ");

		for (ClassPath.ClassInfo ci : cp.getTopLevelClasses(pkg)) {
			System.out.println(ci.getName());
		}
	}

	private static void traverse(MutableGraph<State> graph, State state,
			int sum)
	{
		// Set<State> set = Graphs.<State>reachableNodes(graph, state);
		Set<State> set = graph.successors(state);
		int idx = ThreadLocalRandom.current().nextInt(set.size()+1);
		State next = null;
		int count = 0;

		for (State s : set) {
			if (count++ < idx) {
				continue;
			}
			next = s;
			break;
		}
		switch (state) {
		case ZERO:
			System.out.println("no money...");
			break;
		case CHECK:
			// System.out.printf("we now have %s cents\n", sum);
			break;
		case PENNY:
			System.out.println("adding a penny");
			sum++;
			break;
		case NICKEL:
			System.out.println("adding a nickel");
			sum += 5;
			break;
		case DIME:
			System.out.println("adding a dime");
			sum += 10;
			break;
		case QUARTER:
			System.out.println("adding a quarter");
			sum += 25;
			break;
		case DONE:
		default:
			break;
		}
		next = (sum < 100 && next == null) ? State.CHECK : next;

		if (next != null) {
			traverse(graph, next, sum);
		}
		else {
			System.out.println("we have a dollar or more");
		}
	}

	public static void main(String... args) throws Exception
	{
		showcaseGraph();
		showcaseHostAndPort();
		showcaseEventBus();
		showcaseHashing();
		showcaseSplitter();
		showcaseOptional();
		showcaseFiles();
		showcaseUnsignedInteger();
		showcaseInternetDomainName();
		showcaseClassPath();
	}
}
