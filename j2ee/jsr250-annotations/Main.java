package com.khallware.jsr250;

import java.lang.annotation.Retention;
import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

/**
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=https://mvnrepository.com/ \
 *     -Dartifact=javax.annotation:javax.annotation-api:1.3
 * ANNOT_JAR=$(find $POC_MAVEN_REPO -name \*annot\*jar)
 * javac -d /tmp -cp $ANNOT_JAR Main.java
 * echo 'Main-Class: com.khallware.jsr250.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/jsr250-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 *
 * java -cp /tmp/jsr250-poc.jar:$ANNOT_JAR com.khallware.jsr250.Main
 * rm -rf /tmp/jsr250-poc.jar $POC_MAVEN_REPO
 */
public class Main
{
	private static Map<String,String> map = new java.util.HashMap<>();

	// from wikipedia.org (jsr_250)
	static {
		map.put("javax.annotation.Generated", "Marks sources that have been generated");
		map.put("javax.annotation.Resource", "Declares a reference to a resource, e.g. a database");
		map.put("javax.annotation.Resources", "Container for multiple Resource annotations");
		map.put("javax.annotation.PostConstruct", "Is used on methods that need to get executed after dependency injection is done to perform any initialization.");
		map.put("javax.annotation.PreDestroy", "Is used on methods that are called before the instance is removed from the container");
		map.put("javax.annotation.Priority", "Is used to indicate in what order the classes should be used. For, e.g., the Interceptors specification defines the use of priorities on interceptors to control the order in which interceptors are called.");
		map.put("javax.annotation.security.RunAs", "Defines the role of the application during execution in a Java EE container");
		map.put("javax.annotation.security.RolesAllowed", "Specifies the security roles permitted to access method(s) in an application.");
		map.put("javax.annotation.security.PermitAll", "Specifies that all security roles are permitted to access the annotated method, or all methods in the annotated class.");
		map.put("javax.annotation.security.DenyAll", "Specifies that no security roles are allowed to invoke the specified method(s).");
		map.put("javax.annotation.security.DeclareRoles", "Used to specify the security roles by the application.");
		map.put("javax.annotation.sql.DataSourceDefinition", "Is used to define a container DataSource and be registered with JNDI. The DataSource may be configured by setting the annotation elements for commonly used DataSource properties.");
		map.put("javax.annotation.ManagedBean", "Is used to declare a Managed Bean which are container managed objects that support a small set of basic services such as resource injection, lifecycle callbacks and interceptors.");
	};

	@Retention(RetentionPolicy.RUNTIME)
	public @interface MyAnnotation {}

	public static void main(String... args) throws Exception
	{
		ClassLoader cl = ClassLoader.getSystemClassLoader();

		for (String key : map.keySet()) {
			Class clazz = cl.loadClass(key);
			System.out.printf("\t%s - %s\n", key, map.get(key));
		}
	}
}
