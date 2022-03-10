package com.example.imageloader.request

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.example.imageloader.cache.IImageCache
import com.example.imageloader.config.ImageCacheManager
import com.example.imageloader.config.genDefaultImageCache

class BitmapRequest(context: Context) {
    var url: String? = null
    private var placeholder: Int = 0
    private var errorPlaceholder: Int = 0
    private lateinit var imageView: ImageView

    init {
        if (ImageCacheManager.imageCache == null) {
            ImageCacheManager.updateCacheStrategy(genDefaultImageCache(context))
        }
    }

    fun load(url: String) =
        apply { this.url = url }

    fun placeholder(@DrawableRes placeholder: Int) =
        apply { this.placeholder = placeholder }

    fun errorPlaceholder(@DrawableRes errorPlaceholder: Int) =
        apply { this.errorPlaceholder = errorPlaceholder }

    fun cacheStrategy(imageCache: IImageCache) =
        apply {
            ImageCacheManager.updateCacheStrategy(imageCache)
        }

    fun into(imageView: ImageView, requireWidth: Int = 0, requireHeight: Int = 0) {
        this.imageView = imageView
        if (url.isNullOrEmpty()) {
            if (errorPlaceholder != 0) {
                imageView.setImageResource(errorPlaceholder)
            }
            return
        }
        val bitmap = ImageCacheManager.imageCache!!.get(url!!, requireWidth, requireHeight)?.also {
            imageView.setImageBitmap(it)
        }

        if (bitmap == null) {
            if (placeholder != 0) {
                imageView.setImageResource(placeholder)
            }
            RequestManager.addRequest(this)
        }
    }
}