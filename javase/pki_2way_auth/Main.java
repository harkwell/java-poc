import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import javax.security.cert.CertificateNotYetValidException;
import javax.security.cert.CertificateExpiredException;
import javax.security.cert.X509Certificate;
import java.security.cert.Certificate;
import java.security.PublicKey;
import java.security.Principal;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.net.Socket;

/**
 * Proof of Concept (PoC) to connect to a site that utilizes two-way
 * authentication of public key infrastructure (PKI).
 * javac -d /tmp/ Main.java
 * java -cp /tmp Main www.google.com
 * java -cp /tmp Main ss.tvc.tsa.dhs.gov 443
 */
public class Main
{
	private static void doProtos(SSLSocket socket) throws Exception
	{
		for (String proto : socket.getEnabledProtocols()) {
			System.out.printf("PROTO: %s\n", proto);
		}
	}

	private static void doParms(SSLParameters parms) throws Exception
	{
		System.out.printf("PARM: endpoint ident algorithm: %s\n",
			parms.getEndpointIdentificationAlgorithm());
		System.out.printf("PARM: client auth (need): %s\n",
			""+parms.getNeedClientAuth());
		System.out.printf("PARM: client auth (want): %s\n",
			""+parms.getWantClientAuth());

		for (String proto : parms.getProtocols()) {
			System.out.printf("PARM: protocol: %s\n", proto);
		}
	}

	private static void doSettings(SSLSocket socket) throws Exception
	{
		System.out.printf("SETTING: use client mode: %s\n",
			""+socket.getUseClientMode());
	}

	private static void dump(Principal principal) throws Exception
	{
		System.out.printf("name: %s\t%s\n", principal.getName(),
			""+principal);
	}

	private static void dump(PublicKey publicKey) throws Exception
	{
		System.out.printf("algorithm: \"%s\"\tformat: \"%s\"\n%s\n",
			publicKey.getAlgorithm(), publicKey.getFormat(),
			new String(publicKey.getEncoded()));
	}

	private static void dump(Certificate certificate) throws Exception
	{
		System.out.printf("type: %s\t%s\n", certificate.getType(),
			""+certificate);
		System.out.printf("PEM: %s\n",
			new String(certificate.getEncoded()));
	}

	private static void dumpX509(X509Certificate x509) throws Exception
	{
		System.out.printf("serial: %s\tversion: %s\n",
			""+x509.getSerialNumber(), ""+x509.getVersion());
		System.out.printf("signature algorithm: %s\n",
			x509.getSigAlgName());
		System.out.printf("signature OID: %s\n", x509.getSigAlgOID());
		System.out.printf("signature parms: %s\n",
			(x509.getSigAlgParams() != null)
				? new String(x509.getSigAlgParams())
				: "no sig alg params");
		try {
			x509.checkValidity();
		}
		catch (CertificateExpiredException e) {
			System.out.printf("ERROR: expired: %s\n", ""+e);
		}
		catch (CertificateNotYetValidException e) {
			System.out.printf("ERROR: in the future: %s\n", ""+e);
		}
		System.out.printf("issuer\n\t");
		dump(x509.getIssuerDN());
		System.out.printf("subject\n\t");
		dump(x509.getSubjectDN());
		System.out.printf("not after: %s\n", x509.getNotAfter());
		System.out.printf("not before: %s\n", x509.getNotBefore());
		System.out.printf("PEM: %s\n", new String(x509.getEncoded()));
	}

	private static void doSession(SSLSession session) throws Exception
	{
		System.out.printf("SESSION: id: %s\n",
			new String(session.getId()));
		System.out.printf("SESSION: cipher suite: %s\n",
			session.getCipherSuite());
		System.out.printf("SESSION: protocol: %s\n",
			session.getProtocol());

		for (String name : session.getValueNames()) {
			System.out.printf("SESSION: \"%s\" = \"%s\"\n",
				name, ""+session.getValue(name));
		}
		System.out.printf("SESSION: peer certificates\n");

		for (Certificate c : session.getPeerCertificates()) {
			System.out.printf("\t");
			dump(c);
		}
		System.out.printf("SESSION: peer\n\t");
		dump(session.getPeerPrincipal());
		System.out.printf("SESSION: local certificates\n");

		if (session.getLocalCertificates() == null) {
			System.out.printf("\tNo local certificates sent\n");
		}
		else {
			for (Certificate c : session.getLocalCertificates()) {
				System.out.printf("\t");
				dump(c);
			}
		}
		System.out.printf("SESSION: peer certificate chain\n");

		for (X509Certificate c : session.getPeerCertificateChain()){
			System.out.printf("\t");
			dumpX509(c);
		}
	}

	private static void doDump(SSLSocket socket) throws Exception
	{
		BufferedReader reader = new BufferedReader(
			new InputStreamReader(socket.getInputStream()));

		while (reader.ready()) {
			System.out.printf("%s\n", reader.readLine());
		}
		reader.close();
	}

	public static void main(String... args) throws Exception
	{
		int port = (args.length >= 2) ? Integer.parseInt(args[1]) : 443;
		SSLSocket socket = (SSLSocket)
			SSLSocketFactory.getDefault().createSocket(
				args[0], port);
		socket.startHandshake();
		doProtos(socket);
		doParms(socket.getSSLParameters());
		doSettings(socket);
		doSession(socket.getSession());
		doDump(socket);
	}
}
