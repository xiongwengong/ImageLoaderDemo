package com.example.imageloader.config

import com.example.imageloader.cache.IImageCache

object ImageCacheManager {
    var imageCache: IImageCache? = null
        private set

    fun updateCacheStrategy(imageCache: IImageCache) {
        this.imageCache = imageCache
    }
}