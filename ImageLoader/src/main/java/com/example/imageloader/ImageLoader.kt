package com.example.imageloader

import android.content.Context
import com.example.imageloader.request.BitmapRequest

object ImageLoader {
    fun with(context: Context) = BitmapRequest(context.applicationContext)
}