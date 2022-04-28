package com.example.gemalto

import android.util.Log
import com.gemalto.idp.mobile.authentication.AuthenticationModule
import com.gemalto.idp.mobile.authentication.mode.biometric.BiometricAuthMode
import com.gemalto.idp.mobile.authentication.mode.biometric.BiometricAuthService
import com.gemalto.idp.mobile.authentication.mode.pin.PinAuthInput
import com.gemalto.idp.mobile.otp.oath.OathService
import com.gemalto.idp.mobile.otp.oath.OathToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class FingerprintAuthModeActivator(builder: Builder) {

    private val oathService: OathService = builder.mOathService
    private val tokenName: String? = builder.tokenName
    private val authInput: PinAuthInput? = builder.authInput
    private val authMode: BiometricAuthMode?

    init {
        val authenticationModule = AuthenticationModule.create()
        authMode = BiometricAuthService.create(authenticationModule)?.authMode
    }

    class Builder {
        var tokenName: String? = null
        lateinit var mOathService: OathService
        var authInput: PinAuthInput? = null

        fun oathService(oathService: OathService) = apply { this.mOathService = oathService }
        fun tokenName(tokenName: String?) = apply { this.tokenName = tokenName }
        fun authInput(authInput: PinAuthInput?) = apply { this.authInput = authInput }

        fun build(): FingerprintAuthModeActivator {
            return FingerprintAuthModeActivator(this)
        }
    }

    fun activate(): Flow<Boolean> {
        return flow {
            val token = oathService.tokenManager.getToken<OathToken>(tokenName)
            if (!token.isMultiAuthModeEnabled) {
                token.upgradeToMultiAuthMode(authInput)

            }
            if (token.isMultiAuthModeEnabled && !token.isAuthModeActive(authMode)) {
                token.activateAuthMode(authMode, authInput)
            }
            authInput?.wipe()
            emit(true)
        }.catch {
            Log.d("ActivateTag",it.stackTrace.toString())
            authInput?.wipe()
            emit(false)
        }
            .flowOn(Dispatchers.Default)
    }
}