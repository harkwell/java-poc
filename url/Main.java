import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.net.URL;

/**
 * Proof of Concept (Poc) to connect to a site that utilizes two-way
 * authentication of public key infrastructure (PKI).
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
