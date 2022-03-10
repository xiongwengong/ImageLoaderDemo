package com.example.imageloader.cache

import android.graphics.Bitmap

class DoubleCache(
    private val memoryCache: IImageCache,
    private val diskCache: IImageCache
) : IImageCache {

    override fun put(url: String, bitmap: Bitmap?) {
        memoryCache.put(url, bitmap)
        diskCache.put(url, bitmap)
    }

    override fun get(url: String, requireWidth: Int, requireHeight: Int): Bitmap? {
        return memoryCache.get(url, requireWidth, requireHeight)
            ?: diskCache.get(url, requireWidth, requireHeight)
                .also {
                    memoryCache.put(url, it)
                }
    }
}