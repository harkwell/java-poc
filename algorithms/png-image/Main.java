package com.khallware.png;

import java.util.zip.Deflater;
import java.util.zip.CRC32;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.awt.image.DataBufferByte;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;

/**
 *
 * javac -d /tmp algorithms/png-image/Main.java
 * echo 'Main-Class: com.khallware.png.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/png-poc.jar -C /tmp com
 *
 * java -jar /tmp/png-poc.jar khallware /tmp/foo.png
 * rm -rf /tmp/com /tmp/manifest /tmp/png-poc.jar
 *
 * URLs:
 *    http://www.libpng.org/pub/png/spec/1.2/PNG-Chunks.html
 */
public class Main
{
	/* Color Type:    Allowed Depth:    Description (each pixel is...):
	 *    0              1,2,4,8,16     a grayscale sample.
	 *    2              8,16           an R,G,B triple.
	 *    3              1,2,4,8        palette index; a PLTE chunk required
	 *    4              8,16           grayscale sample, then alpha sample
	 *    6              8,16           R,G,B triple, then alpha sample
	 *
	 * Compression: 0 = deflate/inflate w/ sliding window of <= 32768 bytes
	 * Filter: 0 = adaptive filtering with five basic filter types
	 * Interlace: 0 = none, 1 = Adam7 interlace transmission order of data
	 */
	public static final int IMG_WIDTH = 1024;
	public static final int IMG_HEIGHT = 768;
	public static final int IMG_COLORTYPE = 6;
	public static final int IMG_DEPTH = 8;
	public static final int IMG_COMPRESSION = 0;
	public static final int IMG_FILTER = 0;
	public static final int IMG_INTERLACE = 0;
	public static final int IMG_TYPE = BufferedImage.TYPE_INT_ARGB;

	public static class Chunk
	{
		private int len = -1;
		private String type = "";
		private byte[] data = new byte[] {};
		private long crc32 = -1;

		public Chunk(int len, String type)
		{
			this.len = len;
			this.type = type;
		}

		public byte[] getlen()
		{
			ByteBuffer retval = ByteBuffer.allocate(Integer.BYTES);
			retval.putInt(len);
			return(retval.array());
		}

		public byte[] gettype()
		{
			if (type.length() != 4) {
				throw new RuntimeException(
					"chunk type must be four bytes");
			}
			return(type.getBytes());
		}

		public byte[] getCRC32()
		{
			ByteBuffer retval = ByteBuffer.allocate(Integer.BYTES);

			if (crc32 < 0) {
				updateCRC32();
			}
			if (crc32 >Integer.MAX_VALUE) {
				// throw new RuntimeException(
				// 	"the crc32 value is too big for png");
			}
			retval.putInt((int)crc32);
			return(retval.array());
		}

		private void updateCRC32()
		{
			byte[] bytes = new byte[4+data.length];
			CRC32 crc32 = new CRC32();
			int idx = 0;

			for (byte b : gettype()) {
				bytes[idx++] = b;
			}
			for (byte b : data) {
				bytes[idx++] = b;
			}
			crc32.update(bytes);
			this.crc32 = crc32.getValue();
		}

		public void setData(byte[] data)
		{
			this.data = data;
		}

		public boolean isCritical()
		{
			String flag = new String(new byte[] { gettype()[0] });
			return(flag.equals(flag.toUpperCase()));
		}

		public void write(FileOutputStream fos) throws IOException
		{
			updateCRC32();
			fos.write(getlen());
			fos.write(gettype());
			fos.write(data);
			fos.write(getCRC32());
		}

		@Override
		public String toString()
		{
			return(type);
		}
	}

	public static Image createArbitraryImage(String text)
	{
		Image retval = new BufferedImage(IMG_WIDTH,IMG_HEIGHT,IMG_TYPE);
		Graphics2D gfx2d = ((BufferedImage)retval).createGraphics();
		gfx2d.getFontRenderContext().getTransform().scale(30d,30d);
		gfx2d.setPaint(makeColor("#51748e"));
		gfx2d.draw3DRect(5,5,IMG_WIDTH-5,10,true);
		gfx2d.drawString(text, 5, 15);
		// javax.imageio.ImageIO.write((BufferedImage)retval, "png",
		// 	new java.io.File("/tmp/foo1.png"));
		return(retval);
	}

	public static Color makeColor(String hex)
	{
		return(new Color(
			Integer.valueOf(hex.substring(1,3), 16),
			Integer.valueOf(hex.substring(3,5), 16),
			Integer.valueOf(hex.substring(5,7), 16)));
	}

	public static byte[] createHeader()
	{
		byte[] retval = new byte[8];
		retval[0] = (byte)0x89; // PNG magic identifier

		retval[1] = 0x50; // ascii "PNG"
		retval[2] = 0x4E;
		retval[3] = 0x47;

		retval[4] = 0x0D; // DOS CRLF characters
		retval[5] = 0x0A;

		retval[6] = 0x1A; // DOS EOF character

		retval[7] = 0x0A; // Unix newline character
		return(retval);
	}

	public static Chunk createIHDR()
	{
		Chunk retval = null;
		byte[] data = new byte[] {
			0b00000000, 0b00000000, 0b00000000, 0b00000000,
			0b00000000, 0b00000000, 0b00000000, 0b00000000,
			(byte)IMG_DEPTH, (byte)IMG_COLORTYPE,
			(byte)IMG_COMPRESSION, (byte)IMG_FILTER,
			(byte)IMG_INTERLACE
		};
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(IMG_WIDTH);
		data[0] = bb.array()[0];
		data[1] = bb.array()[1];
		data[2] = bb.array()[2];
		data[3] = bb.array()[3];
		bb.clear();
		bb.putInt(IMG_HEIGHT);
		data[4] = bb.array()[0];
		data[5] = bb.array()[1];
		data[6] = bb.array()[2];
		data[7] = bb.array()[3];
		retval = new Chunk(data.length, "IHDR");
		retval.setData(data);
		return(retval);
	}

	/**
	 * IDAT chunk = contains the actual image data (compressed scanlines)
	 * scanline = starts with filter byte, then sequential pixels
	 * pixel = multiple samples (eg red, green, blue, alpha)
	 * sample = stored in natural big-endian (network byte order / MSB)
	 */
	public static Chunk createIDAT(BufferedImage image) throws IOException
	{
		Chunk retval = null;
		byte[] data = new byte[] {};
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		for (int y=0; y<IMG_HEIGHT; y++) {
			// create "scanlines" corresponding to IHDR
			// prepend a filter-type byte to each scanline
			baos.write(0b00000000); // filter

			for (int x=0; x<IMG_WIDTH; x++) {
				Color color = new Color(image.getRGB(x,y));
				baos.write((byte)color.getRed());
				baos.write((byte)color.getGreen());
				baos.write((byte)color.getBlue());
				baos.write((byte)color.getAlpha());
			}
		}
		// compress the filtered data corresponding to IHDR
		Deflater deflater = new Deflater();
		byte[] buffer = new byte[baos.size()];
		deflater.setInput(baos.toByteArray());
		deflater.finish();
		baos.close();
		int len = deflater.deflate(buffer);
		// data = new byte[deflater.getTotalOut()];
		data = new byte[len];

		for (int idx=0; idx < len; idx++) {
			data[idx] = buffer[idx];
		}
		retval = new Chunk(data.length, "IDAT");
		retval.setData(data);
		return(retval);
	}

	public static Chunk createIEND()
	{
		return(new Chunk(0, "IEND"));
	}

	public static void main(String... args) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(args[1]);
		byte[] header = Main.createHeader();
		Chunk[] chunks = new Chunk[] {
			Main.createIHDR(),
			Main.createIDAT((BufferedImage)
				Main.createArbitraryImage(args[0])),
			Main.createIEND()
		};
		fos.write(header);

		for (Chunk chunk : chunks) {
			System.out.println("writing chunk "+chunk);
			chunk.write(fos);
		}
		fos.close();
	}
}
