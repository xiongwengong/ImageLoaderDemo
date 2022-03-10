package com.example.imageloader.cache

import android.graphics.Bitmap
import com.example.imageloader.utils.decodeSampleBitmapFromFileDescriptor
import com.example.imageloader.utils.generateKeyByUrl
import com.jakewharton.disklrucache.DiskLruCache
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.OutputStream


class DiskCache(maxSize: Int, cacheDirPath: String) : IImageCache {
    private var diskLruCache: DiskLruCache? = null

    init {
        try {
            val cacheDir = File(cacheDirPath)
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
            diskLruCache = DiskLruCache.open(cacheDir, 1, 1, maxSize.toLong())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    override fun put(url: String, bitmap: Bitmap?) { // todo
        if (bitmap == null) {
            return
        }
        val key = url.generateKeyByUrl()
        val editor = diskLruCache?.edit(key)
        editor?.let {
            val outputStream: OutputStream = it.newOutputStream(0)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            editor.commit()
        }

    }

    override fun get(url: String, requireWidth: Int, requireHeight: Int): Bitmap? {
        // todo
        if (diskLruCache == null) return null
        val snapShot = diskLruCache!!.get(url.generateKeyByUrl()) ?: return null
        val fileInputStream = snapShot.getInputStream(0) as FileInputStream
        return fileInputStream.fd.decodeSampleBitmapFromFileDescriptor(requireWidth, requireHeight)
    }
}