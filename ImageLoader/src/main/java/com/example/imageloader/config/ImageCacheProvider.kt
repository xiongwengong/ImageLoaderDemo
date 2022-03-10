package com.example.imageloader.config

import android.content.Context
import com.example.imageloader.cache.DiskCache
import com.example.imageloader.cache.DoubleCache
import com.example.imageloader.cache.IImageCache
import com.example.imageloader.cache.MemoryCache
import java.io.File

fun genDefaultImageCache(context: Context): IImageCache {
    val maxMemory = Runtime.getRuntime().maxMemory() / 1024 // unit:kb
    val memoryCache = MemoryCache((maxMemory / 10).toInt())

    val cachePath = context.cacheDir.absolutePath + File.separator + "twImageLoader"
    val diskCacheMaxSize = 50 * 1024 * 1024 // 50MB
    val diskCache = DiskCache(diskCacheMaxSize, cachePath)
    return DoubleCache(memoryCache, diskCache)
}