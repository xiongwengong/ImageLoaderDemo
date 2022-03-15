package com.example.imageloader.request

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class RequestExecutor(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {
    suspend fun getResults(url: String): InputStream? {
        return withContext(dispatcher) {
            var urlConnection: HttpURLConnection? = null
            var ins: InputStream? = null
            try {
                urlConnection = URL(url).openConnection() as HttpURLConnection
                ins = BufferedInputStream(urlConnection.inputStream, 500 * 1024)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                urlConnection?.disconnect()
            }
            return@withContext ins
        }
    }

    suspend fun convertInputStreamToBitmap(input: InputStream): Bitmap? {
        var bitmap: Bitmap? = null
        return withContext(dispatcher) {
            try {
                bitmap = BitmapFactory.decodeStream(input)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                input?.close()
            }
            return@withContext bitmap
        }

    }
}
