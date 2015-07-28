import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Proof of Concept (Poc) to implement an echo server in java.
 * cp Main.java /tmp
 * javac /tmp/Main.java
 * java -cp /tmp Main 7777 2>/dev/null
 * echo hello world |nc localhost 7777
 */
public class Main
{
	public static final int PORT = 7;

	public static void main(String... args) throws Exception
	{
		int port = (args.length > 0) ? Integer.parseInt(args[0]) : PORT;
		ServerSocket socket = new ServerSocket(port);
		System.err.println("Started server on port "+port);

		while (true) {
			String input = null;
			Socket client = socket.accept();
			System.err.println("Accepted connection from client");
			BufferedReader reader = new BufferedReader(
				new InputStreamReader(client.getInputStream()));

			while ((input = reader.readLine()) != null) {
				System.out.println(input);
				client.getOutputStream().write(
					(input+"\n").getBytes());
			}
			System.err.println("Closing connection with client");
			reader.close();
			client.close();
		}
	}
}
