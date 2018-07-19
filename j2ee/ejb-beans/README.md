Enterprise Java Beans (EJB)
=================
Overview
---------------
A java bean is a java class that implements an interface that is annotated with 
either javax.ejb.Remote, or javax.ejb.Local and is itself annotated with
one of: javax.ejb.Stateless, javax.ejb.Stateful, javax.ejb.Singleton or
javax.ejb.MessageDriven.  Beans exist only within an EJB container (similar to
servlets).

Build Beans
---------------

```shell
# install OpenEJB
# chromium-browser http://archive.apache.org/dist/openejb/3.0/
wget -c http://archive.apache.org/dist/openejb/3.0/openejb-3.0.tar.gz -O /tmp/openejb.tgz
sudo tar zxvpf /tmp/openejb.tgz -C /usr/local --transform=s#openejb-3.0#openejb#

export OPENEJB_HOME=/usr/local/openejb/
sudo chmod -R go+r $OPENEJB_HOME
sudo chmod 777 $OPENEJB_HOME/conf/
sudo mkdir -f $OPENEJB_HOME/txlog $OPENEJB_HOME/logs $OPENEJB_HOME/apps
sudo chmod 777 $OPENEJB_HOME/txlog $OPENEJB_HOME/logs $OPENEJB_HOME/apps
git clone https://gitlab.com/harkwell/java-poc.git && cd java-poc/ejb-beans
javac -cp $OPENEJB_HOME/lib/javaee-api-5.0-1.jar -d . MyBeans.java
jar cvf mybeans.jar com && rm -rf com/
```

Deploy Beans
---------------

```shell
screen
$OPENEJB_HOME/bin/openejb start
# ctrl-a c
$OPENEJB_HOME/bin/openejb deploy mybeans.jar
```

Build/Run Client
---------------

```shell
javac -cp mybeans.jar Main.java
java -cp $OPENEJB_HOME/lib/openejb-client-3.0.jar:$OPENEJB_HOME/lib/javaee-api-5.0-1.jar:mybeans.jar:`pwd` Main
telnet localhost 4202
system
exit
$OPENEJB_HOME/bin/openejb undeploy $OPENEJB_HOME/apps/mybeans.jar
java -cp $OPENEJB_HOME/lib/openejb-client-3.0.jar:$OPENEJB_HOME/lib/javaee-api-5.0-1.jar:mybeans.jar:`pwd` Main
```
