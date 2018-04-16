Apache Shiro
=================
Overview
---------------
Shiro provides a framework for role based authorization in java.  The
footprint is pretty heavy with over 900 classes.

```shell
chromium-browser https://shiro.apache.org/
```

Build
---------------

```shell
SHIRO_MAVEN_REPO=/tmp/foo
rm -rf $SHIRO_MAVEN_REPO
mvn -Dmaven.repo.local=$SHIRO_MAVEN_REPO package assembly:single
```

Run
---------------

```shell
cat <<'EOF' >/tmp/shiro.ini
[users]
khall = khall, teacher
foo1 = foo1, grade0
foo2 = foo2, grade1
foo3 = foo3, grade2

[roles]
teacher = *
grade0 = addition
grade1 = subtraction
grade1 = multiplication, division
EOF
java -jar target/myshiro-poc-0.1.jar khall khall add 1 4 6 4
```
