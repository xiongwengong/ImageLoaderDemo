package com.example.imageloader.config

import android.content.Context
import androidx.annotation.DrawableRes
import com.example.imageloader.cache.DoubleCache
import com.example.imageloader.cache.IImageCache

class ImageLoaderConfig private constructor(builder: Builder) {

    @DrawableRes
    var loadingRes: Int = builder.loadingRes

    @DrawableRes
    var errorRes: Int = builder.errorRes

    var imageCache: IImageCache = builder.imageCache!!


    class Builder(context: Context) {

        @DrawableRes
        internal var loadingRes: Int = 0

        @DrawableRes
        internal var errorRes: Int = 0

        private var memoryCacheMaxSize: Int = 0

        private var diskCacheMaxSize: Int = 0

        private lateinit var diskCachePath: String

        internal var imageCache: IImageCache? = null

        fun loadingRes(@DrawableRes loadingRes: Int) = apply { this.loadingRes = loadingRes }

        fun errorRes(@DrawableRes errorRes: Int) = apply { this.errorRes = errorRes }

        fun imageCache(imageCache: IImageCache) = apply { this.imageCache = imageCache }


        fun memoryCacheMaxSize(memoryCacheMaxSize: Int) =
            apply { this.memoryCacheMaxSize = memoryCacheMaxSize }

        fun diskCacheMaxSize(diskCacheMaxSize: Int) =
            apply { this.diskCacheMaxSize = diskCacheMaxSize }

        fun diskCachePath(diskCachePath: String) =
            apply { this.diskCachePath = diskCachePath }


        fun build(): ImageLoaderConfig {
            if (this.imageCache == null) {
                // todo  config memoryCache or disk cache
//                imageCache = DoubleCache()
            }
            return ImageLoaderConfig(this);
        }
    }
}