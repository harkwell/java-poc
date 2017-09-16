import java.io.Console;

/**
 * This PoC shows how to use the java console to read input from the user.
 *
 * javac -d /tmp/ ReadStdin.java 
 * cat /etc/passwd |java -cp /tmp/ ReadStdin # notice NPE
 * java -cp /tmp/ ReadStdin # type some stuff... hit ctrl-d to finish
 *
 */
public class ReadStdin
{
	private static String readit() throws Exception
	{
		StringBuilder retval = new StringBuilder();
		Console console = System.console();

		if (console == null) {
			throw new IllegalStateException("console is null!");
		}
		for (String line; (line = console.readLine()) != null;) {
			retval.append(line);
			retval.append("\n");
		}
		return(""+retval);
	}

	public static void main(String... args) throws Exception
	{
		System.out.printf("%s", ReadStdin.readit());
	}
}
