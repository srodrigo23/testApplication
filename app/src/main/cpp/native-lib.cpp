#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_rodresdev_testapplication_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string ip = "192.168.0.1";
    return env->NewStringUTF(ip.c_str());
}