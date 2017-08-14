Hibernate (database framework)
=================
Overview
---------------

```shell
chromium-browser http://hibernate.org/
```

Build
---------------

```shell
git clone https://github.com/harkwell/java-poc.git
cd java-poc/3rdParty/hibernate
POC_MAVEN_REPO=/tmp/foo
rm -rf $POC_MAVEN_REPO && mkdir -p $POC_MAVEN_REPO
mvn -Dmaven.repo.local=$POC_MAVEN_REPO package assembly:single
ls -ld target/hibernate-poc-0.0.1-jar-with-dependencies.jar
```

Execute
---------------

```shell
CLASSPATH=$POC_MAVEN_REPO/org/hsqldb/hsqldb/2.3.2/hsqldb-2.3.2.jar:$CLASSPATH

# start the database
java org.hsqldb.server.Server --database.0 file:test.db --dbname.0 test &

# insert items into database with poc (Main.java)
java -jar target/hibernate-poc-0.0.1-jar-with-dependencies.jar key1 val1

# test
henplus connect jdbc:hsqldb:file:$PWD/test.db
SELECT * FROM item;
exit
```
