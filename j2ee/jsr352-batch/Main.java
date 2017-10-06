package com.khallware.batch;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;

/**
 * WARNING: this is a non-functioning example (couldn't get job to rise
 *          above the starting status)
 *
 * Proof of Concept (PoC) to utilize the java batch api.  It requires a
 * container that implements JSR-000352 and CDI such as: jberet, batchee,
 * jboss, spring, jetty, websphere, jbatch, glassfish, wildfly or tomcat.
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=https://mvnrepository.com/ \
 *     -Dartifact=org.glassfish.main.extras:glassfish-embedded-all:4.1.2
 * GLASSFISH_JARFILE=$POC_MAVEN_REPO/org/glassfish/main/extras/glassfish-embedded-all/4.1.2/glassfish-embedded-all-4.1.2.jar
 *
 * javac -cp $GLASSFISH_JARFILE -d /tmp *.java
 * echo 'Main-Class: com.khallware.batch.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/batch-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * mkdir -p /tmp/META-INF/batch-jobs
 * cp batchfile.xml /tmp/META-INF/batch-jobs/
 * cd /tmp
 * java -cp $GLASSFISH_JARFILE:/tmp/:/tmp/batch-poc.jar \
 *      com.khallware.batch.Main batchfile
 * rm -rf /tmp/batch-poc.jar $POC_MAVEN_REPO /tmp/META-INF
 */
public class Main
{
	private static org.glassfish.embeddable.GlassFish jsr352 = null;

	static {
		try {
			jsr352 = org.glassfish.embeddable.GlassFishRuntime
				.bootstrap().newGlassFish();
			jsr352.start();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	};

	public static void main(String... args) throws Exception
	{
		JobOperator oper = BatchRuntime.getJobOperator();
		long id = oper.start(args[0], System.getProperties());
		BatchStatus status = null;

		LOOP: while (true) {
			status = oper.getJobExecution(id).getBatchStatus();

			switch (status) {
			case ABANDONED:
			case FAILED:
			case STOPPED:
				System.out.println("failed: "+status);
				System.exit(1);
				break;
			case COMPLETED:
				System.out.println("success...");
				break LOOP;
			case STARTED:
			case STARTING:
			case STOPPING:
				Thread.sleep(1000);
				break;
			}
			System.out.printf("job %s\n",(""+status).toLowerCase());
		}
	}
}
