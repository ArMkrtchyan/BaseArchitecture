package com.example.exception_catcher

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Process
import android.provider.Settings
import kotlin.system.exitProcess


object ExceptionHandler {

    lateinit var mApplication: Application

    fun init(application: Application) {
        mApplication = application
    }

    @SuppressLint("HardwareIds")
    @JvmStatic
    fun setExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            val pair = try {
                val pInfo: PackageInfo = mApplication.packageManager.getPackageInfo(mApplication.packageName, 0)
                Pair(pInfo.versionName, pInfo.versionCode)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                Pair("unavailable", 0)
            }

            val s = "throwable -> ${throwable.message}\n" +
                    "manufacture -> ${Build.MANUFACTURER}\n" +
                    "deviceModel -> ${Build.MODEL}\n" +
                    "versionName -> ${pair.first}\n" +
                    "versionCode -> ${pair.second}\n" +
                    "deviceModel -> ${Build.MODEL}\n" +
                    "androidVersionCode -> ${Build.VERSION.SDK_INT}\n" +
                    "deviceId -> ${Settings.Secure.getString(mApplication.contentResolver, Settings.Secure.ANDROID_ID)}\n" +
                    "threadName -> ${thread.name}\n" +
                    "stackTrace -> ${throwable.stackTraceToString()}"

            val model = ErrorModel()
            model.text = s
            model.manufacture = Build.MANUFACTURER
            model.deviceModel = Build.MODEL

            val stackTrace = throwable.cause?.stackTrace
            if (!stackTrace.isNullOrEmpty()) {
                model.className = stackTrace.firstOrNull()?.className
                model.crashLine = stackTrace.firstOrNull()?.lineNumber
            }
            val intent = Intent(mApplication, ExceptionActivity::class.java)
            intent.putExtra("model", model)
            intent.putExtra("packageName", mApplication.packageName)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            mApplication.startActivity(intent)
            Process.killProcess(Process.myPid())
            exitProcess(10)
        }
    }
}