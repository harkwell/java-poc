// see: http://stackoverflow.com/questions/1798366/how-do-i-find-out-which-jaxp-implementation-is-in-use-and-where-it-was-loaded-fr
// mkdir /tmp/foo && cd /tmp/foo
// javac WhichJaxp.java
// java -cp . WhichJaxp

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPathFactory;
import java.security.CodeSource;
import java.text.MessageFormat;

public class WhichJaxp
{
	private static void OutputJaxpImplementationInfo()
	{
		System.out.println(
			getJaxpImplementationInfo(
				"DocumentBuilderFactory",
				DocumentBuilderFactory.newInstance().getClass()
			)
		);
		System.out.println(
			getJaxpImplementationInfo(
				"XPathFactory",
				XPathFactory.newInstance().getClass()));
		System.out.println(
			getJaxpImplementationInfo(
				"TransformerFactory",
				TransformerFactory.newInstance().getClass()));
		System.out.println(
			getJaxpImplementationInfo(
				"SAXParserFactory",
				SAXParserFactory.newInstance().getClass()));
	}

	private static String getJaxpImplementationInfo(String name,Class clazz)
	{
		CodeSource source = clazz.getProtectionDomain().getCodeSource();
		return MessageFormat.format(
			"{0} implementation: {1} loaded from: {2}",
			name,
			clazz.getName(),
			source == null ? "Java Runtime" : source.getLocation());
	}

	public static void main(String... args) throws Exception
	{
		OutputJaxpImplementationInfo();
	}
}
