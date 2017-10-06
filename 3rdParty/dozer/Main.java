package com.khallware.poc.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapping;

/**
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * 
 * for dep in  org.slf4j:slf4j-api:1.7.25 \
 *       org.apache.commons:commons-lang3:3.6 \
 *       com.github.dozermapper:dozer-core:6.1.0; do
 *    mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *        org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *        -DrepoUrl=https://mvnrepository.com/ \
 *        -Dartifact=$dep
 * done
 * DOZER_JAR=$(find $POC_MAVEN_REPO -name \*dozer\*jar)
 * JAR1=$(find $POC_MAVEN_REPO -name \*slf4j\*jar)
 * JAR2=$(find $POC_MAVEN_REPO -name \*lang\*jar)
 * JAR3=$(find $POC_MAVEN_REPO -name \*beanutils\*jar)
 * JAR4=$(find $POC_MAVEN_REPO -name \*logging-1\*jar)
 * javac -d /tmp -cp $DOZER_JAR 3rdParty/dozer/Main.java
 * echo 'Main-Class: com.khallware.poc.dozer.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/dozer-poc.jar -C /tmp com
 *
 * export CLASSPATH=/tmp/dozer-poc.jar:$DOZER_JAR:$JAR1:$JAR2:$JAR3:$JAR4
 * java -cp $CLASSPATH com.khallware.poc.dozer.Main
 * rm -rf /tmp/com /tmp/manifest /tmp/dozer-poc.jar
 *
 */
public class Main
{
	public static class Dog
	{
		private String name = "";
		private String eyecolor = "";
		private double noselen = 0.0d;

		public Dog() {}

		public Dog(String name, String eyecolor, double noselen)
		{
			this.name = name;
			this.eyecolor = eyecolor;
			this.noselen = noselen;
		}

		public String getName()
		{
			return(name);
		}

		public String getEyecolor()
		{
			return(eyecolor);
		}

		public double getNoselen()
		{
			return(noselen);
		}

		@Override
		public String toString()
		{
			return(String.format("dog: [ name=\"%s\", eyes=\"%s\", "
				+"nose=%.2din ]\n", name, eyecolor, noselen));
		}
	}

	public static class Wolf
	{
		private String label = "";
		private String eyecolor = "";
		private int snoutlen = 0;

		@Mapping("name")
		public String getLabel()
		{
			return(label);
		}

		public void setLabel(String label)
		{
			this.label = label;
		}

		public void setEyecolor(String eyecolor)
		{
			this.eyecolor = eyecolor;
		}

		@Mapping("noselen")
		public int getSnoutlen()
		{
			return(snoutlen);
		}

		public void setSnoutlen(int snoutlen)
		{
			this.snoutlen = snoutlen;
		}

		@Override
		public String toString()
		{
			return(String.format("wolf: [ label=\"%s\", "
				+"eyes=\"%s\", snout=%din ]\n", label,
				eyecolor, snoutlen));
		}
	}

	public static void main(String... args) throws Exception
	{
		DozerBeanMapper mapper = new DozerBeanMapper();
		Dog dog = new Dog("fido", "brown", 3.2d);
		Wolf wolf = mapper.map(dog, Wolf.class);
		System.out.println(""+wolf);
	}
}
