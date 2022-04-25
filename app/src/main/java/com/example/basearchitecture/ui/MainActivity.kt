package com.example.basearchitecture.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.lifecycle.lifecycleScope
import com.example.basearchitecture.base.BaseActivity
import com.example.basearchitecture.databinding.ActivityMainBinding
import com.example.basearchitecture.shared.gemalto.AppData
import com.example.basearchitecture.shared.gemalto.Gemalto
import com.example.basearchitecture.shared.gemalto.OtpTypeEnum
import com.gemalto.idp.mobile.core.ApplicationContextHolder
import com.gemalto.idp.mobile.core.IdpCore
import com.gemalto.idp.mobile.otp.OtpConfiguration
import com.gemalto.idp.mobile.otp.OtpModule
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate
    private var core: IdpCore? = null
    override fun ActivityMainBinding.initView() {
        ApplicationContextHolder.setContext(applicationContext)
        initIdpCore()
        val otpModule = OtpModule.create()
        val gemalto = Gemalto.Builder()
            .context(this@MainActivity)
            .lifeCycleScope(lifecycleScope)
            .otpType(OtpTypeEnum.OTP)
            .build()
        keyboard.addView(gemalto.openKeyBoard { otp -> Log.i("OtpTag", otp) })
        slideUp(keyboard)
    }

    private fun initIdpCore(): IdpCore? {
        return if (!IdpCore.isConfigured()) {
            val otpConfig = OtpConfiguration.Builder()
                .setRootPolicy(OtpConfiguration.TokenRootPolicy.IGNORE)
                .build()
            IdpCore.configure(AppData.getActivationCode(), otpConfig)
                .also {
                    core = it
                    it.passwordManager.login()
                }
        } else {
            IdpCore.getInstance()
                .also {
                    core = it
                    it.passwordManager.login()
                }
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
}