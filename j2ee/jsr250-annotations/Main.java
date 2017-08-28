package com.khallware.jsr250;

import java.lang.annotation.Retention;
import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import org.reflections.Reflections;
import java.util.Arrays;
import java.util.Map;

/**
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=url \
 *     -Dartifact=org.reflections:reflections:0.9.11
 * REFL_JAR=$(find $POC_MAVEN_REPO -name \*reflections\*jar)
 * javac -d /tmp -cp $REFL_JAR Main.java
 * echo 'Main-Class: com.khallware.jsr250.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/jsr250-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 *
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=url \
 *     -Dartifact=com.google.guava:guava:23.0
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=url \
 *     -Dartifact=org.javassist:javassist:3.22.0-CR2
 * JAR1=$(find $POC_MAVEN_REPO -name \*google\*jar)
 * JAR2=$(find $POC_MAVEN_REPO -name \*javassist\*jar)
 * java -cp /tmp/jsr250-poc.jar:$REFL_JAR:$JAR1:$JAR2 com.khallware.jsr250.Main
 * rm -rf /tmp/jsr250-poc.jar $POC_MAVEN_REPO
 */
public class Main
{
	private static Map<String,String> map = new java.util.HashMap<>();

	static {
		map.put("Generated",
			"code written externally by utilities");
		map.put("Resource",
			"refers to an external resource (eg database)");
	};

	@Retention(RetentionPolicy.RUNTIME)
	public @interface MyAnnotation {}

	public static void main(String... args) throws Exception
	{
		String name = null;
		String[] list = new String[] {
			"javax.annotation", "com.khallware.jsr250",
			"java.lang"
		};
		for (String pkg : list) {
			Reflections r = new Reflections(pkg);
			System.out.printf("Annotations in package: %s (%d)\n",
				pkg, r.getSubTypesOf(Annotation.class).size());

			for (Class clazz : r.getSubTypesOf(Annotation.class)) {
				name = clazz.getSimpleName();
				System.out.printf("\t%s - %s\n",
					name,
					(map.containsKey(name))
						? map.get(name)
						: "not JSR250");
			}
		}
	}
}
