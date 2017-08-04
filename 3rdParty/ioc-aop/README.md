IoC/AOP (inversion of control and aspect oriented programming)
=================
Overview
---------------
Aspectj by Xerox PARC empowers developers to apply code with cross-functioning
concerns to existing products without modifying the underlying code.

Build
---------------

```shell
POC_MAVEN_REPO=/tmp/foo
rm -rf $POC_MAVEN_REPO && mkdir -p $POC_MAVEN_REPO
mvn -Dmaven.repo.local=$POC_MAVEN_REPO package assembly:single
#rm -rf POC_MAVEN_REPO
```

Execute
---------------

```shell
java -jar target/ioc-0.1-jar-with-dependencies.jar
```
