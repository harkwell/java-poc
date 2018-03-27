Activi Workflow
=================
Overview
---------------
The Activi library is a workflow engine that provides new and existing
software with Business Process Management (BPM).  Activiti supports open
standards such as BPMN and DMN with open REST APIs for the most demanding
human- and system-centric processes.  Leverage any BPM XML editor to create
the relevant workflow, add hooks specific to the application, then
integrate it into your code with the activi libraries simply by including
it as a classpath resource and then referencing it via activi API calls.

```
chromium-browser https://www.activiti.org/ https://bpmn.io/ http://www.omg.org/spec/BPMN/2.0/
```

Build
---------------

```
POC_MAVEN_REPO=/tmp/foo
rm -rf $POC_MAVEN_REPO
mvn -Dmaven.repo.local=$POC_MAVEN_REPO package
ls -ld target/activi-poc-0.0.1.jar
CLASSPATH=$CLASSPATH:$POC_MAVEN_REPO/org/hsqldb/hsqldb/2.4.0/hsqldb-2.4.0.jar
java -jar target/activi-poc-0.0.1-jar-with-dependencies.jar
```
