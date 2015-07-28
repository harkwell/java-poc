import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 * This POC shows how to retrieve the actual physical memory address for an
 * object.
 */
public class Main
{
	private static Unsafe unsafe;

	static {
		Field field = null;
		try {
			field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe)field.get(null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) throws Exception
	{
		Object obj = new String("foo");
		Object[] array = new Object[] { obj };
		long baseOffset = unsafe.arrayBaseOffset(Object[].class);
		long address = (unsafe.addressSize() == 4)
			? unsafe.getInt(array, baseOffset)
			: unsafe.getLong(array, baseOffset);
		System.out.println(address);
	}
}
