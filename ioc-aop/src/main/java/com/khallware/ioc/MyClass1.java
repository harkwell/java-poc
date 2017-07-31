package com.khallware.ioc;

public class MyClass1
{
	public void method1()
	{
		System.out.println("this is method1 from class 1");
	}

	@MyAnnotation
	public void method2()
	{
		System.out.println("this is method2 from class 1");
	}
}
