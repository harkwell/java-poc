/**
 * cp Main.java /tmp
 * javac /tmp/Main.java
 * java -cp /tmp Main
 *
 */
public class Main
{
	public static void conditionals()
	{
		String data[] = new String[] {
			"if statement...",
			"if (true) {\n\t\tbreak;\n\t}",
			"if (false) {\n\t\tbreak;\n\t}\n\t"
				+"else {\n\t\tbreak;\n\t}",
			"if (false) {\n\t\tbreak;\n\t}\n\t"
				+"else if (false) {\n\t\tbreak;\n\t}\n\t"
				+"else {\n\t\tbreak;\n\t}\n",
			"case statement...",
			"switch (5) {\n\t"
				+"case 1:\n\t\tbreak;\n\t"
				+"default:\n\t\tbreak;\n\t"
				+"}\n",
			"ternary operator...",
			"i = (false) ? 1 : 2; // i = 2",
		};
		System.out.println("java conditionals:");

		for (String row : data) {
			System.out.printf("\t%s\n", row);
		}
	}

	public static void looping()
	{
		String data[] = new String[] {
			"while loop...",
			"while (true) { break; }\n",
			"do loop...",
			"do { break; } while( true);\n",
			"traditional for loop...",
			"for (int i=0; i<5; i++) { continue; }\n",
			"enhanced for loop...",
			"for (String s : Arrays.asList(\"foo\", \"bar\")) {\n\t"
				+"\tSystem.out.println(s);\n\t}\n",
			"labeled break or continue in loops...",
			"MYLABEL: while (true) { break MYLABEL; }\n",
		};
		System.out.println("java looping constructs:");

		for (String row : data) {
			System.out.printf("\t%s\n", row);
		}
	}

	public static String returnExample()
	{
		return("method values are sent with the return() statement");
	}

	public static void main(String... args) throws Exception
	{
		Main.conditionals();
		Main.looping();
		System.out.printf("\t%s\n", Main.returnExample());
	}
}
