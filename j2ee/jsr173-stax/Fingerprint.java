package com.khallware.poc.stax;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Fingerprint
{
	public static final String DATE_FORMAT = "yyyMMdd-HH:mm:ss";
	private List<Minutiae> list = new ArrayList<>();
	private String name = "";
	private Date date = null;

	public static class Minutiae
	{
		public static final int MIN_X = 0;
		public static final int MIN_Y = 0;
		public static final int MAX_X = 512;
		public static final int MAX_Y = 512;
		public static enum Type { ENDING, BIFURCATION, DOT, ISLAND,
			ENCLOSURE, SPUR, BRIDGE, DELTA, CORE };

		private Point point = null;
		private Type type = null;

		public static class Point
		{
			private int x = MIN_X;
			private int y = MIN_Y;

			public Point(int x, int y)
			{
				this.x = x;
				this.y = y;

				if (x < MIN_X || x > MAX_X || y < MIN_Y
						|| y > MAX_Y) {
					throw new RuntimeException(
						"point values out of bounds");
				}
			}

			@Override
			public String toString()
			{
				return("["+x+", "+y+"]");
			}
		}

		public Minutiae(Point point, Type type)
		{
			this.point = point;
			this.type = type;
		}

		public Point getPoint()
		{
			return(point);
		}

		public Type getType()
		{
			return(type);
		}

		@Override
		public String toString()
		{
			return(String.format("minutiae (point: %s type: %s)",
				""+point, (""+type).toLowerCase()));
		}
	}

	public static class Builder
	{
		private Fingerprint retval = new Fingerprint("",new Date());
		private SimpleDateFormat fmt =new SimpleDateFormat(DATE_FORMAT);

		public Builder name(String name)
		{
			retval.name = name;
			return(this);
		}

		public Builder date(String date)
		{
			try {
				retval.date = fmt.parse(date);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			return(this);
		}

		public Builder minutiae(String point, Minutiae.Type type)
		{
			String[] dat = point.split(",");
			int x = Integer.parseInt(dat[0]);
			int y = Integer.parseInt(dat[1]);
			return(minutiae(x, y, type));
		}

		public Builder minutiae(int x, int y, Minutiae.Type type)
		{
			retval.add(new Minutiae(new Minutiae.Point(x,y), type));
			return(this);
		}

		public Fingerprint build()
		{
			return(retval);
		}
	}

	public Fingerprint(String name, Date date)
	{
		this.name = name;
		this.date = date;
	}

	public void add(Minutiae minutiae)
	{
		list.add(minutiae);
	}

	@Override
	public String toString()
	{
		SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT);
		return(String.format("name: %s\ndate: %s\nminutiae (%d): %s\n",
			name, fmt.format(date), list.size(), ""+list));
	}
}
