import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * This PoC shows how to use the java to read input from the user.
 *
 * javac -d /tmp/ ReadStdin2.java 
 * cat /etc/passwd |java -cp /tmp/ ReadStdin2
 * java -cp /tmp/ ReadStdin2 # type some stuff... hit ctrl-d to finish
 *
 */
public class ReadStdin2
{
	public static void main(String... args) throws Exception
	{
		StringBuilder retval = new StringBuilder();
		BufferedReader reader = new BufferedReader(
			new InputStreamReader(System.in));

		for (String line; (line=reader.readLine()) != null;) {
			retval.append(line);
			retval.append("\n");
		}
		System.out.printf("%s", ""+retval);
	}
}
