package com.khallware.cdi;

import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.se.SeContainer;

/**
 * A simple example showing CDI (context and dependency injection).
 * Tie shoes when they are put on the foot.
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=url \
 *     -Dartifact=javax.enterprise:cdi-api:2.0
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=url \
 *     -Dartifact=javax.inject:javax.inject:1
 * CDI_JAR=$(find $POC_MAVEN_REPO -name \*cdi\*jar)
 * INJECT_JAR=$(find $POC_MAVEN_REPO -name \*inject\*jar)
 * javac -d /tmp -cp $CDI_JAR:$INJECT_JAR *java
 * echo 'Main-Class: com.khallware.cdi.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/cdi-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 *
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=url \
 *     -Dartifact=org.jboss.weld.se:weld-se-shaded:3.0.0.Final
 * WELD_JAR=$(find $POC_MAVEN_REPO -name \*weld\*jar)
 * java -Djavax.enterprise.inject.scan.implicit=true \
 *      -cp /tmp/cdi-poc.jar:/tmp:$CDI_JAR:$INJECT_JAR:$WELD_JAR \
 *      com.khallware.cdi.Main
 * rm -rf /tmp/cdi-poc.jar $POC_MAVEN_REPO
 */
public class Main
{
	public static void main(String... args) throws Exception
	{
		SeContainer ctnr = null;
		ctnr = SeContainerInitializer.newInstance().initialize();
		System.out.println("putting on the left shoe...");
		ctnr.getBeanManager().fireEvent(new ShoeOnEvent("left"));
		System.out.println("putting on the right shoe...");
		ctnr.getBeanManager().fireEvent(new ShoeOnEvent("right"));
		ctnr.close();
	}
}
