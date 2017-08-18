#include "Main.h"
#include "jni.h"
#include "stdio.h"

JNIEXPORT void JNICALL Java_Main_runMe(JNIEnv *env, jobject obj)
{
	printf("Khallware JNI says hello\n");
	return;
}
