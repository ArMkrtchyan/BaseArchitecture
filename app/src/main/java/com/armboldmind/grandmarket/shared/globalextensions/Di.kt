package com.armboldmind.grandmarket.shared.globalextensions

import com.armboldmind.grandmarket.GrandMarketApp
import com.armboldmind.grandmarket.base.BaseViewModel
import com.armboldmind.grandmarket.di.components.AppComponent

fun BaseViewModel.appComponent(): AppComponent = GrandMarketApp.getInstance().mAppComponent