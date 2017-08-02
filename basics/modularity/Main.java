package com.khallware.firearm;

import java.util.List;

public class Main
{
	public static interface Firearm
	{
		public List<Part> partlist();
		public Bullet getBulletType();
		public String getDescription();
	}

	public static interface Part
	{
		public String getFunction();
		public String getLocation();
	}

	public static class Barrel implements Part
	{
		public static class Sight implements Part
		{
			public String getFunction()
			{
				return("launches the bullet");
			}

			public String getLocation()
			{
				return("atop the stock");
			}
		}

		public static class Bolt implements Part
		{
			public String getFunction()
			{
				return("loads the bullet into the chamber");
			}

			public String getLocation()
			{
				return("at the end of the barrel");
			}
		}

		public static class Chamber implements Part
		{
			public String getFunction()
			{
				return("holds and readies the bullet");
			}

			public String getLocation()
			{
				return("at the end of the barrel");
			}
		}

		public static class Muzzle implements Part
		{
			public String getFunction()
			{
				return("finalizes the shot");
			}

			public String getLocation()
			{
				return("in front of the barrel");
			}
		}

		public String getFunction()
		{
			return("implements the firearm");
		}

		public String getLocation()
		{
			return("atop the stock");
		}
	}

	public static class Stock implements Part
	{
		public static class Trigger implements Part
		{
			public String getFunction()
			{
				return("engages the hammer to fire");
			}

			public String getLocation()
			{
				return("under the stock");
			}
		}

		public static class Safety implements Part
		{
			public String getFunction()
			{
				return("prohibits accidental firing");
			}

			public String getLocation()
			{
				return("on the stock");
			}
		}

		public static class Guard implements Part
		{
			public String getFunction()
			{
				return("protects the trigger");
			}

			public String getLocation()
			{
				return("around the trigger");
			}
		}

		public static class Magazine implements Part
		{
			public String getFunction()
			{
				return("acts as a bullet reservoir");
			}

			public String getLocation()
			{
				return("under the stock engaging the bolt");
			}
		}

		public String getFunction()
		{
			return("interfaces with the shooter");
		}

		public String getLocation()
		{
			return("behind the stock");
		}
	}

	public static class Bullet
	{
		private String caliper;
		private int grain = -1;
		private int speed = -1;

		public Bullet(String caliper, int grain, int speed)
		{
			this.caliper = caliper;
			this.grain = grain;
			this.speed = speed;
		}

		@Override
		public String toString()
		{
			return(String.format("%s caliper bullet with %d grain "
				+"and a velocity of %d feet per second",
				caliper, grain, speed));
		}
	}

	public static class Rifle implements Firearm
	{
		private List<Part> parts = new java.util.ArrayList<>();
		private Bullet bullet = new Bullet("12 guage", 365, 1330);

		public Rifle()
		{
			parts.add(new Barrel());
			parts.add(new Barrel.Sight());
			parts.add(new Barrel.Bolt());
			parts.add(new Barrel.Chamber());
			parts.add(new Barrel.Muzzle());
			parts.add(new Stock());
			parts.add(new Stock.Safety());
			parts.add(new Stock.Guard());
			parts.add(new Stock.Magazine());
		}

		public List<Part> partlist()
		{
			return(parts);
		}

		public Bullet getBulletType()
		{
			return(bullet);
		}

		public String getDescription()
		{
			return("double barrel 12 guage pump");
		}

		@Override
		public String toString()
		{
			StringBuilder retval = new StringBuilder();
			retval.append(String.format(
				"A %s firearm has %d parts. ",
				getDescription(), partlist().size()));

			for (Part part : partlist()) {
				retval.append(String.format(
					"A \"%s\" is located %s and %s. ",
					part.getClass().getSimpleName(),
					part.getLocation(),
					part.getFunction()));
			}
			retval.append(String.format(
				"The firearm uses a %s.\n", getBulletType()));
			return(""+retval);
		}
	}

	public static void main(String... args) throws Exception
	{
		System.out.println(new Rifle().toString());
	}
}
