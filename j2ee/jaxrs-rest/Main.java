package apis;

import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import java.util.Set;
import java.util.HashSet;

/**
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * LIST="
 *    com.sun.jersey:jersey-server:1.19.3
 *    com.sun.jersey:jersey-grizzly2:1.19.3
 *    org.glassfish.grizzly:grizzly-http-server:2.3.28
 *    org.glassfish.grizzly:grizzly-framework:2.3.28
 *    org.glassfish.grizzly:grizzly-http:2.3.28
 *    com.sun.jersey:jersey-core:1.19.3
 *    javax.ws.rs:javax.ws.rs-api:2.0.1
 *    asm:asm:3.3.1
 * "
 * for x in $LIST; do
 *    mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *       org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *       -DrepoUrl=https://mvnrepository.com/ \
 *       -Dartifact=$x
 * done
 * export CLASSPATH=/tmp:$POC_MAVEN_REPO/com/sun/jersey/jersey-server/1.19.3/jersey-server-1.19.3.jar:$POC_MAVEN_REPO/com/sun/jersey/jersey-grizzly2/1.19.3/jersey-grizzly2-1.19.3.jar:$POC_MAVEN_REPO/org/glassfish/grizzly/grizzly-http-server/2.3.28/grizzly-http-server-2.3.28.jar:$POC_MAVEN_REPO/com/sun/jersey/jersey-core/1.19.3/jersey-core-1.19.3.jar:$POC_MAVEN_REPO/asm/asm/3.3.1/asm-3.3.1.jar:$POC_MAVEN_REPO/org/glassfish/grizzly/grizzly-framework/2.3.28/grizzly-framework-2.3.28.jar:$POC_MAVEN_REPO/org/glassfish/grizzly/grizzly-http/2.3.28/grizzly-http-2.3.28.jar:$POC_MAVEN_REPO/javax/ws/rs/javax.ws.rs-api/2.0.1/javax.ws.rs-api-2.0.1.jar
 * javac -d /tmp Main.java
 * echo 'Main-Class: apis.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/jaxrs-poc.jar -C /tmp apis
 * rm -rf /tmp/apis /tmp/manifest
 * java -cp $CLASSPATH:/tmp/jaxrs-poc.jar apis.Main
 * curl -i -X GET http://localhost:8080/v1/apis/foo
 */
public class Main
{
	public static final String DEF_URL = "http://localhost:8080";

	@Path("/v1/apis")
	public static class Ctrl
	{
		@GET
		@Path("/foo")
		public String get()
		{
			return("blah");
		}
	}

	public static void main(String... args) throws Exception
	{
		String url = (args.length > 0) ? args[0] : DEF_URL;
		ResourceConfig cfg = new PackagesResourceConfig("apis") {
			public Set<Class<?>> getClasses()
			{
				Set<Class<?>> retval = new HashSet<>();
				retval.add(Ctrl.class);
				return(retval);
			}
		};
		HttpServer svr = GrizzlyServerFactory.createHttpServer(url,cfg);
		svr.start();
		System.out.println("press enter to stop...");
		System.in.read();
	}
}
