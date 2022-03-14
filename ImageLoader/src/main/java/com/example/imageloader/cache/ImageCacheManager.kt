package com.example.imageloader.cache

object ImageCacheManager {
    var imageCache: IImageCache? = null
        private set

    fun updateCacheStrategy(imageCache: IImageCache) {
        this.imageCache = imageCache
    }
}