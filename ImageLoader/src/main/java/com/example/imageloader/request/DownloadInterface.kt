package com.example.imageloader.request

import java.io.InputStream

interface DownloadInterface {
    fun getInputStreamByUrl(url: String): InputStream?
}