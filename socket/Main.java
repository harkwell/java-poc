import java.net.Socket;

/**
 * Proof of Concept (Poc) to check availability of a port on a server.
 * cp Main.java /tmp
 * javac /tmp/Main.java
 * java -cp /tmp Main localhost 80
 */
public class Main
{
	public static void main(String... args) throws Exception
	{
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		Socket socket = new Socket(host, port);
		System.out.printf("The socket is %sconnected.",
			(socket.isConnected()) ? "" : "not ");
		socket.close();
	}
}
