package com.example.basearchitecture.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.lifecycle.lifecycleScope
import com.example.basearchitecture.base.BaseActivity
import com.example.basearchitecture.databinding.ActivityMainBinding
import com.example.basearchitecture.shared.gemalto.FingerprintDialog
import com.example.basearchitecture.shared.gemalto.Gemalto
import com.example.basearchitecture.shared.gemalto.OtpTypeEnum
import com.gemalto.idp.mobile.core.ApplicationContextHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun ActivityMainBinding.initView() {
        ApplicationContextHolder.setContext(applicationContext)
        val gemalto = Gemalto.Builder()
            .context(this@MainActivity)
            .lifeCycleScope(lifecycleScope)
            .tokenName("Arshak")
            .otpType(OtpTypeEnum.OTP)
            .build()
        open.setOnClickListener {
            keyboard.addView(gemalto.openKeyBoard({ pin ->
                otpTv.text = otpTv.text.toString() + pin
            }) { otp ->
                Log.i("OtpTag", otp)
                otpTv.text = otp
                slideDown(keyboard)
            })
            slideUp(keyboard)
        }
        useFingerprint.setOnClickListener {
            gemalto.useFingerPrint(FingerprintDialog(this@MainActivity) {
                open.callOnClick()
            }) { otp ->

            }
        }
        close.setOnClickListener { slideDown(keyboard) }
        register.setOnClickListener {
            gemalto.provisioning("123456", onComplete = {
                Log.d("ProvTag", "Complete")
            }, onError = { Log.d("ProvTag", "Error") })
        }
    }

    fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(0f, 0f, view.height.toFloat(), 0f)
        animate.duration = 400
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    fun slideDown(view: View) {
        val animate = TranslateAnimation(0f, 0f, 0f, view.height.toFloat())
        animate.duration = 400
        animate.fillAfter = true
        view.startAnimation(animate)
        view.visibility = View.GONE
    }
}