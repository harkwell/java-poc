import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;

/**
 * This PoC shows how to show information for the garbage collectors.
 *
 * cp Main.java /tmp
 * javac /tmp/Main.java 
 * java -cp /tmp/ Main
 * java -cp /tmp/ -XX:+UseConcMarkSweepGC Main
 * java -cp /tmp/ -XX:+UseG1GC Main
 */
public class Main
{
	public static void main(String... args) throws Exception
	{
		for (GarbageCollectorMXBean bean : ManagementFactory
				.getGarbageCollectorMXBeans()) {
			System.out.println("Name: "+bean.getName());
			System.out.println("Num Collections: "
				+bean.getCollectionCount());
			System.out.println("Collection time: "
				+bean.getCollectionTime()+"ms");
			System.out.println("Pool names: ");

			for (String name : bean.getMemoryPoolNames()) {
				System.out.println("\t"+name);
			}
			System.out.println();
		}
	}
}
