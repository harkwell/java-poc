package com.khallware.patterns.decorator;

/**
 * The decorator pattern is an alternative to subclassing.  It allows new
 * functionality to be added for selective classes.
 *
 * Build:
 * javac -d /tmp Main.java
 * echo 'Main-Class: com.khallware.patterns.decorator.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/decorator-pattern.jar -C /tmp com/
 * java -jar /tmp/decorator-pattern.jar
 * rm -rf /tmp/com /tmp/manifest /tmp/decorator-pattern.jar
 */
public class Main
{
	public static interface Camera
	{
		public byte[] snap();
	}

	public static class PlainCamera implements Camera
	{
		public byte[] snap()
		{
			return("image".getBytes());
		}
	}

	public static abstract class CameraDecorator implements Camera
	{
		protected Camera camera = null;

		public CameraDecorator(Camera camera)
		{
			this.camera = camera;
		}

		public byte[] snap()
		{
			return(camera.snap());
		}
	}

	public static class PlainCameraWithFilter extends CameraDecorator
	{
		public PlainCameraWithFilter(Camera camera)
		{
			super(camera);
		}

		@Override
		public byte[] snap()
		{
			return(filter(camera.snap()));
		}

		public byte[] filter(byte[] image)
		{
			return(new String(image).toUpperCase().getBytes());
		}
	}

	public static void main(String... args) throws Exception
	{
		Camera plainCamera = new PlainCamera();
		Camera betterCamera = new PlainCameraWithFilter(plainCamera);
		System.out.printf("%s\n", new String(betterCamera.snap()));
	}
}
