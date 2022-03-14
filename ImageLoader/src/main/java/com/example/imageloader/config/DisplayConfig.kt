package com.example.imageloader.config

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.example.imageloader.dispatcher.LoaderDispatcher

class DisplayConfig {
    var url: String? = null
    var placeholder: Int = 0
    var errorPlaceholder: Int = 0
    lateinit var imageView: ImageView

    fun load(url: String) =
        apply { this.url = url }

    fun placeholder(@DrawableRes placeholder: Int) =
        apply { this.placeholder = placeholder }

    fun errorPlaceholder(@DrawableRes errorPlaceholder: Int) =
        apply { this.errorPlaceholder = errorPlaceholder }

    fun into(imageView: ImageView, requireWidth: Int = 0, requireHeight: Int = 0) {
        this.imageView = imageView
        LoaderDispatcher.loadBitmap(this, requireWidth, requireHeight)
    }
}