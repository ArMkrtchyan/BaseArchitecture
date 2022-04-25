package com.example.basearchitecture.shared.gemalto

import com.example.basearchitecture.BuildConfig

/**
 * This class provides application specific data. These values must not be used
 * by a real application because they are not universal settings.
 */
object AppData {
    /**
     * Replace this string with your own EPS key ID.
     *
     *
     * This is specific to the configuration of the bank's system. Therefore
     * other values should be used here.
     */
    fun getRsaKeyId(): String {
        return "acba-public-key"
    }

    /**
     * Replace this URL with your EPS URL.
     */
    fun getEpsUrl(): String {
        return if (BuildConfig.DEBUG) { //            return "https://www.acbadigital.am/api/mobiletoken/provisioning";
            //            return "https://online-dev.acba.am:12443/mobiletoken/provisioner/api/provisioning/pp";
            "https://online1-test.acba.am/api/mobiletoken/provisioning"
        } else { //          return "https://online1-test.acba.am/api/mobiletoken/provisioning";
            "https://www.acbadigital.am/api/mobiletoken/provisioning"
        }
    }

    /**
     * Replace this byte array with your own EPS key modulus unless you are
     * using the EPS 2.X default key pair.
     *
     *
     * The EPS' RSA modulus. This is specific to the configuration of the
     * bank's system.  Therefore other values should be used here.
     */
    fun getRsaKeyModulus(): ByteArray { // Security Guideline: GEN13. Integrity of public keys
        // Since this example hard codes the key and does not load it from a
        // file, this guideline is skipped.
        // Security Guideline: GEN17. RSA key length
        // 2048 bit key
        return byteArrayOf(0x00.toByte(), 0xc5.toByte(), 0xfa.toByte(), 0x65.toByte(), 0xaf.toByte(), 0xcf.toByte(), 0x7d.toByte(), 0x55.toByte(), 0x4f.toByte(), 0x10.toByte(), 0x43.toByte(), 0x73.toByte(), 0x55.toByte(), 0xc8.toByte(), 0x75.toByte(), 0xb6.toByte(), 0x1b.toByte(), 0x95.toByte(), 0xd0.toByte(), 0x02.toByte(), 0x42.toByte(), 0x07.toByte(), 0x7b.toByte(), 0xc1.toByte(), 0xe5.toByte(), 0x9d.toByte(), 0x88.toByte(), 0x77.toByte(), 0x03.toByte(), 0x8f.toByte(), 0x6a.toByte(), 0xcd.toByte(), 0xcf.toByte(), 0xd0.toByte(), 0xfe.toByte(), 0x89.toByte(), 0x3e.toByte(), 0x9c.toByte(), 0x8f.toByte(), 0x6e.toByte(), 0x5e.toByte(), 0x7c.toByte(), 0x9c.toByte(), 0x41.toByte(), 0xc4.toByte(), 0x4c.toByte(), 0x5c.toByte(), 0x0c.toByte(), 0xe5.toByte(), 0x38.toByte(), 0xe3.toByte(), 0xfa.toByte(), 0x60.toByte(), 0x3c.toByte(), 0xd6.toByte(), 0xa5.toByte(), 0x09.toByte(), 0xcb.toByte(), 0x96.toByte(), 0xaa.toByte(), 0x2f.toByte(), 0x68.toByte(), 0x05.toByte(), 0x36.toByte(), 0x2f.toByte(), 0x8a.toByte(), 0x6b.toByte(), 0xea.toByte(), 0x6c.toByte(), 0x98.toByte(), 0xd7.toByte(), 0xc6.toByte(), 0xaa.toByte(), 0x27.toByte(), 0xc4.toByte(), 0xe0.toByte(), 0x49.toByte(), 0x9d.toByte(), 0x4e.toByte(), 0x01.toByte(), 0xd3.toByte(), 0x9c.toByte(), 0xa8.toByte(), 0x42.toByte(), 0x43.toByte(), 0xb7.toByte(), 0xf0.toByte(), 0xd1.toByte(), 0xbc.toByte(), 0x16.toByte(), 0x1b.toByte(), 0x25.toByte(), 0x96.toByte(), 0xa7.toByte(), 0x9a.toByte(), 0xfb.toByte(), 0x66.toByte(), 0x77.toByte(), 0x42.toByte(), 0x00.toByte(), 0xd7.toByte(), 0xaa.toByte(), 0xb1.toByte(), 0xc1.toByte(), 0xc0.toByte(), 0xaf.toByte(), 0xeb.toByte(), 0x34.toByte(), 0xad.toByte(), 0xcd.toByte(), 0x9a.toByte(), 0x94.toByte(), 0xf4.toByte(), 0x81.toByte(), 0x7e.toByte(), 0x95.toByte(), 0xeb.toByte(), 0x4f.toByte(), 0xd3.toByte(), 0x2d.toByte(), 0xff.toByte(), 0xdb.toByte(), 0xed.toByte(), 0x1a.toByte(), 0xae.toByte(), 0x96.toByte(), 0xb9.toByte(), 0xb6.toByte(), 0x50.toByte(), 0x4e.toByte(), 0x3b.toByte(), 0xed.toByte(), 0x56.toByte(), 0x03.toByte(), 0x36.toByte(), 0xc6.toByte(), 0xda.toByte(), 0x83.toByte(), 0xd2.toByte(), 0x8a.toByte(), 0xae.toByte(), 0x78.toByte(), 0xeb.toByte(), 0xbb.toByte(), 0x76.toByte(), 0x85.toByte(), 0x30.toByte(), 0x78.toByte(), 0xd2.toByte(), 0x0b.toByte(), 0xa3.toByte(), 0x2b.toByte(), 0x0c.toByte(), 0x36.toByte(), 0xf0.toByte(), 0xb3.toByte(), 0x47.toByte(), 0xb1.toByte(), 0x14.toByte(), 0x1f.toByte(), 0xac.toByte(), 0x49.toByte(), 0x18.toByte(), 0x1a.toByte(), 0x23.toByte(), 0x7f.toByte(), 0xf8.toByte(), 0xcd.toByte(), 0x53.toByte(), 0xfc.toByte(), 0x60.toByte(), 0x48.toByte(), 0x74.toByte(), 0xf1.toByte(), 0x7a.toByte(), 0x8e.toByte(), 0xde.toByte(), 0xe7.toByte(), 0xc8.toByte(), 0x08.toByte(), 0xff.toByte(), 0x09.toByte(), 0x1d.toByte(), 0xbe.toByte(), 0xdb.toByte(), 0x67.toByte(), 0x01.toByte(), 0x59.toByte(), 0xa6.toByte(), 0x71.toByte(), 0x9e.toByte(), 0x9f.toByte(), 0x84.toByte(), 0x3a.toByte(), 0x62.toByte(), 0xab.toByte(), 0x3f.toByte(), 0xb1.toByte(), 0x3e.toByte(), 0x15.toByte(), 0x10.toByte(), 0x76.toByte(), 0x36.toByte(), 0x06.toByte(), 0xe2.toByte(), 0x08.toByte(), 0x40.toByte(), 0x12.toByte(), 0x29.toByte(), 0x41.toByte(), 0xf1.toByte(), 0x4d.toByte(), 0xe9.toByte(), 0xfa.toByte(), 0xb4.toByte(), 0xe0.toByte(), 0x1b.toByte(), 0x44.toByte(), 0x84.toByte(), 0x24.toByte(), 0x30.toByte(), 0x4c.toByte(), 0x5a.toByte(), 0x46.toByte(), 0xd4.toByte(), 0xa3.toByte(), 0xfe.toByte(), 0x6a.toByte(), 0x33.toByte(), 0x5e.toByte(), 0xc1.toByte(), 0x24.toByte(), 0x19.toByte(), 0x37.toByte(), 0x1d.toByte(), 0x8e.toByte(), 0x71.toByte(), 0xf1.toByte(), 0x39.toByte(), 0x97.toByte(), 0xe3.toByte(), 0xf6.toByte(), 0x1a.toByte(), 0x67.toByte(), 0x88.toByte(), 0x22.toByte(), 0xac.toByte(), 0xed.toByte(), 0xcd.toByte(), 0x61.toByte(), 0xfd.toByte(), 0x09.toByte(), 0x31.toByte(), 0x0b.toByte(), 0x8d.toByte(), 0x2b.toByte(), 0x9f.toByte())
    }

    fun getActivationCode(): ByteArray {
        return byteArrayOf(0x01.toByte(), 0x41.toByte(), 0x43.toByte(), 0x42.toByte(), 0x41.toByte(), 0x42.toByte(), 0x61.toByte(), 0x6e.toByte(), 0x6b.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x02.toByte(), 0x5e.toByte(), 0x71.toByte(), 0xd1.toByte(), 0x6e.toByte(), 0x1a.toByte(), 0x57.toByte(), 0xc9.toByte(), 0xb1.toByte(), 0x78.toByte(), 0xec.toByte(), 0xf9.toByte(), 0xb3.toByte(), 0xb2.toByte(), 0x6c.toByte(), 0xbe.toByte(), 0x4b.toByte(), 0xb7.toByte(), 0x85.toByte(), 0xa9.toByte(), 0xe3.toByte(), 0x2a.toByte(), 0x2c.toByte(), 0x11.toByte(), 0x87.toByte(), 0x34.toByte(), 0xfc.toByte(), 0xac.toByte(), 0x22.toByte(), 0x5b.toByte(), 0xf5.toByte(), 0xf5.toByte(), 0x86.toByte(), 0x8a.toByte(), 0x2a.toByte(), 0x64.toByte(), 0xf4.toByte(), 0x7a.toByte(), 0xf5.toByte(), 0x0a.toByte(), 0xd6.toByte(), 0xe9.toByte(), 0xf4.toByte(), 0x62.toByte(), 0x0c.toByte(), 0x08.toByte(), 0x4d.toByte(), 0x05.toByte(), 0x6f.toByte(), 0x1a.toByte(), 0xde.toByte(), 0x51.toByte(), 0x47.toByte(), 0x57.toByte(), 0x56.toByte(), 0x8e.toByte(), 0xc6.toByte(), 0x6d.toByte(), 0x39.toByte(), 0x82.toByte(), 0x08.toByte(), 0xc5.toByte(), 0xc3.toByte(), 0x04.toByte(), 0x8b.toByte())
    }

    /**
     * Replace this byte array with your own EPS key exponent.
     *
     *
     * The EPS' RSA exponent. This is specific to the configuration of the
     * bank's system.  Therefore other values should be used here.
     */
    fun getRsaKeyExponent(): ByteArray { // Security Guideline: GEN13. Integrity of public keys
        // Since this example hard codes the key and does not load it from a
        // file, this guideline is skipped.
        return byteArrayOf(0x01.toByte(), 0x00.toByte(), 0x01.toByte())
    }

    /**
     * The custom fingerprint data that seals all the token credentials in this
     * example.
     *
     *
     * This data does not need to be modified in order to use this example app.
     */
    fun getCustomFingerprintData(): ByteArray { // This example simply uses the package name.
        //
        // This is one example of possible data that can be used for the custom
        // data. It provides domain separation so that the data stored by the
        // Ezio Mobile SDK is different for this application than it would be
        // for another bank's application. More data can be appended to
        // further improve the fingerprinting.
        return "am.acba.acbamobile.application.ACBAApplication".toByteArray()
    }
}