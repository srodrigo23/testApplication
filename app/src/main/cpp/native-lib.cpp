#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_rodresdev_testapplication_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++ app";
    return env->NewStringUTF(hello.c_str());
}