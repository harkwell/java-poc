import com.khallware.ejb.MyBeans.MyBean;
import java.util.Properties;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

/**
 * Proof of Concept (PoC) to manipulate enterprise java beans (ejb).
 * See README.md for details.
 */
public class Main
{
	private static final Properties props = new Properties();
	private static final String CTXT_FACTORY =
		"org.apache.openejb.client.RemoteInitialContextFactory";
	private static final String DEF_EJB_URL = "ejbd://127.0.0.1:4201";

	public static void main(String... args) throws Exception
	{
		MyBean bean1 = null; // must be interface type, not class
		MyBean bean2 = null;
		Object ref = null;
		Context ctxt = null;
		props.put(Context.INITIAL_CONTEXT_FACTORY, CTXT_FACTORY);
		props.put(Context.PROVIDER_URL,
			(args.length > 1) ? args[0] : DEF_EJB_URL);
		ctxt = new javax.naming.InitialContext(props);
		ref = ctxt.lookup("Bean1Remote");
		bean1 = (MyBean)PortableRemoteObject.narrow(ref, MyBean.class);
		ref = ctxt.lookup("Bean2Remote");
		bean2 = (MyBean)PortableRemoteObject.narrow(ref, MyBean.class);
		System.out.println(bean1.doStuff());
		System.out.println(bean2.doStuff());
	}
}
