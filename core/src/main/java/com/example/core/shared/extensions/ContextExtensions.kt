package com.example.core.shared.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager

object ContextExtensions {
    @JvmStatic
    fun Context.inflater(): LayoutInflater = LayoutInflater.from(this)

    @JvmStatic
    fun ViewGroup.inflater(): LayoutInflater = LayoutInflater.from(this.context)

    @JvmStatic
    infix fun Context.callTo(number: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: $number"))
        startActivity(intent)
    }

    @JvmStatic
    fun Context.getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    @JvmStatic
    fun Context.getDisplayHeight(): Int {
        val displayMetrics = DisplayMetrics()
        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    @JvmStatic
    fun Context.getDisplayWidth(): Int {
        val displayMetrics = DisplayMetrics()
        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    @JvmStatic
    fun Context.getActionBarHeight(): Int {
        var actionBarHeight = 0
        val tv = TypedValue()
        if (this.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
        }
        return actionBarHeight
    }
}