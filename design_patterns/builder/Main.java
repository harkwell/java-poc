package com.khallware.patterns.builder;

/**
 * The builder pattern separates the construction of a pojo from its
 * representation.
 *
 * Build:
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.patterns.builder.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/builder-pattern.jar -C /tmp com/
 * java -jar /tmp/builder-pattern.jar
 * rm -rf /tmp/com /tmp/manifest /tmp/builder-pattern.jar
 */
public class Main
{
	public static class MyPojo
	{
		private String attrib1 = null;
		private String attrib2 = null;
		private String attrib3 = null;

		@Override
		public String toString()
		{
			StringBuilder retval = new StringBuilder();
			retval.append(this.getClass().getSimpleName());
			retval.append(": attrib1=");
			retval.append(attrib1);
			retval.append(", attrib2=");
			retval.append(attrib2);
			retval.append(", attrib3=");
			retval.append(attrib3);
			return(""+retval);
		}
	}

	public static class MyPojoBuilder
	{
		private MyPojo pojo = null;

		public MyPojoBuilder()
		{
			pojo = new MyPojo();
		}

		public MyPojoBuilder attrib1(String attrib1)
		{
			pojo.attrib1 = attrib1;
			return(this);
		}

		public MyPojoBuilder attrib2(String attrib2)
		{
			pojo.attrib2 = attrib2;
			return(this);
		}

		public MyPojoBuilder attrib3(String attrib3)
		{
			pojo.attrib3 = attrib3;
			return(this);
		}

		public MyPojo build()
		{
			return(pojo);
		}
	}

	public static void main(String... args) throws Exception
	{
		MyPojo pojo1 = new MyPojoBuilder()
			.attrib1("foo")
			.attrib2("bar")
			.attrib3("baz")
			.build();
		MyPojo pojo2 = new MyPojoBuilder()
			.attrib1("buz")
			.attrib2("boz")
			.attrib3("doh")
			.build();
		System.out.printf("POJO1:\n%s\nPOJO2:\n%s\n",""+pojo1,""+pojo2);
	}
}
