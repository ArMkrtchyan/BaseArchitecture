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
        open.setOnClickListener {
            val gemalto = Gemalto.Builder()
                .context(this@MainActivity)
                .lifeCycleScope(lifecycleScope)
                .tokenName("Arshak")
                .otpType(OtpTypeEnum.OTP)
                .build() //            keyboard.addView(gemalto.openKeyBoard({ pin -> //                otpTv.text = otpTv.text.toString() + pin //            }) { otp ->
            //                Log.i("OtpTag", otp)
            //                otpTv.text = otp
            //                slideDown(keyboard)
            //            })
            //            slideUp(keyboard)
            gemalto.useFingerPrint(FingerprintDialog()) { otp ->

            }
        }
        close.setOnClickListener { slideDown(keyboard) }
        register.setOnClickListener {
            val gemalto = Gemalto.Builder()
                .context(this@MainActivity)
                .lifeCycleScope(lifecycleScope)
                .tokenName("Arshak")
                .build()
            gemalto.provisioning("123456", onComplete = {
                Log.d("ProvTag", "Complete")
            }, onError = { Log.d("ProvTag", "Error") })
        }
    }

    fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(0f,  // fromXDelta
            0f,  // toXDelta
            view.height.toFloat(),  // fromYDelta
            0f) // toYDelta
        animate.duration = 400
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    fun slideDown(view: View) {
        val animate = TranslateAnimation(0f,  // fromXDelta
            0f,  // toXDelta
            0f,  // fromYDelta
            view.height.toFloat()) // toYDelta
        animate.duration = 400
        animate.fillAfter = true
        view.startAnimation(animate)
        view.visibility = View.GONE
    }
}