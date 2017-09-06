package com.khallware.poc.jsonp;

/**
 * A Proof of Concept for java jsonp (json processor).  It does not leverage
 * jackson2 libraries because it's to demonstrate native j2ee code.
 *
 * WARNING:  This is a hugely inaccurate model of the eye and should
 *           NOT be used in any kind of serious study of it.  It is only
 *           used here to demonstrate some capabilities of jsonp.
 *
 * BUILD:
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mkdir -p $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=https://mvnrepository.com/ \
 *     -Dartifact=org.glassfish:javax.json:1.1
 * JSONP_JAR=$(find $POC_MAVEN_REPO -name \*json\*jar)
 * javac -d /tmp -cp $JSONP_JAR *.java
 * echo 'Main-Class: com.khallware.poc.jsonp.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/jsonp-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * 
 * cat eye.json |java -cp $JSONP_JAR:/tmp/jsonp-poc.jar \
 *                     com.khallware.poc.jsonp.Main
 * rm -rf /tmp/jsonp-poc.jar $POC_MAVEN_REPO
 *
 */

import java.io.StringReader;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonObject;
import javax.json.Json;

public class Main
{
	public static Eye makeEye(JsonObject obj)
	{
		JsonObject[] joa = new JsonObject[9];
		joa[0] = obj.getJsonObject("cornea");
		joa[1] = joa[0].getJsonObject("pupil");
		joa[2] = joa[0].getJsonObject("iris");
		joa[3] = obj.getJsonObject("sclera");
		joa[4] = joa[3].getJsonObject("choroid");
		joa[5] = joa[4].getJsonObject("retina");
		joa[6] = joa[5].getJsonObject("lens");
		joa[7] = joa[5].getJsonObject("vitreous");
		joa[8] = joa[7].getJsonObject("macula");
		return(new Eye.Factory()
			.power(obj.getJsonNumber("opticPower").doubleValue())
			.iris(joa[2].getJsonNumber("stiffness").doubleValue())
			.pupil(joa[1].getJsonNumber("size").doubleValue())
			.sclera(joa[3].getJsonNumber("density").doubleValue())
			.retina(new Object(), new Object())
			.choroid(joa[4].getJsonNumber("elasticity")
				.doubleValue())
			.lens(joa[6].getJsonNumber("thickness").doubleValue())
			.vitreous(joa[7].getJsonNumber("volume").doubleValue())
			.macula(joa[8].getJsonNumber("reninReleaseRate")
				.doubleValue())
			.build());
	}

	public static void main(String... args) throws Exception
	{
		Eye.Factory factory = new Eye.Factory();
		JsonReader jsonReader = Json.createReader(System.in);
		JsonWriter jsonWriter = Json.createWriter(System.out);
		JsonObject jsonObject = null;

		System.out.println("reading json, producing JsonObject");
		jsonObject = jsonReader.readObject();
		jsonReader.close();

		System.out.println("mapping JsonObject to POJO");
		System.out.printf("%s\n", makeEye(jsonObject).toJsonString());

		System.out.println("converting POJO to JsonObject");
		jsonReader = Json.createReader(new StringReader(
			factory.pupil(1.5d).lens(4.1d).build().toJsonString()));
		jsonObject = jsonReader.readObject();
		jsonReader.close();

		System.out.println("given JsonObject, write json string");
		jsonWriter.writeObject(jsonObject);

		System.out.println("\ngiven JsonObject, print json string");
		System.out.println(""+jsonObject);
	}
}
