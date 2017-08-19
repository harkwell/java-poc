package com.khallware.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement
public class Racquet
{
	@XmlAttribute private String model = null;
	@XmlElement private Head head = null;
	@XmlElement private Shaft shaft = null;

	public static class Builder
	{
		String model = null;
		Head.Beam beam = null;
		Head.Grommet grommet = null;
		Head.TRString trString = null;
		Head.BumperGuard bumperGuard = null;
		Shaft.Throat throat = null;
		Shaft.Grip grip = null;
		Shaft.Butt butt = null;
		float headSize = 0.0f;
		float handleLen = 0.0f;

		public static Builder getBuilder()
		{
			return(new Builder());
		}

		public Builder model(String model)
		{
			this.model = model;
			return(this);
		}

		public Builder head(float headSize)
		{
			this.headSize = headSize;
			return(this);
		}

		public Builder bumperGuard(String material)
		{
			bumperGuard = new Head.BumperGuard(material);
			return(this);
		}

		public Builder trString(String material)
		{
			trString = new Head.TRString(material);
			return(this);
		}

		public Builder beam(float width)
		{
			beam = new Head.Beam(width);
			return(this);
		}

		public Builder grommet(float radius)
		{
			grommet = new Head.Grommet(radius);
			return(this);
		}

		public Builder handle(float handleLen)
		{
			this.handleLen = handleLen;
			return(this);
		}

		public Builder throat(float width, float height)
		{
			this.throat = new Shaft.Throat(width, height);
			return(this);
		}

		public Builder grip(String material, String color)
		{
			grip = new Shaft.Grip(material, color);
			return(this);
		}

		public Builder butt(float radius)
		{
			butt = new Shaft.Butt(radius);
			return(this);
		}

		public Racquet build()
		{
			boolean missing = false;
			missing |= (beam == null);
			missing |= (model == null);
			missing |= (trString == null);
			missing |= (bumperGuard == null);
			missing |= (grommet == null);
			missing |= (throat == null);
			missing |= (grip == null);
			missing |= (butt == null);
			missing |= (headSize <= 0.0001f);
			missing |= (handleLen <= 0.0001f);

			if (missing) {
				throw new RuntimeException("missing pieces...");
			}
			return(new Racquet(
				model,
				new Head(headSize, bumperGuard, trString,
					beam, grommet),
				new Shaft(throat,
					new Shaft.Handle(grip,butt,handleLen)))
			);
		}
	}

	public Racquet() {} // this is for JAXB JAXBContext.newInstance()

	public Racquet(String model, Head head, Shaft shaft)
	{
		this.model = model;
		this.head = head;
		this.shaft = shaft;
	}

	@Override
	public String toString()
	{
		return(String.format("%s tennis racquet with %.2f square inch "
			+"head and %.2f inch length handle",
			model, head.getHeadSize(), shaft.getHandleLength()));
	}
}
