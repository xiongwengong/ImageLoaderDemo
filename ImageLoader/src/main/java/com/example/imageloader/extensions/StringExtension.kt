package com.example.imageloader.extensions

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun String.generateMd5KeyByUrl(): String {
    return try {
        val digest: MessageDigest = MessageDigest.getInstance("MD5")
        digest.update(toByteArray())
        digest.digest().bytesToHexString()
    } catch (e: NoSuchAlgorithmException) {
        hashCode().toString()
    }
}