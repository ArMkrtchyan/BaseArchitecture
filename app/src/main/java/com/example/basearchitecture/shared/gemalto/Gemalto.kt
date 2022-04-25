package com.example.basearchitecture.shared.gemalto

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.gemalto.idp.mobile.core.IdpCore
import com.gemalto.idp.mobile.core.passwordmanager.PasswordManagerException
import com.gemalto.idp.mobile.core.util.SecureByteArray
import com.gemalto.idp.mobile.otp.OtpConfiguration
import com.gemalto.idp.mobile.otp.OtpModule
import com.gemalto.idp.mobile.otp.oath.OathService
import kotlinx.coroutines.flow.collectLatest

class Gemalto private constructor(builder: Builder) {
    private var mContext: Context = builder.mContext
    private val mOathService: OathService
    private var mLifeCycleScope: LifecycleCoroutineScope? = builder.mLifeCycleScope
    private var tokenName: String? = builder.tokenName
    private var secureByteArray: SecureByteArray? = builder.secureByteArray
    private var otpType: OtpTypeEnum = builder.otpType
    private lateinit var core: IdpCore

    class Builder {
        lateinit var mContext: Context
        var mLifeCycleScope: LifecycleCoroutineScope? = null
        var tokenName: String? = null
        var registrationCode: String? = null
        var secureByteArray: SecureByteArray? = null
        var otpType: OtpTypeEnum = OtpTypeEnum.OTP

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
    }

    private fun initIdpCore(): IdpCore {
        return if (!IdpCore.isConfigured()) {
            val otpConfig = OtpConfiguration.Builder()
                .setRootPolicy(OtpConfiguration.TokenRootPolicy.IGNORE)
                .build()
            IdpCore.configure(AppData.getActivationCode(), otpConfig)
                .also {
                    core = it
                    try {
                        val pm = core.passwordManager
                        if (!pm.isLoggedIn) {
                            pm.login()
                        }
                    } catch (e: PasswordManagerException) {
                        Log.e("Password", e.toString())
                    }
                }
        } else {
            IdpCore.getInstance()
                .also {
                    core = it
                    try {
                        val pm = core.passwordManager
                        if (!pm.isLoggedIn) {
                            pm.login()
                        }
                    } catch (e: PasswordManagerException) {
                        Log.e("Password", e.toString())
                    }
                }
        }
    }

    fun openKeyBoard(onPinCountChange: (String) -> Unit, onOtpGenerated: (String) -> Unit): GemaltoKeyboard = GemaltoKeyboard(mContext, onPinAuth = {
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
    }, onPinCountChange = { pin -> onPinCountChange.invoke(pin.toString()) })

    fun useFingerPrint(controller: FingerprintUiController, onOtpGenerated: (String) -> Unit) {
        mLifeCycleScope?.launchWhenStarted {
            val fingerprintAuthInput = FingerprintAuthInput.Builder()
                .oathService(mOathService)
                .tokenName(tokenName)
                .build()
            fingerprintAuthInput.getOtpWithFingerprint(controller::show, { bioFingerprintAuthInput ->
                controller.dismiss()
                mLifeCycleScope?.launchWhenStarted {
                    val otpGenerator = OtpGenerator.Builder()
                        .otpType(otpType)
                        .oathService(mOathService)
                        .authInput(bioFingerprintAuthInput)
                        .tokenName(tokenName)
                        .secureByteArray(secureByteArray)
                        .build()
                    otpGenerator.generate()
                        .collectLatest { otp -> onOtpGenerated.invoke(otp) }
                }
            }, {}, controller::cancel,controller::setStatusText)
        }
    }

    fun provisioning(registrationCode: String, onComplete: () -> Unit, onError: () -> Unit) {
        mLifeCycleScope?.launchWhenStarted {
            val prov = Provisioning.Builder()
                .oathService(mOathService)
                .secureString(core.secureContainerFactory.fromString(registrationCode))
                .tokenName(tokenName)
                .build()
            prov.provision()
                .collectLatest { isSuccess ->
                    if (isSuccess) onComplete.invoke()
                    else onError.invoke()
                }
        }
    }
}