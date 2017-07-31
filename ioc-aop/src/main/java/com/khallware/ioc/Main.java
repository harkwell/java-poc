package com.khallware.ioc;

public class Main
{
	public static void main(String... args) throws Exception
	{
		MyClass1 obj1 = new MyClass1();
		MyClass2 obj2 = new MyClass2();
		obj1.method1();
		obj2.method1();
		obj1.method2();
		obj2.method2();
	}
}
