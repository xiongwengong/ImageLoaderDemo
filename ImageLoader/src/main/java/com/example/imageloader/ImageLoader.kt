package com.example.imageloader

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.imageloader.cache.IImageCache
import com.example.imageloader.cache.ImageCacheManager
import com.example.imageloader.config.DisplayConfig
import com.example.imageloader.config.getDefaultImageCache
import com.example.imageloader.dispatcher.LoaderDispatcher
import com.example.imageloader.request.RequestExecutor
import kotlinx.coroutines.*

class ImageLoader private constructor(
    private val loaderDispatcher: LoaderDispatcher,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {

    companion object {

        @SuppressLint("StaticFieldLeak")
        private lateinit var loader: ImageLoader
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        @JvmStatic
        fun with(context: Context): DisplayConfig {
            return DisplayConfig(get(context))
        }

        fun get(
            context: Context,
            requestExecutor: RequestExecutor = RequestExecutor(),
            loaderDispatcher: LoaderDispatcher = LoaderDispatcher(requestExecutor)
        ): ImageLoader {
            if (!::loader.isInitialized) {
                this.context = context.applicationContext
                loader = ImageLoader(loaderDispatcher).also {
                    if (ImageCacheManager.imageCache == null) {
                        configGlobalCacheStrategy(getDefaultImageCache(context))
                    }
                }
            }
            return loader
        }

        @JvmStatic
        fun configGlobalCacheStrategy(imageCache: IImageCache) =
            ImageCacheManager.updateCacheStrategy(imageCache)

    }

    @DelicateCoroutinesApi
    fun dispatch(displayConfig: DisplayConfig, requireWidth: Int = 0, requireHeight: Int = 0) {
        GlobalScope.launch(dispatcher) {
            loaderDispatcher.loadBitmapIntoView(
                displayConfig,
                requireWidth,
                requireHeight
            )
        }
    }

    suspend fun loadBitmap(url: String, reqWidth: Int = 0, reqHeight: Int = 0): Bitmap? {
        return loaderDispatcher.loadBitmap(url, reqWidth, reqHeight)
    }

    fun loadBitmap(resId: Int): Bitmap? {
        return BitmapFactory.decodeResource(context.resources, resId)
    }
}