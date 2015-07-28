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
 * mkdir -p /tmp/apis && cp Main.java /tmp/apis
 * mvn deps: com.sun.jersey:jersey-server, com.sun.jersey:jersey-grizzly2
 * export CLASSPATH=/tmp:/home/khall/.m2/repository/com/sun/jersey/jersey-server/1.16/jersey-server-1.16.jar:/home/khall/.m2/repository/com/sun/jersey/jersey-grizzly2/1.16/jersey-grizzly2-1.16.jar:/home/khall/.m2/repository/org/glassfish/grizzly/grizzly-http-server/2.2.16/grizzly-http-server-2.2.16.jar:/home/khall/.m2/repository/com/sun/jersey/jersey-core/1.16/jersey-core-1.16.jar:/home/khall/.m2/repository/asm/asm/3.1/asm-3.1.jar:/home/khall/.m2/repository/org/glassfish/grizzly/grizzly-framework/2.2.16/grizzly-framework-2.2.16.jar:/home/khall/.m2/repository/org/glassfish/grizzly/grizzly-http/2.2.16/grizzly-http-2.2.16.jar
 * javac /tmp/apis/Main.java
 * java apis.Main
 * curl -i -X GET http://localhost:8080/v1/apis/foo
 */
public class Main
{
	public static final String DEF_URL = "http://localhost:8080";

	@Path("/v1/apis")
	public staic class Ctrl
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
