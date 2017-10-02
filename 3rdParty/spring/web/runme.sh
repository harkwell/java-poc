#!/bin/bash

java -version 2>&1 |head -1 |grep 1.[6-9] 2>/dev/null || {
	echo java 6 or newer is required for spring roo
	exit 1
}
mvn -version 2>&1 |head -1 |grep ' 3.[3-9]' 2>/dev/null || {
	echo maven 3.3 or newer is required for spring roo
	exit 1
}
export ROO_HOME=/tmp/roosoft

POC_MAVEN_REPO=/tmp/foo
rm -rf $POC_MAVEN_REPO
mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
    org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
    -DrepoUrl=https://mvnrepository.com/ \
    -Dartifact=org.apache.felix:org.apache.felix.framework:5.6.8
FELIXJAR=$(find $POC_MAVEN_REPO -name \*felix\*jar)

[ ! -d $ROO_HOME ] && {
   wget -c 'http://spring-roo-repository.springsource.org.s3.amazonaws.com/milestone/ROO/spring-roo-2.0.0.RC2.zip' -O /tmp/roo.zip
   #wget -c 'http://spring-roo-repository.springsource.org.s3.amazonaws.com/release/ROO/spring-roo-1.3.2.RELEASE.zip' -O /tmp/roo.zip

   unzip /tmp/roo.zip -d /tmp/
   mv /tmp/spring-roo* /tmp/roosoft && rm /tmp/roo.zip
}
echo $PATH |grep roosoft >/dev/null || export PATH=$PATH:$ROO_HOME/bin
cp $FELIXJAR $ROO_HOME/bin/

roo.sh <recipes.roo

[ -f /usr/bin/eclipse ] && {
   echo 'formatting the generated java code..."
   eclipse -nosplash -verbose \
           -application org.eclipse.jdt.core.JavaCodeFormatter \
           -config ../org.eclipse.jdt.core.prefs \
           src/

}
mvn -Dmaven.repo.local=$POC_MAVEN_REPO package
ls -ld target/khallware-1.0.0.BUILD-SNAPSHOT-exec.jar && \
   echo java -jar target/khallware-1.0.0.BUILD-SNAPSHOT-exec.jar

cat <<EOF
This is a simple web application to manage food recipes.  To use it,
point your browser to: http://localhost:8080/ after running the jar file.

java -jar target/spring-1.0.0.BUILD-SNAPSHOT-exec.jar
chromium-browser http://localhost:8080/
curl http://localhost:8080/recipes
EOF
#rm -rf /tmp/roo*
