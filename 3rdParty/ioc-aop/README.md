IoC/AOP (inversion of control and aspect oriented programming)
=================
Overview
---------------
Aspectj by Xerox PARC empowers developers to apply code with cross-functioning
concerns to existing products without modifying the underlying code.

Build
---------------

```shell
# compile-time weaving
POC_MAVEN_REPO=/tmp/foo
rm -rf $POC_MAVEN_REPO && mkdir -p $POC_MAVEN_REPO
mvn -Dmaven.repo.local=$POC_MAVEN_REPO package assembly:single
#rm -rf POC_MAVEN_REPO

# load-time weaving
mvn org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
    -Dmaven.repo.local=$POC_MAVEN_REPO \
    -DrepoUrl=https://mvnrepository.com/ \
    -Dartifact=org.aspectj:aspectjweaver:1.8.5

JAR1=$(find $POC_MAVEN_REPO -name aspectjrt\*jar)
JAR2=$(find $POC_MAVEN_REPO -name aspectjweaver\*jar)
sed -i -e 's#//KDH ##g' src/main/java/com/khallware/ioc/Main.java
javac -g -d /tmp -cp $JAR1:$JAR2 src/main/java/com/khallware/ioc/*java
```

Execute
---------------

```shell
java -jar target/ioc-0.1-jar-with-dependencies.jar

mkdir -p /tmp/META-INF/
cat <<-EOF >/tmp/META-INF/aop.xml
<aspectj>
    <aspects>
        <aspect name="com.khallware.ioc.MyAspect"/>
    </aspects>
</aspectj>
EOF

java -cp /tmp com.khallware.ioc.Main
java -cp /tmp:$JAR2 com.khallware.ioc.Main
java -javaagent:$JAR2 -cp /tmp com.khallware.ioc.Main
```
