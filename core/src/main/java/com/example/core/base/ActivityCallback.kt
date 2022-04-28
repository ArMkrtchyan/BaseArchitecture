package com.example.core.base

import androidx.annotation.StringRes

interface ActivityCallback {
    fun showServerError(message: Throwable, onRetryClick: (() -> Unit)?)
    fun showToast(message: String)
    fun showToast(@StringRes resId: Int)
    fun showSnackBar(message: String)
    fun showSnackBar(@StringRes resId: Int)
    fun hideSoftInput()
}