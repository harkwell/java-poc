package com.khallware.ioc;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;

@Aspect
public class MyAspect
{
	@Pointcut("@annotation(MyAnnotation)")
	public void mypointcut1() {}

	@Pointcut("execution(* *(..))")
	public void mypointcut2() {}

	@Before("mypointcut1() && mypointcut2()")
	public void before(JoinPoint jp)
	{
		System.out.println("MyAspect: @Before: "+jp.toLongString());
	}

	@After("mypointcut1() && mypointcut2()")
	public void after(JoinPoint jp)
	{
		System.out.println("MyAspect: @After: "+jp.toLongString());
	}
}
