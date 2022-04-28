package com.example.gemalto

interface FingerprintUiController {
    fun showDialog()
    fun dismissDialog()
    fun onCancel()
    fun setStatusText(text: String)
}