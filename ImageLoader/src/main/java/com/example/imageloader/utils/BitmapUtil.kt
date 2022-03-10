package com.example.imageloader.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.FileDescriptor


fun FileDescriptor.decodeSampleBitmapFromFileDescriptor(reqWidth: Int, reqHeight: Int): Bitmap? {  // todo
    val options = BitmapFactory.Options().also { it.inJustDecodeBounds = true }
    BitmapFactory.decodeFileDescriptor(this, null, options)
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeFileDescriptor(this, null, options)
}

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
