package com.example.imageloader.cache

import android.graphics.Bitmap

/**
 * custom cache interface
 *
 */
interface IImageCache {

    /**
     *  cache bitmap
     */
    fun put(url: String, bitmap: Bitmap?) // todo  null?


    /**
     * get bitmap from cache
     * maybe return null
     */
    fun get(url: String, requireWidth: Int, requireHeight: Int): Bitmap?
}