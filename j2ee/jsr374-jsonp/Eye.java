package com.khallware.poc.jsonp;

/**
 * WARNING:  This is a hugely inaccurate model of the eye and should
 *           NOT be used in any kind of serious study of it.  It is only
 *           used here to demonstrate some capabilities of jsonp.
 *
 */

import javax.json.Json;
import java.util.Arrays;
import java.util.List;

public class Eye
{
	public static final String UNITS = "diopters";
	private double opticPower = 0.0d;
	private Cornea cornea = null;
	private Sclera sclera = null;

	public static class Cornea
	{
		public static final String UNITS = "nm";
		private double thickness = 0.0d;
		private double curvature = 0.0d;
		private double refractiveIndex = 0.0d;
		private Iris iris = null;
		private Pupil pupil = null;

		public Cornea(Iris iris, Pupil pupil)
		{
			this.iris = iris;
			this.pupil = pupil;
		}

		public double getThickness()
		{
			return(thickness);
		}

		public double getCurvature()
		{
			return(curvature);
		}

		public double getRefractiveIndex()
		{
			return(refractiveIndex);
		}

		public void enforceValidity()
		{
			pupil.enforceSize();
		}

		public String toJsonString()
		{
			return(String.format("{ \"thickness\" : \"%f %s\", "
				+"\"curvature\" : \"%f %s\", "
				+"\"refractiveIndex\" : \"%f %s\", "
				+"\"pupil\" : %s, \"iris\" : %s }",
				thickness, UNITS, curvature, UNITS,
				refractiveIndex, UNITS,
				pupil.toJsonString(), iris.toJsonString()));
		}
	}

	public static class Pupil
	{
		public static final String UNITS = "nm";
		private double size = 0.0d;

		public double getSize()
		{
			return(size);
		}

		public void enforceSize()
		{
			if (size < 1.5d || size > 8.0d) {
				throw new RuntimeException(
					"invalid pupil size");
			}
		}

		public String toJsonString()
		{
			return(String.format("{ \"size\" : \"%f %s\" }",
				size, UNITS));
		}
	}

	public static class Iris
	{
		public static final String UNITS = "nm";
		private double stiffness = 0.0d;

		public double getStiffness()
		{
			return(stiffness);
		}

		public String toJsonString()
		{
			return(String.format("{ \"stiffness\" : \"%f %s\" }",
				stiffness, UNITS));
		}
	}

	public static class Sclera
	{
		public static final String UNITS = "g/cm3";
		private double density = 0.0d;
		private Choroid choroid = null;

		public Sclera(Choroid choroid)
		{
			this.choroid = choroid;
		}

		public double getDensity()
		{
			return(density);
		}

		public void enforceValidity()
		{
			choroid.enforceValidity();
		}

		public String toJsonString()
		{
			return(String.format(
				"{ \"density\" : \"%f %s\", \"choroid\" : %s }",
				density, UNITS, choroid.toJsonString()));
		}
	}

	public static class Choroid
	{
		private double elasticity = 0.0d;
		private Retina retina = null;

		public Choroid(Retina retina)
		{
			this.retina = retina;
		}

		public double getElasticity()
		{
			return(elasticity);
		}

		public void enforceValidity()
		{
			retina.enforceValidity();
		}

		public String toJsonString()
		{
			return(String.format(
				"{ \"elasticity\" : %f, \"retina\" : %s }",
				elasticity, retina.toJsonString()));
		}
	}

	public static class Retina
	{
		private Object rods = null;
		private Object cones = null;
		private Lens lens = null;
		private Vitreous vitreous = null;

		public Retina(Lens lens, Vitreous vitreous)
		{
			this.lens = lens;
			this.vitreous = vitreous;
		}

		public Object[] getRodsAndCones()
		{
			return(new Object[] { rods, cones });
		}

		public void enforceValidity()
		{
			lens.enforceValidity();
			vitreous.enforceValidity();
		}

		public String toJsonString()
		{
			return(String.format("{ \"rods\" : {}, \"cones\" : {}, "
				+"\"lens\" : %s, \"vitreous\" : %s }",
				lens.toJsonString(), vitreous.toJsonString()));
		}
	}

	public static class Lens
	{
		public static final String UNITS = "nm";
		private double thickness = 0.0d;

		public double getThickness()
		{
			return(thickness);
		}

		public void enforceValidity()
		{
			if (thickness < 4.0d || thickness > 10.0d) {
				throw new RuntimeException("invalid lens size");
			}
		}

		public String toJsonString()
		{
			return(String.format("{ \"thickness\" : \"%f %s\" }",
				thickness, UNITS));
		}
	}

	public static class Vitreous
	{
		private double volume = 0.0d;
		private Macula macula = null;

		public Vitreous(Macula macula)
		{
			this.macula = macula;
		}

		public double getVolume()
		{
			return(volume);
		}

		public void enforceValidity()
		{
			macula.enforceValidity();
		}

		public String toJsonString()
		{
			return(String.format(
				"{ \"volume\" : %f, \"macula\" : %s }",
				volume, macula.toJsonString()));
		}
	}

	public static class Macula
	{
		private double reninReleaseRate = 0.0d;

		public double getReninReleaseRate()
		{
			return(reninReleaseRate);
		}

		public void enforceValidity() {}

		public String toJsonString()
		{
			return(String.format("{ \"reninReleaseRate\" : %f }",
				reninReleaseRate));
		}
	}

	public static class Factory
	{
		private double opticPower = 0.0d;
		private double reninReleaseRate = 0.0d;
		private double volume = 0.0d;
		private double thickness = 0.0d;
		private double elasticity = 0.0d;
		private double density = 0.0d;
		private double size = 0.0d;
		private double stiffness = 0.0d;
		private Object rods = new Object();
		private Object cones = new Object();

		public Factory power(double opticPower)
		{
			this.opticPower = opticPower;
			return(this);
		}

		public Factory iris(double stiffness)
		{
			this.stiffness = stiffness;
			return(this);
		}

		public Factory pupil(double size)
		{
			this.size = size;
			return(this);
		}

		public Factory sclera(double density)
		{
			this.density = density;
			return(this);
		}

		public Factory retina(Object rods, Object cones)
		{
			this.rods = rods;
			this.cones = cones;
			return(this);
		}

		public Factory choroid(double elasticity)
		{
			this.elasticity = elasticity;
			return(this);
		}

		public Factory lens(double thickness)
		{
			this.thickness = thickness;
			return(this);
		}

		public Factory vitreous(double volume)
		{
			this.volume = volume;
			return(this);
		}

		public Factory macula(double reninReleaseRate)
		{
			this.reninReleaseRate = reninReleaseRate;
			return(this);
		}

		public Eye build()
		{
			Eye retval = null;
			Macula macula = new Macula();
			Vitreous vitreous = new Vitreous(macula);
			Lens lens = new Lens();
			Retina retina = new Retina(lens, vitreous);
			Choroid choroid = new Choroid(retina);
			Sclera sclera = new Sclera(choroid);
			Iris iris = new Iris();
			Pupil pupil = new Pupil();
			Cornea cornea = new Cornea(iris, pupil);
			macula.reninReleaseRate = reninReleaseRate;
			vitreous.volume = volume;
			lens.thickness = thickness;
			retina.rods = rods;
			retina.cones = cones;
			choroid.elasticity = elasticity;
			sclera.density = density;
			pupil.size = size;
			iris.stiffness = stiffness;
			retval = new Eye(cornea, sclera);
			retval.opticPower = opticPower;
			retval.enforceValidity();
			return(retval);
		}
	}

	public Eye(Cornea cornea, Sclera sclera)
	{
		this.cornea = cornea;
		this.sclera = sclera;
	}

	public double getOpticPower()
	{
		return(opticPower);
	}

	public void enforceValidity()
	{
		cornea.enforceValidity();
		sclera.enforceValidity();
	}

	public String toJsonString()
	{
		return(String.format(
			"{ \"opticPower\" : %f, \"cornea\" : %s, "
			+"\"sclera\" : %s }",
			opticPower, cornea.toJsonString(),
			sclera.toJsonString()));
	}
}
