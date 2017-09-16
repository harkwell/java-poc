package com.khallware.poc.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * 
 * for dep in org.junit.platform:junit-platform-console-standalone:1.0.0-M4 \
 *       org.junit.jupiter:junit-jupiter-api:5.0.0-M4 \
 *       org.junit.jupiter:junit-jupiter-params:5.0.0-M4; do
 *    mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *        org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *        -DrepoUrl=url \
 *        -Dartifact=$dep
 * done
 * RUNNER_JAR=$(find $POC_MAVEN_REPO -name \*console\*jar)
 * JUNIT_JAR=$(find $POC_MAVEN_REPO -name \*jupiter-api\*jar)
 * JAR1=$(find $POC_MAVEN_REPO -name \*params\*jar)
 * javac -d /tmp -cp $JUNIT_JAR:$JAR1 3rdParty/junit5/Main.java # *.java
 * echo 'Main-Class: com.khallware.poc.junit.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/junit-poc.jar -C /tmp com
 * export CLASSPATH=/tmp/junit-poc.jar:$RUNNER_JAR
 * #java -jar $RUNNER_JAR --class-path /tmp/junit-poc.jar --scan-class-path
 * #java -jar $RUNNER_JAR --class-path /tmp/junit-poc.jar \
 * #     --select-class com.khallware.poc.junit.Main
 * java -cp $CLASSPATH org.junit.platform.console.ConsoleLauncher \
 *      --class-path $CLASSPATH --select-package com.khallware.poc.junit \
 *      --include-classname ".*"
 * rm -rf /tmp/com /tmp/manifest /tmp/junit-poc.jar
 *
 */
public class Main
{
	private static int count = -1;

	@BeforeAll
	public static void init()
	{
		count = 0;
	}

	@Test
	public void simpleTest()
	{
		boolean expected = true;
		boolean actual = true;
		System.out.println("performing simpleTest()...");
		assertEquals(expected, actual);
		assertTrue(count == 0);
	}

	@ParameterizedTest
	@ValueSource(strings = { "token1", "token2", "token3" })
	public void parameterizedTest(String data)
	{
		System.out.println("performing parameterizedTest()...");
		assertTrue(data.startsWith("token"));
	}

	public static void main(String... args) throws Exception
	{
	}
}
