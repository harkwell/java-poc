package com.khallware.cdi;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * A simple example showing CDI (context and dependency injection).
 * Tie shoes when they are put on the foot.
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=https://mvnrepository.com/ \
 *     -Dartifact=javax.inject:javax.inject:1
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=https://mvnrepository.com/ \
 *     -Dartifact=org.jboss.weld.se:weld-se-shaded:3.0.0.Final
 * INJECT_JAR=$(find $POC_MAVEN_REPO -name \*inject\*jar)
 * WELD_JAR=$(find $POC_MAVEN_REPO -name \*weld\*jar)
 * javac -d /tmp -cp $WELD_JAR:$INJECT_JAR *java
 * echo 'Main-Class: com.khallware.cdi.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/cdi-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 *
 * java -Djavax.enterprise.inject.scan.implicit=true \
 *      -cp /tmp/cdi-poc.jar:/tmp:$INJECT_JAR:$WELD_JAR com.khallware.cdi.Main
 * rm -rf /tmp/cdi-poc.jar $POC_MAVEN_REPO
 */
public class Main
{
	public static void main(String... args) throws Exception
	{
		WeldContainer ctnr = new Weld().initialize();
		ctnr.getBeanManager().fireEvent(new ShoeOnEvent("left"));
		ctnr.getBeanManager().fireEvent(new ShoeOnEvent("right"));
		ctnr.close();
	}
}
