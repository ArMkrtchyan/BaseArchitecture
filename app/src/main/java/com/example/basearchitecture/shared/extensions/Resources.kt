package com.example.basearchitecture.shared.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

object Resources {
    @JvmStatic
    fun Context.getColorCompat(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

    @JvmStatic
    fun Context.getColorStateListCompat(@ColorRes resId: Int) = ContextCompat.getColorStateList(this, resId)

    @JvmStatic
    fun Context.getDrawableCompat(@DrawableRes resId: Int) = ResourcesCompat.getDrawable(resources, resId, theme)

    @JvmStatic
    fun Context.getDimensions(@DimenRes resId: Int) = resources.getDimension(resId)

    @JvmStatic
    fun Context.getBooleans(@BoolRes resId: Int) = resources.getBoolean(resId)

    @JvmStatic
    fun Context.getStyledAttribute(@AttrRes resId: Int): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(resId, typedValue, true)
        return typedValue.data
    }
}