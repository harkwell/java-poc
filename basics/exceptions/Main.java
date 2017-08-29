package com.khallware.poc.exceptions;

/**
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.poc.exceptions.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/exceptions-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -jar /tmp/exceptions-poc.jar
 * rm /tmp/exceptions-poc.jar
 */
public class Main
{
	// Object -> Throwable -> Error (very serious problems)
	// Object -> Throwable -> Exception -> MainException (checked)
	// Object -> Throwable -> Exception -> RuntimeException (unchecked)
	/**
	 * MainException - exceptions are API side-effects
	 *
	 *    Exceptions are necessary to express unexpected conditions that
	 *    interrupt the natural flow of execution.  This is how they
	 *    are declared:
	 */
	public static class MainException extends Exception
	{
		private static final long serialVersionUID = 0x0001L;

		public MainException() {}

		public MainException(String message)
		{
			super(message);
		}

		public MainException(String message, Throwable cause)
		{
			super(message, cause);
		}

		public MainException(Throwable cause)
		{
			super(cause);
		}
	}

	// to use assert, call java -ea or java -enableassertions
	public static void assertions()
	{
		boolean flag1 = true;
		boolean flag2 = true;

		// this is a java assertion, it throws java.lang.AssertionError
		// Object -> Throwable -> Error -> AssertionError
		assert flag1 : flag2;
	}

	public static void checkedExceptions()
	{
		// this is a custom, checked exception
		Exception e = new MainException("must be handled");

		// turn checked exception into an unchecked exception
		// do this when a client cannot do anything about the error
		throw new RuntimeException("not required to be handled", e);
	}

	// best practice is to throw a specific exception
	public static void uncheckedExceptions() throws MainException
	{
		// this is an unchecked exception
		Exception e = new RuntimeException("unchecked exception");

		// turn an unchecked exception into a checked exception
		// do this when a client can recover from the error
		throw new MainException("checked exception", e);
	}

	public static void stringOperations()
	{
		String s1 = "college football";
		String s2 = String.format("%s - %d", "another", 1);
		String s3 = "col1:col2:col3:col4";
		int idx = (s1.contains("foo")) ? s1.indexOf("ball") : -1;
		System.out.printf("\"%s\" is at index %d\n",
			s1.substring((idx - "foo".intern().length() -1), idx-1),
			s1.indexOf("foo"));
		System.out.printf("\"%s\"%s\n",
			s2, (s2.endsWith("1")) ? " ends with 1" : "");
		System.out.printf("match ignoring case? %s\n",
			("FOO".equalsIgnoreCase("foo")) ? "yes" : "no");

		for (String token : s3.split(":")) {
			// java.util.StringTokenizer is deprecated
			System.out.print(token+" ");
		}
		System.out.println();
		System.out.printf("clean up spacing: \"%s\"\n",
			"foo bar    ".trim());
		StringBuilder sb = new StringBuilder();
		sb.append("string1");
		sb.append("-").append("string2");
		System.out.println(""+sb);
	}

	// strings are immutable character strings implemented as objects
	public static void stringProperties()
	{
		String s1 = "foo"; // this is stored in the string pool in heap
		String s2 = "foo"; // s1 == s2
		String s3 = new String("foo"); // s3 not stored in string pool
		// s1 != s3
		System.out.printf("\"%s\" and ref to \"%s\" are%s the same\n",
			s1, s3, (s1 == s3) ? "" : " not");
		System.out.printf("\"%s\" and \"%s\" are%s the same\n",
			s1, s3, (s1.equals(s3)) ? "" : " not");
	}

	// where possible, do not throw generic exceptions like Throwable
	// may explicitly throw RuntimeException, but not required to
	public static void main(String... args) throws IllegalStateException
	{
		Main.assertions();
		Main.stringProperties();
		Main.stringOperations();

		// use try, catch, finally blocks to handle exceptions
		try {
			Main.uncheckedExceptions();
		}
		catch (Exception e) {
			Main.checkedExceptions();
			// * preserve encapsulation where possible
			//   an API user should not be required to import deps
			// * best to handle the exception in some way,
			//   do not just silently ignore them
			// * realize that unchecked exceptions are also caught
			//   when catching generically like this
			// * when logging exceptions, do it only once
			throw new IllegalStateException(e);
		}
		// finally blocks are always called before throwing
		finally {
			// * cleanup open connections and such
			System.out.println("exceptions:");
		}
	}
}
