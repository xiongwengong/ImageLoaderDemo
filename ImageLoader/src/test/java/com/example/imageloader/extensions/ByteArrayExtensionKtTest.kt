package com.example.imageloader.extensions

import org.junit.Assert.*
import org.junit.Test
import java.security.MessageDigest

class ByteArrayExtensionKtTest {
    @Test
    fun `given valid ByteArray obj when turn to hex string then return Hex String`() {
        val str = "https://test.test.png"
        val digest: MessageDigest = MessageDigest.getInstance("MD5")
        digest.update(str.toByteArray())
        val byteArray = digest.digest()

        val hexStr = byteArray.bytesToHexString()
        assertFalse(hexStr.isEmpty())
    }
}