package com.example.imageloader.utils

import android.graphics.BitmapFactory

fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int,
    reqHeight: Int
): Int {
    if (reqWidth <= 0 || reqHeight <= 0) {
        return 1
    }
    val width = options.outWidth
    val height = options.outHeight
    var inSampleSize = 1
    if (height > reqHeight || width > reqWidth) {
        val halfWidth = width / 2
        val halfHeight = height / 2
        while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}
