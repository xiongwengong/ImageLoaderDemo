package com.example.imageloader.request

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.*
import java.io.IOException
import java.io.InputStream

class RequestExecutor(
    private val downloadInterface: DownloadInterface = UrlConnectionDownloader(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getBitmapInputStream(url: String): InputStream? {
        return withContext(dispatcher) {
            return@withContext downloadInterface.getInputStreamByUrl(url)
        }
    }

    suspend fun convertInputStreamToBitmap(input: InputStream): Bitmap? {
        var bitmap: Bitmap? = null
        return withContext(dispatcher) {
            try {
                bitmap = BitmapFactory.decodeStream(input)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return@withContext bitmap
        }
    }
}
