package com.khallware.poc.junit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestSuite.TestClass1.class,
	TestSuite.TestClass2.class
})
public class TestSuite
{
	public static class TestClass1
	{
		@Test
		public void TC1simpleTest()
		{
			System.out.println("performing TC1simpleTest()...");
			assertTrue(true);
		}
	}

	public static class TestClass2
	{
		@Test
		public void TC2simpleTest()
		{
			System.out.println("performing TC2simpleTest()...");
			assertTrue(true);
		}
	}

	public static class TestClass3
	{
		@Test
		public void TC3simpleTest()
		{
			System.out.println("performing TC3simpleTest()...");
			assertTrue(true);
		}
	}
}
