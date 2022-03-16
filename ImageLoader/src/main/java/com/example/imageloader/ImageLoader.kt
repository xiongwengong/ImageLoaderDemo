package com.example.imageloader

import android.content.Context
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
        private var loader: ImageLoader? = null

        fun with(context: Context): DisplayConfig {
            return get(context).createDisplayConfig()
        }

        fun get(
            context: Context,
            requestExecutor: RequestExecutor = RequestExecutor(),
            loaderDispatcher: LoaderDispatcher = LoaderDispatcher(requestExecutor)
        ): ImageLoader {
            if (loader == null) {
                loader = ImageLoader(loaderDispatcher).also {
                    if (ImageCacheManager.imageCache == null) {
                        configGlobalCacheStrategy(getDefaultImageCache(context))
                    }
                }
            }
            return loader!!
        }

        fun configGlobalCacheStrategy(imageCache: IImageCache) =
            ImageCacheManager.updateCacheStrategy(imageCache)

    }

    fun createDisplayConfig(): DisplayConfig {
        return DisplayConfig(loader!!)
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun dispatch(displayConfig: DisplayConfig, requireWidth: Int = 0, requireHeight: Int = 0) {
        GlobalScope.launch(dispatcher) {
            loaderDispatcher.loadBitmapIntoView(
                displayConfig,
                requireWidth,
                requireHeight
            )
        }
    }
}