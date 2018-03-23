Lagom Microservices Framework
=================
Overview
---------------
Lagom is an open source framework for building reactive microservice systems in
Java or Scala.  As with any framework, quite a bit of effort goes into learning
the conventions and care must be made when binding yourself -and others- to
one.  In general, if one does not leverage over 50% of the framework and other
libraries will suffice, why burden yourself?

Hello World
---------------

```
chromium-browser https://github.com/lagom/lagom
POC_MAVEN_REPO=/tmp/foo
rm -rf $POC_MAVEN_REPO
mvn archetype:generate -Dmaven.repo.local=$POC_MAVEN_REPO -Dfilter=com.lightbend.lagom:maven-archetype-lagom-java
# 1
# enter (31: 1.4.1)
# com.khallware.poc.lagom
# blah
# enter (1.0-SNAPSHOT)
# enter com.khallware.poc.lagom
# enter Y
cd blah && mvn -Dmaven.repo.local=$POC_MAVEN_REPO lagom:runAll
curl http://localhost:9000/api/hello/World
```
