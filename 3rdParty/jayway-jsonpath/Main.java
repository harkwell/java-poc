package com.khallware.poc.jsonpath;

import com.jayway.jsonpath.JsonPath;

/**
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * 
 * for dep in com.jayway.jsonpath:json-path:2.4.0 \
 *       net.minidev:asm:1.0.2 \
 *       org.slf4j:slf4j-api:1.7.25 \
 *       net.minidev:json-smart:2.3; do
 *    mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *        org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *        -DrepoUrl=https://mvnrepository.com/ \
 *        -Dartifact=$dep
 * done
 * JSONPATH_JAR=$(find $POC_MAVEN_REPO -name \*path\*jar)
 * JAR1=$(find $POC_MAVEN_REPO -name \*smart\*jar)
 * JAR2=$(find $POC_MAVEN_REPO -name \*asm-1\*jar)
 * JAR3=$(find $POC_MAVEN_REPO -name \*slf4j\*jar)
 * javac -d /tmp -cp $JSONPATH_JAR 3rdParty/jayway-jsonpath/Main.java
 * echo 'Main-Class: com.khallware.poc.jsonpath.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/jsonpath-poc.jar -C /tmp com
 *
 * export CLASSPATH=/tmp/jsonpath-poc.jar:$JSONPATH_JAR:$JAR1:$JAR2:$JAR3
 * java -cp $CLASSPATH com.khallware.poc.jsonpath.Main \
 *         "$(curl -s https://api.github.com/users/harkwell)" '$.avatar_url'
 * rm -rf /tmp/com /tmp/manifest /tmp/jsonpath-poc.jar
 *
 */
public class Main
{
	public static void main(String... args) throws Exception
	{
		System.out.println(""+JsonPath.parse(args[0]).read(args[1]));
	}
}
