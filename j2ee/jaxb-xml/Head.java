package com.khallware.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;

public class Head
{
	@XmlAttribute private float size = 0.0f;
	@XmlElement private BumperGuard bumperGuard = null;
	@XmlElement private TRString trString = null;
	@XmlElement private Grommet grommet = null;
	@XmlElement private Beam beam = null;

	public static class BumperGuard
	{
		@XmlElement private String material = null;

		public BumperGuard() {} // required by Unmarshaller.unmarshal()

		public BumperGuard(String material)
		{
			this.material = material;
		}
	}

	public static class TRString
	{
		@XmlElement private String material = null;

		public TRString() {} // required by Unmarshaller.unmarshal()

		public TRString(String material)
		{
			this.material = material;
		}
	}

	public static class Beam
	{
		@XmlAttribute private float width = 0.0f;

		public Beam() {} // required by Unmarshaller.unmarshal()

		public Beam(float width)
		{
			this.width = width;
		}
	}

	public static class Grommet
	{
		@XmlAttribute private float radius = 0.0f;

		public Grommet() {} // required by Unmarshaller.unmarshal()

		public Grommet(float radius)
		{
			this.radius = radius;
		}
	}

	public Head() {} // required by Unmarshaller.unmarshal()

	public Head(float size, BumperGuard bumperGuard, TRString trString,
			Beam beam, Grommet grommet)
	{
		this.size = size;
		this.bumperGuard = bumperGuard;
		this.trString = trString;
		this.grommet = grommet;
		this.beam = beam;
	}

	public float getHeadSize()
	{
		return(size);
	}
}
