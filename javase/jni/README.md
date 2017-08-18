JNI - Java Native Interface
=================
Overview
---------------
JNI allows one to include code written in other languages.  The javah utility
generates a C header file (ie "javah -jini -cp $CLASSPATH Main").

Build
---------------

```shell
CFLAGS="-g -I/opt/java/include -I/opt/java/include/linux"
CFLAGS="$CFLAGS -I$(dirname `rpm -ql java-1.8.0-openjdk-devel |grep jni.h`)"
CFLAGS="$CFLAGS -I$(dirname `rpm -ql java-1.8.0-openjdk-devel |grep jni_md.h`)"
gcc -c $CFLAGS -fPIC my-ext-lib.c
gcc $CFLAGS -o libmy-ext-lib.so -shared -Wl,-soname,my-ext-lib.so my-ext-lib.o -lc
javac Main.java
java -Djava.library.path=. Main
```
