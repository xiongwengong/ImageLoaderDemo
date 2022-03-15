package com.example.imageloader.dispatcher

import android.graphics.Bitmap
import com.example.imageloader.cache.ImageCacheManager
import com.example.imageloader.config.DisplayConfig
import com.example.imageloader.request.RequestExecutor
import kotlinx.coroutines.*

class LoaderDispatcher(
    private val requestExecutor: RequestExecutor,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun loadBitmapIntoView(
        displayConfig: DisplayConfig,
        requireWidth: Int = 0,
        requireHeight: Int = 0
    ) {
        with(displayConfig) {
            imageView.setImageResource(placeholder)
            val bitmap = loadBitmap(url, requireWidth, requireHeight)
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
            } else {
                imageView.setImageResource(errorPlaceholder)
            }
        }
    }

    suspend fun loadBitmap(
        url: String,
        requireWidth: Int = 0,
        requireHeight: Int = 0
    ): Bitmap? {
        return withContext(dispatcher) {
            var bitmap: Bitmap? =
                ImageCacheManager.imageCache?.get(url, requireWidth, requireHeight)
            if (bitmap == null) {
                println("===>can not find cache for $url ,try to get form network.")
                bitmap = requestExecutor.getResults(url)?.run {
                    requestExecutor.convertInputStreamToBitmap(this)
                }?.also {
                    ImageCacheManager.imageCache?.put(url,it)
                }
            }
            return@withContext bitmap
        }
    }
}