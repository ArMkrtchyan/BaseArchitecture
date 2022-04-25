package com.example.basearchitecture

import android.app.Application
import com.example.basearchitecture.shared.gemalto.AppData
import com.example.exception_catcher.ExceptionHandler
import com.gemalto.idp.mobile.core.ApplicationContextHolder
import com.gemalto.idp.mobile.core.IdpCore
import com.gemalto.idp.mobile.otp.OtpConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    init {
        instance = this
    }


    override fun onCreate() {
        super.onCreate()
        ExceptionHandler.init(this)
        System.setProperty("java.io.tmpdir", getDir("files", MODE_PRIVATE).path)
        IdpCore.preLoad()
    }
}