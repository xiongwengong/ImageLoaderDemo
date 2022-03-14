package com.example.imageloader.request

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Looper
import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class BitmapDownloader(
    private val url: String,
    private val callBack: (Bitmap?) -> Unit
) : Runnable {

    override fun run() {
        val bitmap = downloadBitmapFromNetwork(url)
        callBack.invoke(bitmap)
    }

    private fun downloadBitmapFromNetwork(url: String): Bitmap? {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw RuntimeException("download image should not from UI Thread.")
        }
        var bitmap: Bitmap? = null
        var urlConnection: HttpURLConnection? = null
        var ins: BufferedInputStream? = null
        try {
            urlConnection = URL(url).openConnection() as HttpURLConnection
            ins = BufferedInputStream(urlConnection.inputStream, IO_BUFFER_SIZE)
            bitmap = BitmapFactory.decodeStream(ins)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
            try {
                ins?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return bitmap
    }

    companion object {
        private const val IO_BUFFER_SIZE = 5 * 1024
    }
}