package com.example.imageloader

import android.content.Context
import com.example.imageloader.cache.IImageCache
import com.example.imageloader.cache.ImageCacheManager
import com.example.imageloader.config.DisplayConfig
import com.example.imageloader.config.genDefaultImageCache

object ImageLoader {
    fun with(context: Context): DisplayConfig {
        if (ImageCacheManager.imageCache == null) {
            configGlobalCacheStrategy(genDefaultImageCache(context))
        }
        return DisplayConfig()
    }

    fun configGlobalCacheStrategy(imageCache: IImageCache) =
        ImageCacheManager.updateCacheStrategy(imageCache)
}