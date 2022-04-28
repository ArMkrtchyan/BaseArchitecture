package com.example.gemalto

import android.util.Log
import com.gemalto.idp.mobile.core.IdpCore
import com.gemalto.idp.mobile.core.net.TlsConfiguration
import com.gemalto.idp.mobile.core.util.SecureString
import com.gemalto.idp.mobile.otp.oath.OathService
import com.gemalto.idp.mobile.otp.oath.OathToken
import com.gemalto.idp.mobile.otp.provisioning.EpsConfigurationBuilder
import com.gemalto.idp.mobile.otp.provisioning.MobileProvisioningProtocol
import com.gemalto.idp.mobile.otp.provisioning.ProvisioningConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.URL

internal class Provisioning private constructor(builder: Builder) {
    private var oathService: OathService = builder.oathService
    private var tokenName: String? = builder.tokenName
    private var secureString: SecureString = builder.secureString

    class Builder {

        lateinit var oathService: OathService
        var tokenName: String? = null
        lateinit var secureString: SecureString

        fun oathService(oathService: OathService) = apply { this.oathService = oathService }
        fun tokenName(tokenName: String?) = apply { this.tokenName = tokenName }
        fun secureString(secureString: SecureString) = apply { this.secureString = secureString }

        fun build(): Provisioning {
            return Provisioning(this)
        }
    }

    fun provision(): Flow<Boolean> {
        return flow {
            val pm = IdpCore.getInstance().passwordManager
            if (!pm.isLoggedIn) {
                pm.login()
            }
            val oathConfig = setupEpsConfiguration()
            oathService.tokenManager.createToken<OathToken>(tokenName, oathConfig, OathToken.TokenCapability.DUAL_SEED)
            secureString.wipe()
            emit(true)
        }.catch { throwable ->
            Log.d("ProvisioningTag", "Exception: " + throwable.message)
            Log.d("ProvisioningTag", "StackTrace: " + throwable.stackTraceToString())
            emit(false)
        }
            .flowOn(Dispatchers.Default)
    }

    private fun setupEpsConfiguration(): ProvisioningConfiguration? {
        val epsUrl = URL(AppData.getEpsUrl())

        /**
         * Security Guideline: GEN02. Communication over HTTP forbidden
         * Security Guideline: GEN04. Reject invalid SSL certificates
         *
         * Use this to lower the security of the TLS connection. It is preferred
         * to not alter the TLS settings in order to ensure the highest level of
         * security.
         **/
        val tls = TlsConfiguration()

        //build Cap provisioning configuration for EPS provisioning
        return EpsConfigurationBuilder(secureString, epsUrl, MobileProvisioningProtocol.PROVISIONING_PROTOCOL_V3, AppData.getRsaKeyId(), AppData.getRsaKeyExponent(), AppData.getRsaKeyModulus()) // Optional Step:
            /**
             * This is unnecessary if one uses the default TlsConfiguration.
             * However, this example leaves this call in order to demonstrate how
             * to use the TLS options.
             **/
            .setTlsConfiguration(tls)
            .build()
    }
}