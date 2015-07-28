import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLContext;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.net.Socket;

/**
 * Proof of Concept (PoC) to serve an echo site that utilizes two-way
 * authentication of public key infrastructure (PKI).
 *
 * cp Server.java /tmp
 * javac /tmp/Server.java
 * keytool -keystore /tmp/certs.jks -genkey -alias khallware \
 *    -dname 'CN=khallware.com,L=Franklin,ST=TN,C=US'
 * keytool -list -keystore /tmp/certs.jks
 * java -cp /tmp Server 9999 [my.jks] [my.jks-passwd]
 * openssl s_client -connect localhost:9999 -tls1
 */
public class Server
{
	public static final String JKS_FNAME = "certs.jks";
	public static final String JKS_PASSWD = "changeit";

	private static void startServer(SSLServerSocket socket) throws Exception
	{
		Socket client = null;
		String content = null;
		PrintWriter writer = null;
		BufferedReader reader = null;

		socket.setNeedClientAuth(true);
		client = socket.accept();
		writer = new PrintWriter(new BufferedWriter(
			new OutputStreamWriter(client.getOutputStream())));
		reader = new BufferedReader(
			new InputStreamReader(client.getInputStream()));

		while ((content = reader.readLine()) != null) {
			System.out.printf("client says: \"%s\"\n", content);
			writer.println(content);
			writer.flush();
		}
		client.close();
		socket.close();
	}

	/**
	 * Programmatically create a SSLServerSocketFactory.
	 */
	private static SSLServerSocketFactory makeFactory(String fname,
			String passwd) throws Exception
	{
		SSLServerSocketFactory retval = null;
		SSLContext ctxt = SSLContext.getInstance("TLS");
		KeyStore keystore = KeyStore.getInstance("JKS");
		KeyManagerFactory factory = KeyManagerFactory.getInstance(
			"SunX509");
		keystore.load(new FileInputStream(fname), passwd.toCharArray());
		factory.init(keystore, passwd.toCharArray());
		ctxt.init(factory.getKeyManagers(), null, null);
		retval = ctxt.getServerSocketFactory();
		return(retval);
	}

	/**
	 * Prime the system properties to create a SSLServerSocketFactory.
	 * Override this with: javax.net.ssl.keyStore and
	 * javax.net.ssl.keyStorePassword.
	 */
	private static SSLServerSocketFactory getFactory(String keystore,
			String passwd) throws Exception
	{
		SSLServerSocketFactory retval = null;
		String prop = "javax.net.ssl.keyStore";

		if (!System.getProperties().containsKey(prop)) {
			System.setProperty(prop, keystore);
		}
		prop = "javax.net.ssl.keyStorePassword";

		if (!System.getProperties().containsKey(prop)) {
			System.setProperty(prop, passwd);
		}
		retval = (SSLServerSocketFactory)
			SSLServerSocketFactory.getDefault();
		return(retval);
	}

	public static void main(String... args) throws Exception
	{
		int port = (args.length >= 1) ? Integer.parseInt(args[0]) : 443;
		String fname = (args.length >= 2) ? args[1] : JKS_FNAME;
		String passwd = (args.length >= 3) ? args[2] : JKS_PASSWD;
		SSLServerSocketFactory factory = getFactory(fname, passwd);
		// SSLServerSocketFactory factory = makeFactory(fname, passwd);
		startServer((SSLServerSocket)factory.createServerSocket(port));
	}
}
