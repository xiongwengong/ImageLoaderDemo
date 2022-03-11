package com.example.imageloader.extensions

import org.junit.Assert.*
import org.junit.Test

class StringExtensionKtTest {
    @Test
    fun `given url string when try to turn md5 str then return md5 str`() {
        val url = "https://test.test.png"
        val md5Str = "e3385f93d49495620d750ead43f4bf80"
        val md5UrlStr = url.generateMd5KeyByUrl()
        println(md5UrlStr)
        assertEquals(md5Str, md5UrlStr)
    }
}