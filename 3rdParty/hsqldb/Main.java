package com.khallware.poc.hsqldb;

/**
 * A Proof of Concept for the hsqldb in-memory hypersonic database.
 *
 * BUILD:
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mkdir -p $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=https://mvnrepository.com/ \
 *     -Dartifact=org.hsqldb:hsqldb:2.4.0
 * HSQLDB_JAR=$(find $POC_MAVEN_REPO -name \*hsqldb\*jar)
 * javac -d /tmp -cp $HSQLDB_JAR Main.java
 * echo 'Main-Class: com.khallware.poc.hsqldb.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/hsqldb-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * 
 * java -cp $HSQLDB_JAR:/tmp/hsqldb-poc.jar \
 *          com.khallware.poc.hsqldb.Main mytablename key1 value1
 * rm -rf /tmp/hsqldb-poc.jar $POC_MAVEN_REPO mytest*
 *
 */

import java.sql.Connection;
import java.sql.DriverManager;

public class Main
{
	public static void main(String... args) throws Exception
	{
		Class.forName("org.hsqldb.jdbcDriver");
		Connection connection =
			//DriverManager.getConnection("jdbc:hsqldb:mem:mytest");
			DriverManager.getConnection("jdbc:hsqldb:file:mytest");
		connection.createStatement().executeUpdate(String.format(
			"CREATE TABLE %s ("
			+          "key VARCHAR(1024), "
			+          "value VARCHAR(1024)"
			+")",
			(args.length > 0) ? args[0] : "mytable"));
		connection.createStatement().executeUpdate(String.format(
			"INSERT INTO %s (key, value) "
			+    "VALUES ('%s', '%s')",
			(args.length > 0) ? args[0] : "mytable",
			(args.length > 1) ? args[1] : "key",
			(args.length > 2) ? args[2] : "value"));
		connection.close();
	}
}
