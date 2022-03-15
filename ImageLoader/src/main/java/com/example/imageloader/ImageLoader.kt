package com.example.imageloader

import android.content.Context
import com.example.imageloader.cache.IImageCache
import com.example.imageloader.cache.ImageCacheManager
import com.example.imageloader.config.DisplayConfig
import com.example.imageloader.config.getDefaultImageCache
import com.example.imageloader.dispatcher.LoaderDispatcher
import com.example.imageloader.request.RequestExecutor
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object ImageLoader {

    private lateinit var loaderDispatcher: LoaderDispatcher
    private var initialize: Boolean = false

    fun with(context: Context): DisplayConfig {
        init(context)
        return DisplayConfig(this)
    }

    fun configGlobalCacheStrategy(imageCache: IImageCache) =
        ImageCacheManager.updateCacheStrategy(imageCache)

    private fun init(context: Context) {
        if (!initialize) {
            if (ImageCacheManager.imageCache == null) {
                configGlobalCacheStrategy(getDefaultImageCache(context))
            }
            loaderDispatcher = LoaderDispatcher(RequestExecutor())
            initialize = true
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun dispatch(displayConfig: DisplayConfig, requireWidth: Int = 0, requireHeight: Int = 0) {
        GlobalScope.launch(Dispatchers.Main) {
            loaderDispatcher.loadBitmapIntoView(
                displayConfig,
                requireWidth,
                requireHeight
            )
        }
    }
}