package com.khallware.shiro;

import com.khallware.shiro.app.Calculator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import java.util.Arrays;
import java.util.List;

public class Main
{
	public static final String INI_FILE = "/tmp/shiro.ini";
	private static enum roles { TEACHER, GRADE0, GRADE1, GRADE2 };
	private static String[] operations = { "add", "subtract", "multiply",
			"divide" };

	public static void enforceLogin(String user, String passwd)
			throws SecurityException
	{
		UsernamePasswordToken token = null;
		try {
			if (!SecurityUtils.getSubject().isAuthenticated()) {
				token = new UsernamePasswordToken(user, passwd);
				token.setRememberMe(true);
				SecurityUtils.getSubject().login(token);
			}
		}
		catch (Exception e) {
			throw new SecurityException(e);
		}
	}

	private static void dumpProfile(Subject subject)
	{
		String name = subject.getPrincipal().toString();
		System.out.printf("user \"%s\" has these roles:\n", name);

		for (Enum role : roles.values()) {
			if (subject.hasRole((""+role).toLowerCase())) {
				System.out.printf("\trole: \"%s\"\n", ""+role);
			}
		}
		System.out.printf("user \"%s\" can perform:\n", name);

		for (String operation : operations) {
			if (subject.isPermitted(operation)) {
				System.out.printf("\toperation: \"%s\"\n",
					""+operation);
			}
		}
	}

	private static List<Integer> parseArgs(String... args)
	{
		List<Integer> retval = new java.util.ArrayList<>();

		for (Object arg : Arrays.copyOfRange(args,3,args.length)) {
			retval.add(Integer.parseInt(""+arg));
		}
		return(retval);
	}

	private static void initialize()
	{
		SecurityUtils.setSecurityManager(
			new IniSecurityManagerFactory("file://"+INI_FILE)
				.getInstance());
	}

	private static void enforce(Subject subject, String operation)
	{
		if (!subject.isPermitted(operation)) {
			String name = subject.getPrincipal().toString();
			throw new RuntimeException(
				String.format("%s not permitted to perform %s",
					name, operation)
			);
		}
	}

	public static void main(String... args) throws Exception
	{
		Calculator calc = new Calculator();
		List<Integer> terms = parseArgs(args);
		Subject subject = null;
		String operation = args[2].toLowerCase();
		initialize();
		enforceLogin(args[0], args[1]);
		subject = SecurityUtils.getSubject();
		System.out.printf("user \"%s\" logged in...\n",
			subject.getPrincipal().toString());
		dumpProfile(subject);
		enforce(subject, operation);

		switch (operation) {
		case "add":
			System.out.printf("sum is: %d\n", calc.add(terms));
			break;
		case "subtract":
			System.out.printf("difference is: %d\n",
				calc.subtract(terms));
			break;
		case "multiply":
			System.out.printf("product is: %d\n",
				calc.multiply(terms));
			break;
		case "divide":
			System.out.printf("quotient is: %d\n",
				calc.divide(terms));
			break;
		default:
			throw new IllegalArgumentException("unknown operation");
		}
	}
}
