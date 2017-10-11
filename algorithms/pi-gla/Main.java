package com.khallware.pi.gauss_legendre;

import java.math.BigDecimal;

/**
 *
 * javac -d /tmp algorithms/pi-gla/Main.java
 * echo 'Main-Class: com.khallware.pi.gauss_legendre.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/approx-pi.jar -C /tmp com
 *
 * java -jar /tmp/approx-pi.jar 100
 * java -jar /tmp/approx-pi.jar 1024
 * rm -rf /tmp/com /tmp/manifest /tmp/approx-pi.jar
 *
 */
public class Main
{
	/**
	 *  pi ~= (a + b)^2 / 4t
	 */
	private static BigDecimal approx(int scale)
	{
		BigDecimal retval = null;
		BigDecimal a = BigDecimal.ONE;
		BigDecimal b = BigDecimal.ONE.divide(
			sqrt(new BigDecimal(2), scale),
			scale,
			BigDecimal.ROUND_HALF_UP);
		BigDecimal t = new BigDecimal(0.25);
		BigDecimal tmp1 = BigDecimal.ONE;
		BigDecimal tmp2 = null;

		while (!a.equals(b)) {
			tmp2 = a;
			a = a.add(b).divide(new BigDecimal(2), scale,
				BigDecimal.ROUND_HALF_UP);
			b = sqrt(b.multiply(tmp2), scale);
			t = t.subtract(tmp1.multiply(tmp2.subtract(a).multiply(
				tmp2.subtract(a))));
			tmp1 = tmp1.multiply(new BigDecimal(2));
		}
		retval = a.add(b)
			.multiply(a.add(b))
			.divide(t.multiply(new BigDecimal(4)), scale,
				BigDecimal.ROUND_HALF_UP);
		return(retval);
	}

	private static BigDecimal sqrt(BigDecimal val, int scale)
	{
		BigDecimal retval = null;
		BigDecimal approx = BigDecimal.ZERO;
		retval = new BigDecimal(Math.sqrt(val.doubleValue()));

		while (!approx.equals(retval)) {
			approx = retval;
			retval = val.divide(approx, scale,
				BigDecimal.ROUND_HALF_UP);
			retval = retval.add(approx);
			retval = retval.divide(new BigDecimal(2), scale,
				BigDecimal.ROUND_HALF_UP);
		}
		return(retval);
	}

	public static void main(String... args) throws Exception
	{
		System.out.println(approx(Integer.parseInt(args[0])));
	}
}
