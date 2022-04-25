package com.example.basearchitecture.shared.gemalto

import android.os.CancellationSignal
import android.util.Log
import com.gemalto.idp.mobile.authentication.AuthenticationModule
import com.gemalto.idp.mobile.authentication.mode.biofingerprint.BioFingerprintAuthInput
import com.gemalto.idp.mobile.authentication.mode.biofingerprint.BioFingerprintAuthMode
import com.gemalto.idp.mobile.authentication.mode.biofingerprint.BioFingerprintAuthService
import com.gemalto.idp.mobile.authentication.mode.biofingerprint.BioFingerprintAuthenticationCallbacks
import com.gemalto.idp.mobile.core.IdpCore
import com.gemalto.idp.mobile.core.IdpException
import com.gemalto.idp.mobile.otp.oath.OathService
import com.gemalto.idp.mobile.otp.oath.OathToken

class FingerprintAuthInput(builder: Builder) {

    private val oathService: OathService = builder.mOathService
    private val tokenName: String? = builder.tokenName

    private val cancellationSignal: CancellationSignal
    private val bioFPAuthService: BioFingerprintAuthService
    private val bioFPAuthMode: BioFingerprintAuthMode?

    class Builder {
        var tokenName: String? = null
        lateinit var mOathService: OathService

        fun oathService(oathService: OathService) = apply { this.mOathService = oathService }
        fun tokenName(tokenName: String?) = apply { this.tokenName = tokenName }

        fun build(): FingerprintAuthInput {
            return FingerprintAuthInput(this)
        }
    }

    init {
        val authenticationModule = AuthenticationModule.create()
        bioFPAuthService = BioFingerprintAuthService.create(authenticationModule)
        bioFPAuthMode = bioFPAuthService?.authMode
        cancellationSignal = CancellationSignal()
    }

    fun getOtpWithFingerprint(onStart: () -> Unit, onSuccess: (BioFingerprintAuthInput) -> Unit, onError: () -> Unit, onCancel: () -> Unit, onFailed: (String) -> Unit) {
        try {
            val callback = object : BioFingerprintAuthenticationCallbacks {
                // Fingerprint authenticated successfully.
                // Proceed to get OTP
                override fun onSuccess(bioFingerprintAuthInput: BioFingerprintAuthInput) {
                    onSuccess.invoke(bioFingerprintAuthInput)
                }

                override fun onStartFPSensor() {
                    onStart.invoke()
                }

                override fun onError(e: IdpException) {
                    onError.invoke()
                }

                override fun onAuthenticationError(i: Int, charSequence: CharSequence) {
                    onCancel.invoke()
                }

                override fun onAuthenticationHelp(i: Int, charSequence: CharSequence) {
                    Log.e("Fingerprint", charSequence.toString())
                }

                override fun onAuthenticationSucceeded() {
                    Log.e("Fingerprint", "Fingerprint recognized.")
                }

                override fun onAuthenticationFailed() {
                    onFailed.invoke("Try again")
                }
            }
            val oathToken: OathToken = oathService.tokenManager.getToken(tokenName)
            if (oathToken.isAuthModeActive(bioFPAuthMode)) {
                bioFPAuthService.bioFingerprintContainer.authenticateUser(oathToken, cancellationSignal, callback)
            } else {

            }
        } catch (e: Exception) {

        }
    }
}