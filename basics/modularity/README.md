Modularity
=================
Overview
---------------
Java 9 modularity implements concepts such as: strong encapsulation
(internals are hidden, the public api is strictly expressed), well defined
interfaces (for stability of module interaction), and explicit dependencies
(modules enumerate all requirements).

Build
---------------

```shell
git clone https://github.com/harkwell/java-poc.git
cd java-poc/basics/modularity
javac -version # make sure we're at java9
javac -d /tmp/mods/ module-info.java Main.java
```

Run
---------------

```shell
#java -cp /tmp/mods com.khallware.firearm.Main # without modules

java -version # make sure we're at java9
java --list-modules --module-path /tmp/mods |grep khallware
java --module-path /tmp/mods -m khallware.example.firearm/com.khallware.firearm.Main # with modules
```
