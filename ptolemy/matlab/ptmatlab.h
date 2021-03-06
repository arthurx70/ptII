/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class ptolemy_matlab_Engine */

#ifndef _Included_ptolemy_matlab_Engine
#define _Included_ptolemy_matlab_Engine
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabEngOpen
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_ptolemy_matlab_Engine_ptmatlabEngOpen
  (JNIEnv *, jobject, jstring);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabEngClose
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_ptolemy_matlab_Engine_ptmatlabEngClose
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabEngEvalString
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_ptolemy_matlab_Engine_ptmatlabEngEvalString
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabEngGetArray
 * Signature: (JLjava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_ptolemy_matlab_Engine_ptmatlabEngGetArray
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabEngPutArray
 * Signature: (JLjava/lang/String;J)I
 */
JNIEXPORT jint JNICALL Java_ptolemy_matlab_Engine_ptmatlabEngPutArray
  (JNIEnv *, jobject, jlong, jstring, jlong);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabEngOutputBuffer
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_ptolemy_matlab_Engine_ptmatlabEngOutputBuffer
  (JNIEnv *, jobject, jlong, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabCreateCellMatrix
 * Signature: (Ljava/lang/String;II)J
 */
JNIEXPORT jlong JNICALL Java_ptolemy_matlab_Engine_ptmatlabCreateCellMatrix
  (JNIEnv *, jobject, jstring, jint, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabCreateString
 * Signature: (Ljava/lang/String;Ljava/lang/String;II)J
 */
JNIEXPORT jlong JNICALL Java_ptolemy_matlab_Engine_ptmatlabCreateString
  (JNIEnv *, jobject, jstring, jstring, jint, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabCreateDoubleMatrixOneDim
 * Signature: (Ljava/lang/String;[DI)J
 */
JNIEXPORT jlong JNICALL Java_ptolemy_matlab_Engine_ptmatlabCreateDoubleMatrixOneDim
  (JNIEnv *, jobject, jstring, jdoubleArray, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabCreateDoubleMatrix
 * Signature: (Ljava/lang/String;[[DII)J
 */
JNIEXPORT jlong JNICALL Java_ptolemy_matlab_Engine_ptmatlabCreateDoubleMatrix
  (JNIEnv *, jobject, jstring, jobjectArray, jint, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabCreateComplexMatrixOneDim
 * Signature: (Ljava/lang/String;[Lptolemy/math/Complex;I)J
 */
JNIEXPORT jlong JNICALL Java_ptolemy_matlab_Engine_ptmatlabCreateComplexMatrixOneDim
  (JNIEnv *, jobject, jstring, jobjectArray, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabCreateComplexMatrix
 * Signature: (Ljava/lang/String;[[Lptolemy/math/Complex;II)J
 */
JNIEXPORT jlong JNICALL Java_ptolemy_matlab_Engine_ptmatlabCreateComplexMatrix
  (JNIEnv *, jobject, jstring, jobjectArray, jint, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabCreateStructMatrix
 * Signature: (Ljava/lang/String;[Ljava/lang/Object;II)J
 */
JNIEXPORT jlong JNICALL Java_ptolemy_matlab_Engine_ptmatlabCreateStructMatrix
  (JNIEnv *, jobject, jstring, jobjectArray, jint, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabDestroy
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_ptolemy_matlab_Engine_ptmatlabDestroy
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabGetCell
 * Signature: (JII)J
 */
JNIEXPORT jlong JNICALL Java_ptolemy_matlab_Engine_ptmatlabGetCell
  (JNIEnv *, jobject, jlong, jint, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabGetClassName
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ptolemy_matlab_Engine_ptmatlabGetClassName
  (JNIEnv *, jobject, jlong);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabGetDimensions
 * Signature: (J)[I
 */
JNIEXPORT jintArray JNICALL Java_ptolemy_matlab_Engine_ptmatlabGetDimensions
  (JNIEnv *, jobject, jlong);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabGetComplexMatrix
 * Signature: (JII)[[Lptolemy/math/Complex;
 */
JNIEXPORT jobjectArray JNICALL Java_ptolemy_matlab_Engine_ptmatlabGetComplexMatrix
  (JNIEnv *, jobject, jlong, jint, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabGetDoubleMatrix
 * Signature: (JII)[[D
 */
JNIEXPORT jobjectArray JNICALL Java_ptolemy_matlab_Engine_ptmatlabGetDoubleMatrix
  (JNIEnv *, jobject, jlong, jint, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabGetLogicalMatrix
 * Signature: (JII)[[I
 */
JNIEXPORT jobjectArray JNICALL Java_ptolemy_matlab_Engine_ptmatlabGetLogicalMatrix
  (JNIEnv *, jobject, jlong, jint, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabGetFieldNameByNumber
 * Signature: (JI)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ptolemy_matlab_Engine_ptmatlabGetFieldNameByNumber
  (JNIEnv *, jobject, jlong, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabGetFieldByNumber
 * Signature: (JIII)J
 */
JNIEXPORT jlong JNICALL Java_ptolemy_matlab_Engine_ptmatlabGetFieldByNumber
  (JNIEnv *, jobject, jlong, jint, jint, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabGetNumberOfFields
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_ptolemy_matlab_Engine_ptmatlabGetNumberOfFields
  (JNIEnv *, jobject, jlong);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabGetString
 * Signature: (JI)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ptolemy_matlab_Engine_ptmatlabGetString
  (JNIEnv *, jobject, jlong, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabGetOutput
 * Signature: (JI)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ptolemy_matlab_Engine_ptmatlabGetOutput
  (JNIEnv *, jobject, jlong, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabIsComplex
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_ptolemy_matlab_Engine_ptmatlabIsComplex
  (JNIEnv *, jobject, jlong);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabSetCell
 * Signature: (Ljava/lang/String;JIIJ)V
 */
JNIEXPORT void JNICALL Java_ptolemy_matlab_Engine_ptmatlabSetCell
  (JNIEnv *, jobject, jstring, jlong, jint, jint, jlong);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabSetString
 * Signature: (Ljava/lang/String;JILjava/lang/String;I)V
 */
JNIEXPORT void JNICALL Java_ptolemy_matlab_Engine_ptmatlabSetString
  (JNIEnv *, jobject, jstring, jlong, jint, jstring, jint);

/*
 * Class:     ptolemy_matlab_Engine
 * Method:    ptmatlabSetStructField
 * Signature: (Ljava/lang/String;JLjava/lang/String;IIJ)V
 */
JNIEXPORT void JNICALL Java_ptolemy_matlab_Engine_ptmatlabSetStructField
  (JNIEnv *, jobject, jstring, jlong, jstring, jint, jint, jlong);

#ifdef __cplusplus
}
#endif
#endif
/* Header for class ptolemy_matlab_Engine_ConversionParameters */

#ifndef _Included_ptolemy_matlab_Engine_ConversionParameters
#define _Included_ptolemy_matlab_Engine_ConversionParameters
#ifdef __cplusplus
extern "C" {
#endif
#ifdef __cplusplus
}
#endif
#endif
