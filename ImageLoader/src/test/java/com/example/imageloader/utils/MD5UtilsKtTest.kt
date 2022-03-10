package com.example.imageloader.utils

import org.junit.Assert.*

import org.junit.Test

class MD5UtilsKtTest {
    @Test
    fun generateKeyByUrl() {
        val url = "https://test.test.png"
        val md5Str = "e3385f93d49495620d750ead43f4bf80"
        val md5UrlStr = url.generateKeyByUrl()
        println(md5UrlStr)
        assertEquals(md5Str, md5UrlStr)
    }
}