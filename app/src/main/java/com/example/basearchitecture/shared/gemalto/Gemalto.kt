package com.example.basearchitecture.shared.gemalto

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import com.gemalto.idp.mobile.authentication.AuthenticationModule
import com.gemalto.idp.mobile.authentication.mode.biometric.BiometricAuthMode
import com.gemalto.idp.mobile.authentication.mode.biometric.BiometricAuthService
import com.gemalto.idp.mobile.core.IdpCore
import com.gemalto.idp.mobile.core.util.SecureByteArray
import com.gemalto.idp.mobile.otp.OtpConfiguration
import com.gemalto.idp.mobile.otp.OtpModule
import com.gemalto.idp.mobile.otp.oath.OathService
import kotlinx.coroutines.flow.collectLatest

class Gemalto private constructor(private val builder: Builder) {
    private var mContext: Context = builder.mContext
    private val mOathService: OathService
    private var bioFPAuthMode: BiometricAuthMode? = null
    private val bioFPAuthService: BiometricAuthService
    private var mLifeCycleScope: LifecycleCoroutineScope? = builder.mLifeCycleScope
    private var tokenName: String? = builder.tokenName
    private var secureByteArray: SecureByteArray? = builder.secureByteArray
    private var otpType: OtpTypeEnum = builder.otpType
    private var core: IdpCore? = null

    class Builder {
        lateinit var mContext: Context
        var mLifeCycleScope: LifecycleCoroutineScope? = null
        var tokenName: String? = null
        var secureByteArray: SecureByteArray? = null
        lateinit var otpType: OtpTypeEnum

        fun context(context: Context) = apply { this.mContext = context }
        fun lifeCycleScope(mLifeCycleScope: LifecycleCoroutineScope?) = apply { this.mLifeCycleScope = mLifeCycleScope }
        fun tokenName(tokenName: String?) = apply { this.tokenName = tokenName }
        fun secureByteArray(secureByteArray: SecureByteArray?) = apply { this.secureByteArray = secureByteArray }
        fun otpType(otpType: OtpTypeEnum) = apply { this.otpType = otpType }

        fun build(): Gemalto {
            return Gemalto(this)
        }
    }

    init {
        initIdpCore()
        val otpModule = OtpModule.create()
        mOathService = OathService.create(otpModule)
        val authenticationModule = AuthenticationModule.create()
        bioFPAuthService = BiometricAuthService.create(authenticationModule)
        bioFPAuthMode = bioFPAuthService?.authMode
    }

    private fun initIdpCore(): IdpCore? {
        return if (!IdpCore.isConfigured()) {
            val otpConfig = OtpConfiguration.Builder()
                .setRootPolicy(OtpConfiguration.TokenRootPolicy.IGNORE)
                .build()
            IdpCore.configure(AppData.getActivationCode(), otpConfig)
                .also { core = it }
        } else {
            IdpCore.getInstance()
                .also { core = it }
        }
    }

    fun openKeyBoard(onOtpGenerated: (String) -> Unit): GemaltoKeyboard = GemaltoKeyboard(mContext, onPinAuth = {
        mLifeCycleScope?.launchWhenStarted {
            val otpGenerator = OtpGenerator.Builder()
                .otpType(otpType)
                .oathService(mOathService)
                .authInput(it)
                .tokenName(tokenName)
                .secureByteArray(secureByteArray)
                .build()
            otpGenerator.generate()
                .collectLatest { otp -> onOtpGenerated.invoke(otp) }
        }
    }, onPinCountChange = {})

}