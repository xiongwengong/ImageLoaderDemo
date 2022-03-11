package com.example.imageloader.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.imageloader.utils.calculateInSampleSize
import java.io.FileDescriptor

fun FileDescriptor.decodeSampleBitmap(reqWidth: Int, reqHeight: Int): Bitmap? {
    val options = BitmapFactory.Options().also { it.inJustDecodeBounds = true }
    BitmapFactory.decodeFileDescriptor(this, null, options)
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeFileDescriptor(this, null, options)
}
