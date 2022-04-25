# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-printusage
-printmapping


#Google play
-keep class com.google.android.gms.internal.* { *; }

#spongy castle
-keep class org.spongycastle.*.** {*;}
-dontwarn javax.naming.**

#Keeping line number
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

#HCE SDK
-dontwarn util.**
-dontwarn util.a.y.ai.a.**
-dontwarn util.d.a.**
-dontwarn com.util.**
-dontwarn sun.misc.**

-keep public class util.* { public protected *; }
-keepclasseswithmembernames class *  {
    native <methods>;
    }

#-keep class com.gemalto.idp.mobile.otp.OtpConfiguration
#-keep class com.gemalto.idp.mobile.core.IdpCore
#-keep class am.acba.acbamobile.application.ACBAApplication
#-keep class am.acba.acbamobile.application.ACBAApplicationConfigs
#-keep class com.gemalto.*
#-keep class util.*
#-keep class com.sun*
#-keep interface com.gemalto.*
#-keep interface util.*
#-keep enum util.*
#-keep enum com.gemalto.*
#-keep class com.gemalto.** { *; }
#-keep class util.** { *; }
#-keep class sun.misc.*
#-keep class sun.misc.** { *; }


#Add project specific ProGuard rules here.

# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html
# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable
# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-printusage
-printmapping
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*,!code/allocation/variable,!class/unboxing/enum
-optimizationpasses 1
-allowaccessmodification
-dontpreverify
-verbose
-flattenpackagehierarchy test.hcesdk.mpay
#Keeping line number
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
#Google play
-keep class com.google.android.gms.internal.** { *; }
#okhttp
-dontwarn com.squareup.okhttp.**
-dontwarn org.apache.http.**
#spongy castle
-keep class org.spongycastle.*.** {*;}
-dontwarn javax.naming.**
#JNA
-dontwarn com.sun.jna.**
-dontwarn com.fasterxml.**
#Others
-keep public class androidx.viewpager.widget.ViewPager {*;}
-keep class com.sun.jna.** {*;}
-keepclassmembers interface * extends com.sun.jna.Library {
    <methods>;
}
#HCE SDK
-dontwarn util.**
-dontwarn com.gemalto.mfs.mwsdk.**
#-keep public class util.** { public protected *; }
-keep class util.** { *; }
-keepclasseswithmembernames class * {
    native <methods>;
}
# Annotated interfaces
-keep public @interface com.gemalto.medl.MK {*;}
-keep public class util.m.rd { public *; }
-keep public class util.m.md { public *; }
-keep public class util.m.mj { public *; }
-keep public class util.d.a { public *; }
-keep public class com.gemalto.medl.rd {public *;}

-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.gson.** { *; }
-keep class com.fasterxml.** { *; }
-keep class sun.misc.Unsafe { *; }
-keep interface com.gemalto.mfs.mwsdk.** { *; }
-keep class com.gemalto.mfs.mwsdk.** { *; }
-keep enum com.gemalto.mfs.mwsdk.** { *; }
#-keep interface am.acba.acbamobile.ui.fragments.welcomeScreen.view.WelcomeScreenFragment { *; }

-keepclassmembers class am.acba.acbamobile.core.models.**{
  public *;
}
-keepclassmembers class am.acba.acbamobile.core.models.rate.*

-keepclassmembers,allowobfuscation class * {
@com.google.gson.annotations.SerializedName <fields>;
}




-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)


-keepattributes Signature
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keepattributes Annotation

-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keep class * implements java.util.Comparator


-keepclassmembers enum * { *; }




# Recommended package so to be consistent with Ezio SDK
# !!! it is extremely important to 'flatten' or to 'repackage' into package 'util',
# so to hide important packages that cannot be obfuscated further !!!
-flattenpackagehierarchy util

# Global Rules for all SDKs
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-renamesourcefileattribute SourceFile
-dontpreverify
-verbose
-dontoptimize

# for maintenance purposes, keep this files confidential
-dump class_files.txt


-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# EZIO Specific Rules
-optimizations !code/allocation/variable,!code/simplification/arithmetic,!field/*,!class/merging/*

#### ----- EZIO LIB
-keep class util.a.z.** {*;}
-keep class util.d.** {*;}
-keep class com.neurotec.** {*;}
-keep class com.sun.jna.** {*;}

# supress warning
-dontwarn util.a.z.**
-dontwarn com.sun.jna.**
-dontwarn com.neurotec.**
-dontwarn javax.naming.**

# supress notes
-dontnote com.gemalto.idp.mobile.**
-dontnote util.**
-dontnote android.net.**
-dontnote org.apache.**
# Suppress notes about dynamic referenced class used by PRNGFixes (not part of Android API)
-dontnote org.apache.harmony.xnet.provider.jsse.NativeCrypto

# native
-keepclasseswithmembernames class * {
    native <methods>;
}

# Global JNA Rules
-keep,allowobfuscation interface  com.sun.jna.Library

-keep,allowobfuscation interface  * extends com.sun.jna.Library

-keep,allowobfuscation interface  * extends com.sun.jna.Callback

-keepclassmembers interface  * extends com.sun.jna.Library {
    <methods>;
}

-keepclassmembers interface  * extends com.sun.jna.Callback {
    <methods>;
}

-keep class com.sun.jna.CallbackReference {
    void dispose();
    com.sun.jna.Callback getCallback(java.lang.Class,com.sun.jna.Pointer,boolean);
    com.sun.jna.Pointer getFunctionPointer(com.sun.jna.Callback,boolean);
    com.sun.jna.Pointer getNativeString(java.lang.Object,boolean);
    java.lang.ThreadGroup initializeThread(com.sun.jna.Callback,com.sun.jna.CallbackReference$AttachOptions);
}

-keep,includedescriptorclasses class com.sun.jna.Native {
    com.sun.jna.Callback$UncaughtExceptionHandler callbackExceptionHandler;
    void dispose();
    java.lang.Object fromNative(com.sun.jna.FromNativeConverter,java.lang.Object,java.lang.reflect.Method);
    com.sun.jna.NativeMapped fromNative(java.lang.Class,java.lang.Object);
    com.sun.jna.NativeMapped fromNative(java.lang.reflect.Method,java.lang.Object);
    java.lang.Class nativeType(java.lang.Class);
    java.lang.Object toNative(com.sun.jna.ToNativeConverter,java.lang.Object);
}

-keep class com.sun.jna.Callback$UncaughtExceptionHandler {
    void uncaughtException(com.sun.jna.Callback,java.lang.Throwable);
}

-keep class com.sun.jna.Native$ffi_callback {
    void invoke(long,long,long);
}

-keep class com.sun.jna.Structure$FFIType$FFITypes {
    <fields>;
}

-keep class com.sun.jna.ToNativeConverter {
    java.lang.Class nativeType();
}

-keep class com.sun.jna.NativeMapped {
    java.lang.Object toNative();
}

-keep class com.sun.jna.IntegerType {
    long value;
}

-keep class com.sun.jna.PointerType {
    com.sun.jna.Pointer pointer;
}

-keep class com.sun.jna.LastErrorException {
    <init>(int);
    <init>(java.lang.String);
}

-keep class com.sun.jna.Pointer {
    long peer;
    <init>(long);
}

-keep class com.sun.jna.WString {
    <init>(java.lang.String);
}

-keep class com.sun.jna.Structure {
    long typeInfo;
    com.sun.jna.Pointer memory;
    <init>(int);
    void autoRead();
    void autoWrite();
    com.sun.jna.Pointer getTypeInfo();
    com.sun.jna.Structure newInstance(java.lang.Class,long);
}

-keep class androidx.core.app.CoreComponentFactory { *; }