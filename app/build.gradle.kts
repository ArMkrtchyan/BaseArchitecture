import org.jetbrains.kotlin.kapt.cli.main

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt") //  id("dagger.hilt.android.plugin")
    //  id("androidx.navigation.safeargs")
    //  id("com.google.firebase.crashlytics")
}
android {
    compileSdk = AppConfigs.COMPILE_VERSION

    defaultConfig {
        applicationId = AppConfigs.APPLICATION_ID
        minSdk = AppConfigs.MINIMUM_SDK
        targetSdk = AppConfigs.TARGET_SDK
        versionCode = AppConfigs.VERSION_CODE
        versionName = AppConfigs.VERSION_NAME
        testInstrumentationRunner = AppConfigs.ANDROID_JUNIT_RUNNER

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }

            debug {

            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        kotlinOptions {
            jvmTarget = "11"
        }
        buildFeatures {
            dataBinding = true
            viewBinding = true
        }
//
//        sourceSets {
//            main {
//                jniLibs.srcDirs += ['libs']
//            }
//            getByName("debug"){
//                jniLibs.srcDirs += ["$ezioSdkDistDir/debug"]
//            }
//            getByName("release"){
//                jniLibs.srcDirs += ["$ezioSdkDistDir/release"]
//            }
//        }
//
//        project.exec {
//            ezioSdkDistDir = "$projectDir/libs"
//        }
    }
}
dependencies {
    implementation(dependency = Dependencies.androidXDependencies[Dependencies.APP_COMPAT]!!)
    implementation(dependency = Dependencies.androidXDependencies[Dependencies.CORE]!!)
    implementation(dependency = Dependencies.androidXDependencies[Dependencies.CONSTRAINT_LAYOUT]!!)
    implementation(dependency = Dependencies.androidXDependencies[Dependencies.SUPPORT]!!)
    implementation(dependency = Dependencies.androidXDependencies[Dependencies.ACTIVITY_KTX]!!)
    implementation(dependency = Dependencies.androidXDependencies[Dependencies.FRAGMENT_KTX]!!)
}

//dependencies {
//
//    debugImplementation fileTree(dir: "$ezioSdkDistDir/debug", include: ['*.jar'])
//    releaseImplementation fileTree(dir: "$ezioSdkDistDir/release", include: ['*.jar'])
//
//    //sdk's dependencies
//    implementation 'com.madgag.spongycastle:pkix:1.54.0.0'
//    implementation 'net.java.dev.jna:jna:5.5.0@aar'
//
//
//    implementation 'com.madgag.spongycastle:core:1.58.0.0'
//    implementation 'com.madgag.spongycastle:prov:1.58.0.0'
//    implementation 'com.madgag.spongycastle:pg:1.54.0.0'
//    implementation 'androidx.multidex:multidex:2.0.1'
//    implementation 'androidx.biometric:biometric:1.1.0'
//    implementation fileTree(dir: 'libs', include: ['*.jar'])
//    implementation project(':domain')
//    implementation project(':exception_catcher')
//
//    implementation androidXDependencies.appcompat
//    implementation androidXDependencies.core
//    implementation androidXDependencies.constraintlayout
//    implementation androidXDependencies.support
//    implementation androidXDependencies.activity
//    implementation androidXDependencies.fragment
//    implementation thirthPartyDependencies.ccp
//    implementation thirthPartyDependencies.keyboardVisibilityEvent
//    kapt kaptDependencies.hilt
//    kapt kaptDependencies.glide
//
//    for (dep in hiltDependencies) {
//        implementation dep
//    }
//    for (dep in liveDataVMDependencies) {
//        implementation dep.value
//    }
//    for (dep in googleDependencies) {
//        implementation dep
//    }
//    for (dep in firebaseDependencies) {
//        implementation dep
//    }
//    for (dep in navigationDependencies) {
//        implementation dep
//    }
//    for (dep in coroutineDependencies) {
//        implementation dep
//    }
//    for (dep in mediaDependencies) {
//        implementation dep
//    }
//    for (dep in testDependencies) {
//        implementation dep.value
//    }
//}