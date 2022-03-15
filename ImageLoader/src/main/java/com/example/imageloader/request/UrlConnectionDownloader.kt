package com.example.imageloader.request

import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class UrlConnectionDownloader : DownloadInterface {
    override fun getInputStreamByUrl(url: String): InputStream? {
        var urlConnection: HttpURLConnection? = null
        var ins: BufferedInputStream? = null
        try {
            urlConnection = URL(url).openConnection() as HttpURLConnection
            ins = BufferedInputStream(urlConnection.inputStream, 10 * 1024)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ins
    }
}