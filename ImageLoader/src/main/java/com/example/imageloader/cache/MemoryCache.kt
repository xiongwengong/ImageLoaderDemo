package com.example.imageloader.cache

import android.graphics.Bitmap
import android.util.LruCache
import com.example.imageloader.extensions.generateMd5KeyByUrl

class MemoryCache(private val maxSize: Int) : IImageCache {
    private val lruCache: LruCache<String, Bitmap> by lazy {
        object : LruCache<String, Bitmap>(maxSize) {
            override fun sizeOf(key: String?, value: Bitmap?): Int {
                return if (value != null) {
                    value.rowBytes * value.height / 1024
                } else {
                    super.sizeOf(key, value)
                }
            }
        }
    }

    override fun get(url: String, requireWidth: Int, requireHeight: Int): Bitmap? {
        return lruCache.get(url.generateMd5KeyByUrl())
    }

    override fun put(url: String, bitmap: Bitmap) {
        bitmap.let { lruCache.put(url.generateMd5KeyByUrl(), it) }
    }
}