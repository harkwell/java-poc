JSF (Java Server Faces)
=================
Overview
---------------
JSF is tightly associated with java Servlets and must run under a servlet
container such as: jetty, tomcat or glassfish.  It provides client side
interaction (markup tags) with server components (beans).

Build
---------------

```shell
POC_MAVEN_REPO=/tmp/foo
rm -rf $POC_MAVEN_REPO
mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
    org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
    -DrepoUrl=https://mvnrepository.com/ \
    -Dartifact=org.eclipse.jetty:jetty-runner:9.4.6.v20170531

git clone https://gitlab.com/harkwell/java-poc.git
cd java-poc/jsf-faces && mvn -Dmaven.repo.local=$POC_MAVEN_REPO package
ls -ld target/jsf-poc-0.0.1.war
```

Execute
---------------

```shell
JETTY_JAR=$POC_MAVEN_REPO/org/eclipse/jetty/jetty-runner/9.4.6.v20170531/jetty-runner-9.4.6.v20170531.jar
java -jar $JETTY_JAR --port 8080 --path /foo target/jsf-poc-0.0.1.war
curl -i -X GET http://localhost:8080/foo/index.xhtml
```
