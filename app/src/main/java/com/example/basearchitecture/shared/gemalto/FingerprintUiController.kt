package com.example.basearchitecture.shared.gemalto

interface FingerprintUiController {
    fun showDialog()
    fun dismissDialog()
    fun onCancel()
    fun setStatusText(text: String)
}