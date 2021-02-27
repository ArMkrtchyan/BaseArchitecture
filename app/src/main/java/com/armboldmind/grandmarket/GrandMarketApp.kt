package com.armboldmind.grandmarket

import android.app.Application
import com.armboldmind.grandmarket.di.components.AppComponent
import com.armboldmind.grandmarket.di.components.DaggerAppComponent
import com.armboldmind.grandmarket.di.modules.AppModule
import com.armboldmind.grandmarket.di.modules.NetworkModule

class GrandMarketApp : Application() {

    lateinit var mAppComponent: AppComponent

    companion object {
        private lateinit var mInstance: GrandMarketApp
        fun getInstance(): GrandMarketApp {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        mAppComponent = DaggerAppComponent.builder().appModule(AppModule(this)).networkModule(NetworkModule()).build()
    }
}