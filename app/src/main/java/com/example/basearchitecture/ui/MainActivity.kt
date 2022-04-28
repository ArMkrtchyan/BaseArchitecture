package com.example.basearchitecture.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.lifecycle.lifecycleScope
import com.example.basearchitecture.databinding.ActivityMainBinding
import com.example.core.base.BaseActivity
import com.example.gemalto.FingerprintDialog
import com.example.gemalto.Gemalto
import com.example.gemalto.OtpTypeEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    // gagik97
    //0779
    override fun ActivityMainBinding.initView() {
        Gemalto.setApplicationContext(applicationContext)
        var gemalto: Gemalto
        open.setOnClickListener {
            gemalto = Gemalto.Builder()
                .context(this@MainActivity)
                .lifeCycleScope(lifecycleScope)
                .tokenName(userName.text.toString())
                .otpType(OtpTypeEnum.OTP)
                .build()

            keyboard.addView(gemalto.openKeyBoard({ pin ->

            }) { otp ->
                Log.i("OtpTag", otp)
                otpTv.text = otp
                slideDown(keyboard)
                gemalto.activateFingerPrint()
            })
            slideUp(keyboard)
        }
        useFingerprint.setOnClickListener {
            gemalto = Gemalto.Builder()
                .context(this@MainActivity)
                .lifeCycleScope(lifecycleScope)
                .tokenName(userName.text.toString())
                .otpType(OtpTypeEnum.OTP)
                .build()
            gemalto.useFingerPrint(FingerprintDialog(this@MainActivity) {
                open.callOnClick()
            }) { otp ->
                otpTv.text = otp
            }
        }
        close.setOnClickListener { slideDown(keyboard) }
        register.setOnClickListener {
            gemalto = Gemalto.Builder()
                .context(this@MainActivity)
                .lifeCycleScope(lifecycleScope)
                .tokenName(userName.text.toString())
                .otpType(OtpTypeEnum.OTP)
                .build()
            gemalto.provisioning(regCode.text.toString(), onComplete = {
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