package com.example.basearchitecture.shared.gemalto

interface FingerprintUiController {
    fun show()
    fun dismiss()
    fun cancel()
    fun setStatusText(text: String)
}