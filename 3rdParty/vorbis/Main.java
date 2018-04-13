package com.khallware.poc.vorbis;

import org.gagravarr.ogg.OggFile;
import org.gagravarr.ogg.OggPacketReader;
import org.gagravarr.vorbis.VorbisPacketFactory;
import org.gagravarr.vorbis.VorbisComments;
import org.gagravarr.vorbis.VorbisPacket;
import java.io.FileInputStream;

/**
 * A Proof of Concept for manipulating ogg/vorbis files with vorbisJava lib.
 *
 * BUILD:
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *     org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *     -DrepoUrl=https://mvnrepository.com/ \
 *     -Dartifact=org.gagravarr:vorbis-java-core:0.8
 *
 * VORBISJAR=$(find $POC_MAVEN_REPO -name \*vorbis\*jar)
 * javac -d /tmp -cp $VORBISJAR Main.java
 * echo 'Main-Class: com.khallware.poc.vorbis.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/vorbis-poc.jar -C /tmp com
 * rm -rf /tmp/com /tmp/manifest
 * java -cp $VORBISJAR:/tmp/vorbis-poc.jar com.khallware.poc.vorbis.Main \
 *      myvorbis.ogg
 * rm -rf /tmp/vorbis-poc.jar $POC_MAVEN_REPO
 *
 */
public class Main
{
	public static void dumpComments(VorbisComments vcomments)
	{
		System.out.printf("title: %s\n", vcomments.getTitle());
		System.out.printf("artist: %s\n", vcomments.getArtist());
		System.out.printf("genre: %s\n", vcomments.getGenre());
		System.out.printf("album: %s\n", vcomments.getAlbum());
	}

	public static void main(String... args) throws Exception
	{
		OggFile oggfile = new OggFile(new FileInputStream(args[0]));
		OggPacketReader reader = oggfile.getPacketReader();
		reader.getNextPacket();
		VorbisPacket vpacket = VorbisPacketFactory.create(
			reader.getNextPacket());
		Main.dumpComments((VorbisComments)vpacket);
		oggfile.close();
	}
}
