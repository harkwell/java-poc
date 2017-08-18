public class Main
{
	public native void runMe();

	static {
		System.loadLibrary("my-ext-lib"); // loads libmy-ext-lib.so
	}

	public static void main(String... args) throws Exception
	{
		new Main().runMe();
	}
}
