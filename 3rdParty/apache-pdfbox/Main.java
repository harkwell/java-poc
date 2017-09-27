package com.khallware.poc.pdfbox;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import java.util.GregorianCalendar;
import java.io.IOException;
import java.io.File;
import java.awt.Color;
import javax.imageio.ImageIO;

/**
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=url \
 *     -Dartifact=org.apache.pdfbox:pdfbox:2.0.7
 * PDFBOX_JAR=$(find $POC_MAVEN_REPO -name \*pdfbox\*jar)
 * javac -cp $PDFBOX_JAR -d /tmp Main.java
 * echo 'Main-Class: com.khallware.poc.pdfbox.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/pdfbox-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 *
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=url \
 *     -Dartifact=org.apache.pdfbox:fontbox:2.0.7
 * JAR1=$(find $POC_MAVEN_REPO -name \*logging-1.0.4\*jar)
 * JAR2=$(find $POC_MAVEN_REPO -name \*fontbox\*jar)
 * java -cp $PDFBOX_JAR:/tmp/pdfbox-poc.jar:$JAR1:$JAR2 \
 *          com.khallware.poc.pdfbox.Main /tmp/foo.pdf "some simple text"
 * rm -rf /tmp/pdfbox-poc.jar $POC_MAVEN_REPO /tmp/foo.pdf /tmp/foo.pdf.jpg
 *
 */
public class Main
{
	public static PDDocument setDocumentAttributes(PDDocument doc)
	{
		PDDocumentInformation info = doc.getDocumentInformation();
		info.setAuthor("Kevin D.Hall");
		info.setTitle("khallware pdfbox example");
		info.setCreator("khallware.com");
		info.setSubject("pdfbox howto");
		info.setCreationDate(new GregorianCalendar());
		info.setModificationDate(new GregorianCalendar());
		info.setKeywords("pdfbox, howto, khallware");
		return(doc);
	}

	public static void addText(PDPageContentStream pcs, String content,
			boolean done) throws IOException
	{
		pcs.beginText();
		pcs.setFont(PDType1Font.TIMES_ROMAN, 10);
		pcs.newLineAtOffset(25, 500);
		pcs.showText(content);
		pcs.endText();
		pcs.setNonStrokingColor(makeColor("#51748e"));
		pcs.addRect(20,450,200,200);
		// pcs.fill();

		if (done) {
			pcs.close();
		}
	}

	public static String dumpText(PDDocument doc) throws IOException
	{
		return(new PDFTextStripper().getText(doc));
	}

	public static void toJpeg(File file, PDDocument doc) throws IOException
	{
		ImageIO.write(new PDFRenderer(doc).renderImage(0),"JPEG", file);
	}

	private static Color makeColor(String hex)
	{
		return(new Color(
			Integer.valueOf(hex.substring(1,3), 16),
			Integer.valueOf(hex.substring(3,5), 16),
			Integer.valueOf(hex.substring(5,7), 16)));
	}

	public static void main(String... args) throws Exception
	{
		File file = new File(args[0]);
		boolean existing = false;
		PDDocument doc = ((existing = file.exists()))
			? PDDocument.load(file)
			: new PDDocument();
		PDPage page = new PDPage();
		Main.setDocumentAttributes(doc);

		if (!existing) {
			Main.addText(new PDPageContentStream(doc, page),
				args[1], true);
			doc.addPage(page);
			doc.save(args[0]);
		}
		else {
			System.out.println(dumpText(doc));
			Main.toJpeg(new File(args[0]+".jpg"), doc);
		}
		doc.close();
	}
}
