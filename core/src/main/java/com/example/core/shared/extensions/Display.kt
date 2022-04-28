package com.example.core.shared.extensions

import android.content.res.Resources
import kotlin.math.roundToInt

object Display {
    @JvmStatic
    private val density by lazy { Resources.getSystem().displayMetrics.density }

    @JvmStatic
    private val densityDpi by lazy { Resources.getSystem().displayMetrics.densityDpi }

    @JvmStatic
    fun Int.dpToPx(): Int {
        return (this * density).roundToInt()
    }

    @JvmStatic
    fun Double.dpToPx(): Int {
        return (this * density).roundToInt()
    }

    @JvmStatic
    fun Int.pxToDp(): Float {
        return this / (densityDpi / 160f)
    }

    @JvmStatic
    fun Float.dpToPx(): Int {
        return (this * density).roundToInt()
    }

    @JvmStatic
    fun Float.pxToDp(): Float {
        return this / (densityDpi / 160f)
    }
}