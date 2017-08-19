package com.khallware.jaxb;

import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 * javac -d /tmp/ *java
 * echo 'Main-Class: com.khallware.jaxb.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/jaxb-poc.jar -C /tmp com 
 * java -jar /tmp/jaxb-poc.jar 2>&1 |tee /tmp/x.xml
 * java -jar /tmp/jaxb-poc.jar </tmp/x.xml
 * rm -rf /tmp/com /tmp/jaxb-poc.jar /tmp/x.xml
 */
public class Main
{
	public static void main(String... args) throws Exception
	{
		Racquet racquet = Racquet.Builder.getBuilder()
			.model("Wilson")
			.head(99.0f)
			.bumperGuard("hard plastic")
			.trString("nylon")
			.beam(0.5f)
			.grommet(0.025f)
			.handle(20.0f)
			.throat(3.25f, 3.75f)
			.grip("rubber", "black")
			.butt(1.05f)
			.build();
		JAXBContext ctxt = JAXBContext.newInstance(Racquet.class);
		Marshaller marshaller = ctxt.createMarshaller();
		Unmarshaller unmarshaller = ctxt.createUnmarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(racquet, System.out);

		if (System.in.available() > 0) {
			racquet = (Racquet)unmarshaller.unmarshal(
				new StreamSource(System.in));
			System.out.println(racquet);
		}
	}
}
