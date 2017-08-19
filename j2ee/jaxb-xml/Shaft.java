package com.khallware.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;

public class Shaft
{
	@XmlElement private Throat throat = null;
	@XmlElement private Handle handle = null;

	public static class Throat
	{
		@XmlAttribute private float width = 0.0f;
		@XmlAttribute private float height = 0.0f;

		public Throat() {} // required by Unmarshaller.unmarshal()

		public Throat(float width, float height)
		{
			this.width = width;
			this.height = height;
		}
	}

	public static class Handle
	{
		@XmlElement private Grip grip = null;
		@XmlElement private Butt butt = null;
		@XmlElement private float length = 0.0f;

		public Handle() {} // required by Unmarshaller.unmarshal()

		public Handle(Grip grip, Butt butt, float length)
		{
			this.grip = grip;
			this.butt = butt;
			this.length = length;
		}
	}

	public static class Grip
	{
		@XmlAttribute private String material = null;
		@XmlAttribute private String color = null;

		public Grip() {} // required by Unmarshaller.unmarshal()

		public Grip(String material, String color)
		{
			this.material = material;
			this.color = color;
		}
	}

	public static class Butt
	{
		@XmlAttribute private float radius = 0.0f;

		public Butt() {} // required by Unmarshaller.unmarshal()

		public Butt(float radius)
		{
			this.radius = radius;
		}
	}

	public Shaft() {} // required by Unmarshaller.unmarshal()

	public Shaft(Throat throat, Handle handle)
	{
		this.throat = throat;
		this.handle = handle;
	}

	public float getHandleLength()
	{
		return(handle.length);
	}
}
