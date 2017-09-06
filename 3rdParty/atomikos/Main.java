package com.khallware.poc.jta;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import java.util.Properties;
import java.sql.Connection;
import javax.sql.DataSource;
//import javax.jta.TransactionManager;

/**
 * A Proof of Concept for the java atomikos transaction manager.
 *
 * BUILD:
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mkdir -p $POC_MAVEN_REPO
 * LIST="
 *    com.atomikos:transactions-jta:4.0.4
 *    com.atomikos:transactions-osgi:4.0.4
 *    javax:javaee-api:7.0
 *    org.hsqldb:hsqldb:2.4.0
 * "
 * for product in $LIST; do
 *    mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *        org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *        -DrepoUrl=https://mvnrepository.com/ \
 *        -Dartifact=$product
 * done
 * JTA_JAR=$(find $POC_MAVEN_REPO -name \*jta\*jar)
 * JAR1=$(find $POC_MAVEN_REPO -name \*osgi\*jar)
 * JAR2=$(find $POC_MAVEN_REPO -name \*javaee\*jar)
 * JAR3=$(find $POC_MAVEN_REPO -name \*hsql\*jar)
 * javac -d /tmp -cp $JTA_JAR:$JAR1:$JAR2 Main.java
 * echo 'Main-Class: com.khallware.poc.jta.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/jta-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * 
 * java -cp $JTA_JAR:$JAR1:$JAR2:$JAR3:/tmp/jta-poc.jar \
 *          com.khallware.poc.jta.Main
 * rm -rf /tmp/jta-poc.jar $POC_MAVEN_REPO
 *
 */
public class Main
{
	public static AtomikosDataSourceBean init()
	{
		AtomikosDataSourceBean retval = null;
		Properties props = new Properties();
		try {
			String driver = "org.hsqldb.jdbc.pool.JDBCXADataSource";
			Class.forName("org.hsqldb.jdbcDriver");
			retval = new AtomikosDataSourceBean();
			retval.setMaxPoolSize(20);
			retval.setUniqueResourceName("poc");
			retval.setXaDataSourceClassName(driver);
			props.setProperty("url", "jdbc:hsqldb:file:poc");
			retval.setXaProperties(props);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		return(retval);
	}

	public static void main(String... args) throws Exception
	{
		UserTransactionManager trxMgr = new UserTransactionManager();
		DataSource datasource = null;
		Connection connection = null;
		trxMgr.init();
		datasource = init();
		connection = datasource.getConnection();
		System.out.println("creating table");
		connection.createStatement().executeUpdate(
			"CREATE TABLE tbl ("
			+          "key VARCHAR(1024), "
			+          "value VARCHAR(1024)"
			+")");
		System.out.println("inserting data into table");
		try {
			trxMgr.begin();
			connection.createStatement().executeUpdate(
				"INSERT INTO tbl (key, value) "
				+    "VALUES ('key1', 'val1')");
			trxMgr.commit();

			System.out.println("querying table");
			connection.createStatement().executeQuery(
				"SELECT * FROM tbl");
		}
		catch (Throwable e) {
			trxMgr.rollback();
		}
		connection.close();
		trxMgr.close();
	}
}
