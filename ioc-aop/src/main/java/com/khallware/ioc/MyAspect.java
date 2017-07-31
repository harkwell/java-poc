package com.khallware.ioc;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;

@Aspect
public class MyAspect
{
	@Pointcut("execution(* com.khallware.ioc.Main.main(..))")
	public void mypointcut() {}

	@Before("mypointcut()")
	public void before(JoinPoint jp)
	{
		System.out.println("before mypointcut(): "+jp.toLongString());
	}

	@After("mypointcut()")
	public void after(JoinPoint jp)
	{
		System.out.println("after mypointcut(): "+jp.toLongString());
	}
}
