package com.example.basearchitecture.shared.gemalto

import android.util.Log
import com.gemalto.idp.mobile.authentication.AuthInput
import com.gemalto.idp.mobile.core.IdpCore
import com.gemalto.idp.mobile.core.util.SecureByteArray
import com.gemalto.idp.mobile.core.util.SecureString
import com.gemalto.idp.mobile.otp.oath.OathService
import com.gemalto.idp.mobile.otp.oath.soft.DualSeedSoftOathToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class OtpGenerator private constructor(builder: Builder) {
    private var oathService: OathService = builder.oathService
    private var tokenName: String? = builder.tokenName
    private var secureByteArray: SecureByteArray? = builder.secureByteArray
    private var otpType: OtpTypeEnum = builder.otpType
    private var authInput: AuthInput = builder.authInput

    class Builder {

        lateinit var oathService: OathService
        var tokenName: String? = null
        var secureByteArray: SecureByteArray? = null
        lateinit var otpType: OtpTypeEnum
        lateinit var authInput: AuthInput

        fun oathService(oathService: OathService) = apply { this.oathService = oathService }
        fun tokenName(tokenName: String?) = apply { this.tokenName = tokenName }
        fun secureByteArray(secureByteArray: SecureByteArray?) = apply { this.secureByteArray = secureByteArray }
        fun otpType(otpType: OtpTypeEnum) = apply { this.otpType = otpType }
        fun authInput(authInput: AuthInput) = apply { this.authInput = authInput }

        fun build(): OtpGenerator {
            return OtpGenerator(this)
        }
    }

    fun generate(): Flow<String> {
        return flow {
            if (otpType == OtpTypeEnum.OTP) {
                emit(generateOtp())
            } else {
                generateOCRAOtp()
            }
        }.catch { throwable ->
            Log.d("GetOtpTag", "Exception: " + throwable.message)
            Log.d("GetOtpTag", "StackTrace: " + throwable.stackTraceToString())
            emit("")
        }
            .flowOn(Dispatchers.Default)
    }

    /**
     * This method will generate a OATH TOTP for all the tokens provisioned
     * in the device. It provides a real world example (minus the bad UI) of
     * how this should be performed and it includes all security guidelines that
     * must be followed.
     */
    private fun generateOtp(): String {
        val otp: SecureString?

        /**
         * This is to make sure passwordManager has been logged in
         * It is not mandatory as long as the application make sure it is logged in before
         * the token is to be retrieved
         * */
        val pm = IdpCore.getInstance().passwordManager // Login the Password Manager if not logged in
        if (!pm.isLoggedIn) {
            pm.login()
        }

        /**
         * GENERATE OATH TOTPs
         *
         * The same custom fingerprint data used to create the token must
         * be provided before retrieving the token.
         * Notice that the fingerprint is included when creating the
         * token.
         *
         * Note that the example app simply use the last token in the database
         * In a real application, application should prompt for the PIN associated with
         * the token
         */
        val token = oathService.tokenManager?.getToken<DualSeedSoftOathToken>(tokenName)
        token?.selectKey(0)

        /**
         * A device is the object that generates OTPs. Notice this will
         * create OATH TOTPs.
         */
        val settings = oathService.factory?.createSoftOathSettings()
        settings?.setTotpTimestepSize(3)
        val device = oathService.factory?.createSoftOathDevice(token, settings)
        oathService.factory?.createSoftOathSettings()
            ?.setTotpTimestepSize(3)
        otp = device?.getTotp(authInput)

        /**
         *  Security Guideline: AND01. Sensitive data leaks
         *  The OTP will remain displayed in the UI even after this local
         *  variable is wiped. The application should clear this
         *  information as soon as it is consumed by the user (in
         *  this example's case, until the app is paused).
         */
        Log.d("GetOtpTag", """Current token used:$tokenName,
	                                        TOTP=$otp,
	                                        Last token is taken from the database. Please modify the code if you wish to select among different tokens.
         	                             """)
        var otpStr = ""
        otp?.let {
            otpStr = it.toString()
            it.wipe()
        }
        return otpStr
    }

    private fun generateOCRAOtp(): String {
        val otp: SecureString?

        /**
         * This is to make sure passwordManager has been logged in
         * It is not mandatory as long as the application make sure it is logged in before
         * the token is to be retrieved
         * */
        val pm = IdpCore.getInstance().passwordManager // Login the Password Manager if not logged in
        if (!pm.isLoggedIn) {
            pm.login()
        }

        /**
         * GENERATE OATH TOTPs
         *
         * The same custom fingerprint data used to create the token must
         * be provided before retrieving the token.
         * Notice that the fingerprint is included when creating the
         * token.
         *
         * Note that the example app simply use the last token in the database
         * In a real application, application should prompt for the PIN associated with
         * the token
         */
        val token = oathService.tokenManager?.getToken<DualSeedSoftOathToken>(tokenName)
        token?.selectKey(0)

        /**
         * A device is the object that generates OTPs. Notice this will
         * create OATH TOTPs.
         */
        val settings = oathService.factory?.createSoftOathSettings()
        val ocraSuite = "OCRA-1:HOTP-SHA256-8:QA64-T3S"
        val secureString = IdpCore.getInstance().secureContainerFactory.fromString(ocraSuite)
        settings?.setOcraSuite(secureString)
        val device = oathService.factory?.createSoftOathDevice(token, settings)
        otp = device?.getOcraOtp(authInput, secureByteArray, null, null, null)


        /**
         *  Security Guideline: AND01. Sensitive data leaks
         *  The OTP will remain displayed in the UI even after this local
         *  variable is wiped. The application should clear this
         *  information as soon as it is consumed by the user (in
         *  this example's case, until the app is paused).
         */
        Log.d("GetOtpTag", """Current token used:$tokenName,
	                                        TOTP=$otp,
	                                        Last token is taken from the database. Please modify the code if you wish to select among different tokens.
         	                             """)
        var otpStr = ""
        otp?.let {
            otpStr = it.toString()
            it.wipe()
        }
        return otpStr
    }

}