package com.khallware.rsa;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.Arrays;

/**
 *
 * javac -d /tmp algorithms/rsa/Main.java
 * echo 'Main-Class: com.khallware.rsa.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/rsa-poc.jar -C /tmp com
 *
 * java -jar /tmp/rsa-poc.jar encrypt "my message to be encrypted"
 * rm -rf /tmp/com /tmp/manifest /tmp/rsa-poc.jar
 *
 */
public class Main
{
	public static final int SIZE = 256;
	/**
	 *  (m^e)^d = m (mod n)
	 *  given e, n and/or m, it's difficult to find d
	 */
	private static BigInteger encrypt(BigInteger content, BigInteger pubkey,
			BigInteger modulus)
	{
		return(content.modPow(pubkey, modulus));
	}

	private static BigInteger decrypt(BigInteger content,
			BigInteger privkey, BigInteger modulus)
	{
		return(content.modPow(privkey, modulus));
	}

	private static BigInteger[] genPubPrivMod()
	{
		BigInteger[] retval = new BigInteger[3];
		SecureRandom random = new SecureRandom();
		BigInteger p1 = BigInteger.probablePrime(SIZE/2, random);
		BigInteger p2 = BigInteger.probablePrime(SIZE/2, random);
		BigInteger tmp = (p1.subtract(BigInteger.ONE))
			.multiply(p2.subtract(BigInteger.ONE));
		retval[0] = BigInteger.valueOf(65537);
		retval[1] = retval[0].modInverse(tmp);
		retval[2] = p1.multiply(p2);
		return(retval);
	}

	public static void main(String... args) throws Exception
	{
		BigInteger[] rslt = (args.length >= 5)
			? new BigInteger[] {
				new BigInteger(args[2]),
				new BigInteger(args[3]),
				new BigInteger(args[4]) }
			: Main.genPubPrivMod();
		BigInteger pubkey = rslt[0];
		BigInteger privkey = rslt[1];
		BigInteger modulus = rslt[2];
		BigInteger encrypt = null;
		BigInteger message = null;

		switch (args[0]) {
		case "decrypt":
			encrypt = new BigInteger(args[1]);
			message = Main.decrypt(encrypt, privkey, modulus);
			System.out.printf("MESSAGE:\n%s\n",
				new String(message.toByteArray()));
			break;
		case "encrypt":
			message = new BigInteger(args[1].getBytes());
			encrypt = Main.encrypt(message, pubkey, modulus);
			System.out.printf("PUB/PRIV/MOD:\n%s\n",
				Arrays.toString(rslt));
			System.out.printf("ENCRYPTED:\n%s\n", encrypt);
			break;
		default:
			System.out.println(String.format(
				"java -jar rsa-poc.jar %s%s",
				"<\"encrypt\"|\"decrypt\"> <message> ",
				"[public private modulus]"));
			break;
		}
	}
}
