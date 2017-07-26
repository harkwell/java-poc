import java.lang.reflect.Method;

/**
 * cp Main.java /tmp
 * javac /tmp/Main.java
 * java -cp /tmp Main
 *
 */
public class Main
{
	public static void primitives()
	{
		String[] data = new String[] {
			"boolean (1-bit true/false) (eg \"true\")",
			"byte (8-bit signed -128 to 127) (eg 0b100000000)",
			"short (16-bit signed) (eg -32768)",
			"char (16-bit unsigned) (eg 65535)",
			"int (32-bit signed) (eg 2147483647)",
			"long (64-bit signed/unsigned) (18446744073709551616)",
			"float (32-bit decimal) (eg 0.0f)",
			"double (64-bit decimal) (eg 0.0d)",
			"string (eg \"Hello World\")"
		};
		System.out.printf("List of java primitive data types:\n");

		for (String row : data) {
			System.out.printf("\t%s\n", row);
		}
	}

	public static void arrays()
	{
		int[] array1 = new int[] { 0, 5, 3, 2, 9, 55 };
		System.out.printf("One dimensional array (eg \"%s\")\n",
			java.util.Arrays.toString(array1));

		String[][] array2 = new String[][] {
			{ "row-1-col-1", "row-1-col-2", "row-1-col-3" },
			{ "row-2-col-1", "row-2-col-2", "row-2-col-3" },
			{ "row-3-col-1", "row-3-col-2", "row-3-col-3" }
		};
		System.out.printf("Multi-dimensional array (example):\n");

		for (String[] row : array2) {
			System.out.printf("\t%s\n",
				java.util.Arrays.toString(row));
		}
		java.util.Arrays.sort(array1);
		System.out.printf("Sorted array (eg \"%s\")\n",
			java.util.Arrays.toString(array1));
		System.out.printf("Access row-2-col-3 (\"%s\")\n",array2[1][2]);
	}

	public static void enums()
	{
		for (Enum e : Character.UnicodeScript.values()) {
			if (Character.UnicodeScript.CHEROKEE.equals(e)) {
				System.out.printf("Found enum \"%s\"\n", ""+e);
			}
		}
	}

	/**
	 * This is a static nested class.  Using static allows classes external
	 * to this file access to it.  A member class is an inner class
	 * (defined within a class) that is not static and only available to
	 * this file.  A local class is one that is defined within a method
	 * (like a variable).  An anonymous class is one that cannot be
	 * instantiated directly with new().  It must be extended.
	 *
	 * Access modifiers (public,protected,private,default) behave like:
	 *
	 *            | Class | Package | Subclass | Subclass | World
	 *            |       |         |(same pkg)|(diff pkg)| 
	 * public     |   +   |    +    |    +     |     +    |   +     
	 * protected  |   +   |    +    |    +     |     +    |         
	 * <none>     |   +   |    +    |    +     |          |    
	 * private    |   +   |         |          |          |    
	 *
	 * + : accessible
	 * blank : not accessible
	 * 
	 */
	public static class Foo
	{
		private int val = 7;

		public Foo()
		{
			System.out.printf("%s constructor called\n",
				Foo.class.getName());
			this.val = 10;
		}

		public void method1()
		{
			System.out.printf("method1() called\n");
			System.out.printf("attribute val = %d\n", val);
		}
	}

	public static void classes()
	{
		new Foo().method1();
		System.out.printf("Foo.class has these methods:\n");

		for (Method method : Foo.class.getDeclaredMethods()) {
			System.out.printf("\t%s\n", ""+method);
		}
	}

	public static void operators()
	{
		String data[] = new String[] {
			"unary",
			"\ti=0; ++i; // i=1 { ++ -- + - ~ ! }",
			"postfix",
			"\tint i=0; i++; // i=1 { exp++ exp-- }",
			"arithmetic",
			"\tint i=(5 * 2, // i=10 { * / % + - }",
			"shift",
			"\tbyte b=0b1; b=(byte)(b << 2, // b=4",
			"relational",
			"\tif (4 < 2) { break; } // < > <= >=",
			"\tif (this instanceof Object) {}",
			"equality",
			"\tif (4 == 4) { break; } // == !=",
			"bitwise",
			"\tif ((0b010 & 0b111) == 0b010) {}",
			"\tif ((0b010 ^ 0b100) == 0b110) {}",
			"\tif ((0b110 | 0b100) == 0b110) {}",
			"logical",
			"\tif (true && true) {} // true",
			"\tif (false || true) {} // true",
			"ternary",
			"\tint i=0; i=(i==0) ? 1 : 2, // 1",
			"assignment",
			"\tint i=0; // = += -= *= /= %= &= ^=",
			"\t         // |= <<= >>= >>>="
		};
		System.out.println("List of java operators:");

		for (String row : data) {
			System.out.printf("\t%s\n", row);
		}
	}

	public static void precedence()
	{
		String[] data = new String[] {
			"operator with higher precedence goes first when two ",
			"share an operand.",
			"[] left-to-right",
			". left-to-right",
			"() left-to-right",
			"exp++ exp-- left-to-right",
			"++exp --exp right-to-left",
			"+ - ! ~ right-to-left",
			"(Object) right-to-left",
			"new() right-to-left",
			"* / % left-to-right",
			"+ - left-to-right",
			"+\"\" left-to-right",
			"<< >> >>> left-to-right",
			"< <= > >= left-to-right",
			"instanceof left-to-right",
			"== != left-to-right",
			"& ^ | && || left-to-right",
			"?: right-to-left",
			"= += -= *= /= %= right-to-left",
			"&= ^= |= <<= >>= >>>= right-to-left"
		};
		System.out.println("Operator Order of Precedence:");

		for (String row : data) {
			System.out.printf("\t%s\n", row);
		}
	}

	public static void casting()
	{
		String[] data = new String[] {
			"implicit cast from smaller primitive to \"wider\"",
			"\tbyte -> short -> int -> long -> float -> double",
			"explicit cast from larger primitive to \"narrower\"",
			"\tdouble -> float -> long -> int -> short -> byte",
			"\tint to short loses high order bits",
			"\tint to byte changes sign and magnitude",
			"\tfloat to int results in Integer.MAX_VALUE",
			"\tDouble.NaN to int results in 0",
			"\tlarge double to float results in Float.*INFINITY",
			"\tsmall double to float results in 0",
			"boxing conversions (primitive to Object())",
			"\tboolean -> Boolean()",
			"\tbyte -> Byte()",
			"\tshort -> Short()",
			"\tchar -> Character()",
			"\tint -> Integer()",
			"\tlong -> Long()",
			"\tfloat -> Float()",
			"\tdouble -> Double()",
			"unboxing conversions (Object() to primitive)",
			"capture conversions (subclasses)",
			"string conversions (from constructor of boxed type)",
			"assignment conversions (generics)",
			"method invocation conversions (method paramaters)",
			"casting conversions (explicit)",
			"reference type conversions"
		};
		System.out.println("Type Conversion:");

		for (String row : data) {
			System.out.printf("\t%s\n", row);
		}
	}

	public static void main(String... args) throws Exception
	{
		Main.primitives();
		Main.arrays();
		Main.enums();
		Main.classes();
		Main.operators();
		Main.precedence();
		Main.casting();
	}
}
