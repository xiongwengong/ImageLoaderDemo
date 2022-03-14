package com.example.imageloader.dispatcher

import com.example.imageloader.cache.ImageCacheManager
import com.example.imageloader.config.DisplayConfig
import com.example.imageloader.request.RequestManager

object LoaderDispatcher {

    fun loadBitmap(displayConfig: DisplayConfig, requireWidth: Int = 0, requireHeight: Int = 0) {
        with(displayConfig) {
            if (url.isNullOrEmpty()) {
                if (errorPlaceholder != 0) {
                    imageView.setImageResource(errorPlaceholder)
                }
                return
            }
            val bitmap =
                ImageCacheManager.imageCache?.get(url!!, requireWidth, requireHeight)?.also {
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
}