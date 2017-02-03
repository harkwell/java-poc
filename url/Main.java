import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;

/**
 * Proof of Concept (PoC) to connect and read from a socket expressed as a URL.
 * cp Main.java /tmp
 * javac /tmp/Main.java
 * java -cp /tmp Main https://www.google.com/
 */
public class Main
{
	public static void main(String... args) throws Exception
	{
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(
			new URL(args[0]).openConnection().getInputStream()));

		while (reader.ready()) {
			System.out.printf("%s\n", reader.readLine());
		}
		reader.close();
	}
}
