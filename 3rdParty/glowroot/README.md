Glowroot
=================
Overview
---------------
A java agent to collect metrics and statistics on web applications.

```shell
wget -c 'https://github.com/glowroot/glowroot/releases/download/v0.9.25/glowroot-0.9.25-dist.zip' -O /tmp/glowroot.zip
unzip -j -d /tmp /tmp/glowroot.zip glowroot/glowroot.jar
git clone https://github.com/harkwell/java-poc.git
javac -d /tmp java-poc/basics/echo_server/Main.java
echo 'Main-Class: com.khallware.poc.echosvr.Main' >/tmp/manifest
jar cmvf /tmp/manifest /tmp/echosvr-poc.jar -C /tmp com
rm -rf /tmp/com /tmp/manifest
java -javaagent:/tmp/glowroot.jar -jar /tmp/echosvr-poc.jar 7777 2>/dev/null
chromium-browser http://localhost:4000
```
