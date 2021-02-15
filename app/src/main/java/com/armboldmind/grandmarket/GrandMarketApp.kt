package com.armboldmind.grandmarket

import android.app.Application

class GrandMarketApp : Application() {
    companion object {
        private lateinit var mInstance: GrandMarketApp
        fun getInstance(): GrandMarketApp {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }
}