package com.khallware.poc.stax;

import java.util.List;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import javax.xml.namespace.QName;

/**
 * javac -d /tmp *.java
 * echo 'Main-Class: com.khallware.poc.stax.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/stax-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -jar /tmp/stax-poc.jar fingerprints.xml
 * rm /tmp/stax-poc.jar
 */
public class Main
{
	private static void handle(StartElement se, Fingerprint.Builder builder)
	{
		Attribute attrib = null;
		QName qn1 = new QName("name");
		QName qn2 = new QName("date");
		QName qn3 = new QName("point");
		QName qn4 = new QName("type");

		switch (se.getName().getLocalPart().toLowerCase()) {
		case "fingerprints":
			break;
		case "fingerprint":
			if ((attrib = se.getAttributeByName(qn1)) != null) {
				builder.name(attrib.getValue());
			}
			if ((attrib = se.getAttributeByName(qn2)) != null) {
				builder.date(attrib.getValue());
			}
			break;
		case "minutiae":
			Fingerprint.Minutiae.Type type = null;

			if ((attrib = se.getAttributeByName(qn4)) != null) {
				type = Fingerprint.Minutiae.Type.valueOf(
					attrib.getValue().toUpperCase());
			}
			if ((attrib = se.getAttributeByName(qn3)) != null) {
				builder.minutiae(attrib.getValue(), type);
			}
			break;
		default:
			System.out.println("unhandled xml start tag");
			break;
		}
	}

	private static List<Fingerprint> readFingerprints(String filename)
	{
		try {
			return(readFingerprintsUnwrapped(filename));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static List<Fingerprint> readFingerprintsUnwrapped(
			String filename) throws FileNotFoundException,
			XMLStreamException
	{
		List<Fingerprint> retval = new ArrayList<>();
		XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLEventReader xer = xif.createXMLEventReader(
			new FileInputStream(filename));
		XMLEvent xmlEvent = null;
		String val = null;
		Fingerprint.Builder builder = new Fingerprint.Builder();

		while(xer.hasNext()) {
			if ((xmlEvent = xer.nextEvent()).isStartElement()) {
				handle(xmlEvent.asStartElement(), builder);
			}
			else if (xmlEvent.isEndElement()) {
				EndElement ee = xmlEvent.asEndElement();
				val = ee.getName().getLocalPart().toLowerCase();

				if ("fingerprint".equals(val)) {
					retval.add(builder.build());
					builder = new Fingerprint.Builder();
				}
			}
		}
		return(retval);
	}

	public static void main(String... args) throws Exception
	{
		for (Fingerprint fingerprint : readFingerprints(args[0])) {
			System.out.println(""+fingerprint);
		}
	}
}
