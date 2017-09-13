package com.khallware.poc.apache.text;

import org.apache.commons.text.similarity.FuzzyScore;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.StrBuilder;
import org.apache.commons.text.WordUtils;
import java.util.ArrayList;
import java.util.Locale;

/**
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=url \
 *     -Dartifact=org.apache.commons:commons-text:1.1
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=url \
 *     -Dartifact=org.apache.commons:commons-lang3:3.6
 * APACHE_JAR=$(find $POC_MAVEN_REPO -name \*text\*jar)
 * JAR1=$(find $POC_MAVEN_REPO -name \*lang\*jar)
 * javac -cp $APACHE_JAR:$JAR1 -d /tmp Main.java
 * echo 'Main-Class: com.khallware.poc.apache.text.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/commons-text-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -cp $APACHE_JAR:$JAR1:/tmp/commons-text-poc.jar \
 *      com.khallware.poc.apache.text.Main
 * rm -rf /tmp/commons-text-poc.jar $POC_MAVEN_REPO
 *
 */
public class Main
{
	public static void showcaseRandomStringGenerator()
			throws IllegalArgumentException
	{
		RandomStringGenerator generator = null;
		generator = new RandomStringGenerator.Builder()
			.withinRange('A', 'z')
			.build();
		System.out.printf("apache-text can generate random strings "
			+"like: %s\n", generator.generate(30));
	}

	public static void showcaseStrBuilder()
	{
		StrBuilder sb = new StrBuilder();
		Object obj1 = new String("first token");
		ArrayList obj2 = new ArrayList<String>();
		obj2.add("col1");
		obj2.add("col2");
		sb.append("     StrBuilder can handle doubles (")
			.append(0.2d).append(") ");
		sb.append("and string objects (").append(obj1).append(") ");
		sb.appendln("newline here");
		sb.appendln(obj2);

		if (sb.contains("handle")) {
			sb.appendln("sb contains \"handle\"");
		}
		sb.appendAll(new String[] { "foo", "bar", "baz" });
		sb.appendNewLine();
		sb.insert(0, "handle");
		sb.deleteFirst("handle");
		sb.append("sb is now ").append(sb.length()).appendln(" chars");
		sb.replaceAll("handle","HANDLE");
		sb.trim();
		System.out.printf("%s\n", sb.toString());
	}

	public static void showcaseStringEscapeUtils()
	{
		String json = "{ 'name' : 'foo', 'desc' : 'bar' }";
		String html = "<html><body><ul><li>foo<li>bar<li>baz</ul>"
			+"</body></html>";
		String csv = "abc,def,ghi,jkl,mno,pqr,stu,vwx,yz";
		String xml = "<tag1>foo</tag1>";
		json = json.replaceAll("'","\"");
		System.out.printf("json %s encodes to %s\n",
			json, StringEscapeUtils.escapeJson(json));
		System.out.printf("...and back to %s\n",
			StringEscapeUtils.unescapeJson(
				StringEscapeUtils.escapeJson(json)));
		System.out.printf("html %s encodes to %s\n",
			html, StringEscapeUtils.escapeHtml4(html));
		System.out.printf("...and back to %s\n",
			StringEscapeUtils.unescapeHtml4(
				StringEscapeUtils.escapeHtml4(html)));
		System.out.printf("csv %s encodes to %s\n",
			csv, StringEscapeUtils.escapeCsv(csv));
		System.out.printf("...and back to %s\n",
			StringEscapeUtils.unescapeCsv(
				StringEscapeUtils.escapeCsv(csv)));
		System.out.printf("xml %s encodes to %s\n",
			xml, StringEscapeUtils.escapeXml11(xml));
		System.out.printf("... and back to %s\n",
			StringEscapeUtils.unescapeXml(
				StringEscapeUtils.escapeXml11(xml)));
	}

	public static void showcaseWordUtils()
	{
		StrBuilder sb = new StrBuilder();
		sb.append("Sockets  of type SOCK_STREAM are full-duplex ");
		sb.append("byte streams.  They do not preserve record ");
		sb.append("boundaries.  A stream socket must  be  in a  ");
		sb.append("connected state  before  any data may be ");
		sb.append("sent or received on it.  A connection to ");
		sb.append("another socket is created with a connect(2) ");
		sb.append("call.  Once connected, data may  be ");
		sb.append("transferred using read(2) and write(2) calls ");
		sb.append("or some variant of the send(2) and recv(2) ");
		sb.append("calls.  When a session  has  been  completed ");
		sb.append(" a close(2) may be performed.  Out-of-band ");
		sb.append("data may also be transmitted as described in ");
		sb.append("send(2) and received as described in recv(2).");
		System.out.printf("Abbreviate:\n%s\n",
			WordUtils.abbreviate(""+sb, 50, -1, "..."));
		System.out.printf("Wrapped:\n%s\n",
			WordUtils.wrap(""+sb, 65, "\n", true));
		System.out.printf("Initials:\n%s\n",
			WordUtils.initials("Kevin D Hall"));
		System.out.printf("Capitalization:\n%s\n",
			WordUtils.capitalize("kevin d.hall"));
		System.out.printf("Case:\n%s\n", WordUtils.swapCase("kEVIN"));
	}

	public static void showcaseFuzzyScore()
	{
		FuzzyScore fs = new FuzzyScore(Locale.US);
		String s1 = "Character is like a tree and reputation like its "
			+"shadow. The shadow is what we think of it; the tree "
			+"is the real thing. - Abraham Lincoln";
		String s2 = "String(char) x = new Btree(); reputation extends "
			+"Btree. Shadow s = thought(x); (Btree)s; - ln -s";
		String s3 = "Reputation is only what we think of character.";
		System.out.printf("s2 scores %d against s1\n",
			fs.fuzzyScore(s1, s2));
		System.out.printf("s3 scores %d against s1\n",
			fs.fuzzyScore(s1, s3));
	}

	public static void main(String... args) throws Exception
	{
		showcaseRandomStringGenerator();
		showcaseStrBuilder();
		showcaseStringEscapeUtils();
		showcaseWordUtils();
		showcaseFuzzyScore();
	}
}
