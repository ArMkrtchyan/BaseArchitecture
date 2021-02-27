package com.armboldmind.grandmarket.shared.globalextensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import com.armboldmind.grandmarket.GrandMarketApp
import com.armboldmind.grandmarket.base.BaseActivity
import com.armboldmind.grandmarket.base.BaseFragment
import com.armboldmind.grandmarket.base.BaseViewModel
import com.armboldmind.grandmarket.shared.managers.NetworkStatusManager

fun Context.inflater(): LayoutInflater = LayoutInflater.from(this)


fun Context.createIntentToApplicationPackage(): Intent {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    return intent
}


infix fun Context.callTo(number: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: $number"))
    startActivity(intent)
}

fun BaseActivity<*>.applicationContext() = GrandMarketApp.getInstance().applicationContext
fun BaseViewModel.applicationContext() = GrandMarketApp.getInstance().applicationContext
fun BaseFragment<*>.applicationContext() = GrandMarketApp.getInstance().applicationContext
fun NetworkStatusManager.applicationContext() = GrandMarketApp.getInstance().applicationContext