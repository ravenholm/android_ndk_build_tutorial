# Create an Android NDK build with a basic C/C++ method 
    This is a basic tutorial repo demonstrating a simple Java-to-C/C++ building on Android via the 
    "ndk-build" process on Android Development Studio 4 

# Downloading, configuring and the installing Android NDK
- Android NDK can be download in a couple of ways.
1. Method 1: Go to [NDK Downloads] (https://developer.android.com/ndk/downloads) and download the latest version
- Extract the files at a suitable place and
2. Method 2: Install via Android Development Studio's SDK manager
- Go to the SDK Manager, SDK Tools and then select the NDK Side-by-side option
- This will install the latest version of the NDK in Linux to the "/home/ubuntu/Android/Sdk/ndk/" location

Regardless of the method given above, you'll have to ensure the Android NDK is selected in your native project (explained later on)

- Now create an Android project with an empty activity MainActivity.java
- Go to File->Project Structure-> SDK Location and make sure the NDK location is selected
- This can also be changed by going to Project view -> local.properties and checking the following example entry is visible:
```java
ndk.dir=/home/ubuntu/Android/Sdk/ndk/21.3.6528147
```

# In your Project view, add a jni folder
- Add a ndktest.c C/C++ Resource file in this folder and update it as follows:
```objectivec
    #include<jni.h>
    #include<string.h>
    jstring Java_com_example_androidndktutorial_MainActivity_helloWorld(JNIEnv* env, jobject obj){
    return (*env)->NewStringUTF(env, "Hello World 123");
    }
```
Code explanation of ndktest.c: The native C function showing in this method has the following crucial elements:
- The method's name is basically the complete Java package of MainActivity.java start from the Java 
folder including the actual helloWorld method that we'll be calling
- This makes the entire route to be Java->com.example.androidndktutorial->MainActivity->helloWorld
- Hence, in ndktest.c, the method transforms to Java_com_example_androidndktutorial_MainActivity_helloWorld
- The method simply returns/sets a point to a string (*env)->NewStringUTF(env, "Hello World 123")
- This string is then mapped to the text property of the TextView control in the activity_main.xml file

- Create an Android.mk file as follows:
    The file has a very simple setup indicating the ndktest.c source file name which is to be built 
    by the ndk-build command
```objectivec
    LOCAL_PATH := $(call my-dir)
    
    include $(CLEAR_VARS)
    LOCAL_MODULE := ndktest
    LOCAL_SRC_FILES := ndktest.c
    
    include $(BUILD_SHARED_LIBRARY)
```

- Create an Application.mk file and at present keep it simple by only adding the following:
    ```objectivec
    APP_ABI := all
    APP_PLATFORM := android-16
    
    ```
  The APP_ABI := all indicate that this native built is expected to target all platforms
  
# Use the ndk-build now
- Go to the root folder of the Android project and enter the ndk-build command. This generates 
an output somewhat similar to the following:
```java
[arm64-v8a] Compile        : ndktest <= ndktest.c
[arm64-v8a] SharedLibrary  : libndktest.so
[arm64-v8a] Install        : libndktest.so => libs/arm64-v8a/libndktest.so
[x86_64] Compile        : ndktest <= ndktest.c
[x86_64] SharedLibrary  : libndktest.so
[x86_64] Install        : libndktest.so => libs/x86_64/libndktest.so
[armeabi-v7a] Compile thumb  : ndktest <= ndktest.c
[armeabi-v7a] SharedLibrary  : libndktest.so
[armeabi-v7a] Install        : libndktest.so => libs/armeabi-v7a/libndktest.so
[x86] Compile        : ndktest <= ndktest.c
[x86] SharedLibrary  : libndktest.so
[x86] Install        : libndktest.so => libs/x86/libndktest.so


```
- Now switch to the Project view and create a jniLibs folder in app/java/ location
- Copy the entire set of folders shown above (i.e. arm64-v8a, x86_64, armeabi-v7a and x86 
to this jniLibs folder)

# Preparing the MainActivity.java
- Go to the MainActivity.java and declare the following:
```java
    public native String helloWorld();
    static
    {
        System.loadLibrary("ndktest");

    }
```
This loads the related ndktest library as per our most recently moved build.
- Now switch to the activity_main.xml and add the following:
```xml
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/no_text_at_present"
        android:id="@+id/text_hello_world"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

- Now move to the onCreate method in the MainActivity.java and add the following line of code
```java
        ((TextView) findViewById(R.id.text_hello_world)).setText(helloWorld());
```
The code simply calls the native helloWorld() method which returns the hard-coded "Hello World" string
reference which is then displayed in the TextView

- Finally, we are ready to test our native Android C/C++ method by running our code which will 
eventually display the "Hello World" string the in the TextView control of the activity_main.xml file
