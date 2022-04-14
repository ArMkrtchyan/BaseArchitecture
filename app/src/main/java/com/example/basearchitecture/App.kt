package com.example.basearchitecture

import android.app.Application
import com.example.exception_catcher.ExceptionHandler
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
    }
}