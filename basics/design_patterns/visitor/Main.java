package com.khallware.patterns.visitor;

import java.util.List;

/**
 * The visitor pattern adds a method into a class that accepts a visitor to
 * itself for the visitors own purpose.
 *
 * Build:
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.patterns.visitor.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/visitor-pattern.jar -C /tmp com/
 * java -jar /tmp/visitor-pattern.jar
 * rm -rf /tmp/com /tmp/manifest /tmp/visitor-pattern.jar
 */
public class Main
{
	public static interface GuitarPart
	{
		public void accept(Visitor visitor);
	}

	public static interface Visitor
	{
		public void visit(Guitar guitar);
		public void visit(Head head);
		public void visit(Neck neck);
		public void visit(Body body);
		public void visit(Bridge bridge);
	}

	public static class Head implements GuitarPart
	{
		public void accept(Visitor visitor)
		{
			visitor.visit(this);
		}
	}

	public static class Neck implements GuitarPart
	{
		public void accept(Visitor visitor)
		{
			visitor.visit(this);
		}
	}

	public static class Body implements GuitarPart
	{
		public void accept(Visitor visitor)
		{
			visitor.visit(this);
		}
	}

	public static class Bridge implements GuitarPart
	{
		public void accept(Visitor visitor)
		{
			visitor.visit(this);
		}
	}

	public static class Guitar implements GuitarPart
	{
		List<GuitarPart> list = new java.util.ArrayList<>();

		public Guitar()
		{
			list.add(new Head());
			list.add(new Neck());
			list.add(new Body());
			list.add(new Bridge());
		}

		public void accept(Visitor visitor)
		{
			for (GuitarPart part : list) {
				part.accept(visitor);
			}
			visitor.visit(this);
		}
	}

	public static class PrintVisitor implements Visitor
	{
		public void visit(Guitar guitar)
		{
			System.out.println("guitar: "+guitar);
		}

		public void visit(Head head)
		{
			System.out.println("head: "+head);
		}

		public void visit(Neck neck)
		{
			System.out.println("neck: "+neck);
		}

		public void visit(Body body)
		{
			System.out.println("body: "+body);
		}

		public void visit(Bridge bridge)
		{
			System.out.println("bridge: "+bridge);
		}
	}

	public static void main(String... args) throws Exception
	{
		Guitar guitar = new Guitar();
		guitar.accept(new PrintVisitor());
	}
}
