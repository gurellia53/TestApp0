#include <jni.h>
#include <string>
#include "native-lib.h"

// JNI function to clear inputs to the calculator
extern "C" JNIEXPORT void JNICALL
Java_gurik_andrew_testapp0_MainActivity_gscClearInputValuesJNI(JNIEnv *env, jobject /* this */)
{
    gscClearDfltInputs();
    return;
}

// JNI function to set inputs to the calculator
extern "C" JNIEXPORT void JNICALL
Java_gurik_andrew_testapp0_MainActivity_gscSetInputValuesJNI(JNIEnv *env, jobject /* this */, jdouble i_W, jdouble i_w, jdouble i_R, jdouble i_A_tsa, jdouble i_S, jdouble i_k, jdouble i_u, jdouble i_o, jdouble i_y1, jdouble i_y2, jdouble i_y3)
{
    gscSetDfltInputs((double)i_W, (double)i_w, (double)i_R, (double)i_A_tsa, (double)i_S, (double)i_k, (double)i_u, (double)i_o, (double)i_y1, (double)i_y2, (double)i_y3);
    return;
}

// JNI function to calculate F_extraction and return this value as a string
extern "C" JNIEXPORT jstring JNICALL
Java_gurik_andrew_testapp0_MainActivity_gscCalcJNI(JNIEnv *env, jobject /* this */)
{
    // convert the calculated double to a string
    std::string hello = std::to_string(gscDfltCalculate());
    //std::string hello = std::to_string(input_vals.W + input_vals.w + input_vals.R);

    // return the string
    return env->NewStringUTF(hello.c_str());
}
