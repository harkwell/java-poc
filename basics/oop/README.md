Khallware (Java OOD/OOP Proof-of-Concepts)
=================
Overview
---------------
Object Oriented Design (OOD) is a software engineering paradigm that combines
data and methods to pattern real-world problems and concepts.  Object Oriented
Programming (OOP) constitutes how things in the OOD paradigm play together.
It's the way that objects communicate versus object dependencies in OOD.
In this case java is the OOP platform.  OOD can be reflected in UML or other
diagramming tools.

In this PoC, we model the natural bilological classification system.
Specifically, we look at a food crops (the potato and the tomato).

Building
---------------

```shell
cd /tmp/ && git clone https://github.com/harkwell/java-poc.git
cd java-poc/basics/oop && javac `find . -name \*.java`
echo 'Main-Class: com.khallware.life.Main' >manifest
jar cmvf manifest ../life.jar ./com
cd .. && rm -rf /tmp/oop/
java -jar life.jar
```
