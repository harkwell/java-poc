JSR-356 (Java Web Sockets)
=================
Overview
---------------
Java Web Sockets basically turns HTTP into a stateful platform.  It allows
a client peer to initiate a session and allows synchronous communication
between peers until the sesion is terminated by one.

Build/Run
---------------

```shell
git clone https://gitlab.com/harkwell/java-poc.git
cd java-poc/j2ee/jsr356-websockets

# Server
POC_MAVEN_REPO=/tmp/foo
rm -rf $POC_MAVEN_REPO && mkdir -p $POC_MAVEN_REPO
cd server
mvn -Dmaven.repo.local=$POC_MAVEN_REPO package assembly:single
ls -ld target/websockets-poc-0.0.1-jar-with-dependencies.jar
java -jar target/websockets-poc-0.0.1-jar-with-dependencies.jar \
   localhost 8080 /khallware

# Client
cd client
POC_MAVEN_REPO=/tmp/foo2
rm -rf $POC_MAVEN_REPO && mkdir -p $POC_MAVEN_REPO
cd client
mvn -Dmaven.repo.local=$POC_MAVEN_REPO package assembly:single
ls -ld target/wsclient-poc-0.0.1-jar-with-dependencies.jar
java -jar target/wsclient-poc-0.0.1-jar-with-dependencies.jar \
          ws://localhost:8080/khallware/accumulator
```
