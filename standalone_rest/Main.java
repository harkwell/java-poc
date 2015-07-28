import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import java.net.URI;

/**
 * cp Main.java /tmp
 * export CLASSPATH=$CLASSPATH:/tmp:/home/khall/.m2/repository/org/glassfish/main/extras/glassfish-embedded-web/4.0-b72/glassfish-embedded-web-4.0-b72.jar
 * done
 * javac /tmp/Main.java
 * java -cp /tmp Main http://localhost:8080/v1/apis
 * curl -i -X GET http://localhost:8080/v1/apis/foo
 */
public class Main
{
	public class Ctrl
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
		URI uri = new URI(args[0]);
		HttpServer svr = GrizzlyHttpServerFactory.createHttpServer(uri);
		svr.start();
		System.out.println("press enter to stop...");
		System.in.read();
		svr.shutdownNow();
	}
}
