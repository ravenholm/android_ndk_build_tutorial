//
// Created by ubuntu on 30/06/20.
//
#include<jni.h>
#include<string.h>

jstring Java_com_example_androidndktutorial_MainActivity_helloWorld(JNIEnv* env, jobject obj){

return (*env)->NewStringUTF(env, "Hello World");

}
