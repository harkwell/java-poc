package com.khallware.javamail;

import java.util.Properties;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.Session;

/**
 * Proof of Concept (PoC) to utilize the java mail api.
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=url \
 *     -Dartifact=javax.mail:mail:1.4.7
 * MAIL_JARFILE=$POC_MAVEN_REPO/javax/mail/mail/1.4.7/mail-1.4.7.jar
 *
 * javac -cp $MAIL_JARFILE -d /tmp Main.java
 * echo 'Main-Class: com.khallware.javamail.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/javamail-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -cp $MAIL_JARFILE:/tmp/javamail-poc.jar com.khallware.javamail.Main \
 *      myuser mypass myuser@domain.local mybuddy@hisdomain.local \
 *      "this is the subject" "this is the body"
 * rm -rf /tmp/javamail-poc.jar $POC_MAVEN_REPO
 */
public class Main extends Authenticator
{
	private String user = null;
	private String pass = null;
	private String from = null;

	static {
		String[] data = new String[] {
			"mail.smtp.starttls.enable:true",
			"mail.smtp.host:smtp.gmail.com",
			"mail.smtp.auth:true",
			"mail.smtp.port:587"
		};
		Properties props = System.getProperties();

		for (String row : data) {
			String[] kv = row.split(":");

			if (!props.containsKey(kv[0])) {
				props.put(kv[0], kv[1]);
			}
		}
	};

	public Main(String user, String pass, String from)
	{
		this.user = user;
		this.pass = pass;
		this.from = from;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return(new PasswordAuthentication(user, pass));
	}

	public void send(String to, String subject, String body)
	{
		try {
			Properties props = System.getProperties();
			Message msg = new MimeMessage(
				Session.getInstance(props, this));
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(
				Message.RecipientType.TO,
				InternetAddress.parse(to));
			msg.setSubject(subject);
			msg.setText(body);
			Transport.send(msg);
			System.out.println("Message sent.");
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String... args) throws Exception
	{
		Main instance = new Main(args[0], args[1], args[2]);
		instance.send(args[3], args[4], args[5]);
	}
}
