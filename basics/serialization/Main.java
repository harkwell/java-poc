package com.khallware.poc.serialization;

import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.poc.serialization.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/serialization-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -jar /tmp/serialization-poc.jar
 * rm /tmp/serialization-poc.jar
 */
public class Main
{
	/**
	 */
	public static class MySerializable implements Serializable
	{
		private String token1;
		private Integer token2;
		private Boolean token3;
		private transient String token4 = "baz"; // not preserved

		public MySerializable(String token1, int token2, boolean token3,
				String token4)
		{
			this.token1 = token1;
			this.token2 = token2;
			this.token3 = token3;
			this.token4 = token4;
		}

		// The following three methods may be overriden when necessary.
/*
		private void writeObject(ObjectOutputStream out)
				throws IOException
		{
		}

		private void readObject(ObjectInputStream in)
				throws IOException
		{
		}

		private void readObjectNoData() throws ObjectStreamException
		{
		}
*/
		@Override
		public String toString()
		{
			return(String.format("\"%s\" %d \"%s\" \"%s\"",
				token1, token2, token3, token4));
		}
	}

	/**
	 * A serializable object is one that can be frozen into a series
	 * of bytes, transferred and thawed into an equivalent instance.
	 * It must implement the java.io.Serializable interface.
	 */
	public static void objectSerialization() throws Exception
	{
		MySerializable item1 = new MySerializable("foo",5,true,"bar");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		new ObjectOutputStream(baos).writeObject(item1);
		// String frozen = baos.toString(); notice this doesn't work!!!
		byte[] frozen = baos.toByteArray();
		System.out.println(toPrintableString(new String(frozen)));

		ByteArrayInputStream bais = new ByteArrayInputStream(frozen);
		Object thawed = new ObjectInputStream(bais).readObject();
		MySerializable item2 = (MySerializable)thawed;
		System.out.println(thawed);
	}

	private static String toPrintableString(String input)
	{
		StringBuilder retval = new StringBuilder();

		for (char c : input.toCharArray()) {
			retval.append((c < 32 || c > 126)
				// ? String.format("\\%02x", (byte)c)
				? "^"
				: ""+c);
		}
		return(""+retval);
	}

	public static void main(String... args) throws Exception
	{
		Main.objectSerialization();
	}
}
