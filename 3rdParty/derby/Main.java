package com.khallware.poc.derby;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.File;

/**
 * A Proof of Concept for the java derby embedded database.
 *
 * BUILD:
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mkdir -p $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=https://mvnrepository.com/ \
 *     -Dartifact=org.apache.derby:derby:10.8.3.0
 * DERBY_JAR=$(find $POC_MAVEN_REPO -name \*derby\*jar)
 * javac -d /tmp -cp $DERBY_JAR Main.java
 * echo 'Main-Class: com.khallware.poc.derby.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/derby-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * 
 * java -cp $DERBY_JAR:/tmp/derby-poc.jar com.khallware.poc.derby.Main \
 *          "some text"
 * rm -rf derby* notes.derby/ /tmp/derby-poc.jar $POC_MAVEN_REPO
 *
 */
public class Main
{
	private static Connection connection = null;
	public static final String DBFILE = "notes.derby";
	public static final String URL = "jdbc:derby:"+DBFILE+";create=true";

	public static void init() throws Exception
	{
		boolean createTable = (!new File(DBFILE).exists());
		Main.connection = DriverManager.getConnection(URL);
		String SQL = "CREATE TABLE list (content VARCHAR(1024))";

		if (createTable) {
			Statement statement = Main.connection.createStatement();
			statement.executeUpdate(SQL);
			statement.close();
		}
	}

	public static void insert(String content) throws Exception
	{
		Statement statement = Main.connection.createStatement();
		String SQL = "INSERT INTO list VALUES ('"+content+"')";
		statement.executeUpdate(SQL);
		statement.close();
	}

	public static void dump() throws Exception
	{
		Statement statement = Main.connection.createStatement();
		String SQL = "SELECT content FROM list";
		ResultSet rslt = statement.executeQuery(SQL);
		System.out.printf("Dumping Datbase (%s):\n", DBFILE);

		while (rslt.next()) {
			System.out.printf("%s\n", rslt.getString("content"));
		}
		statement.close();
	}

	public static void main(String... args) throws Exception
	{
		Main.init();

		for (String content : args) {
			Main.insert(content);
		}
		Main.dump();
	}
}
