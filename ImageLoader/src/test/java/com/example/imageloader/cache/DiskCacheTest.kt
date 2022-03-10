package com.example.imageloader.cache

import android.content.Context
import android.graphics.Bitmap
import androidx.test.core.app.ApplicationProvider
import io.mockk.mockk
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.io.File


@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class DiskCacheTest {
    private lateinit var diskCache: DiskCache

    private lateinit var mockBitmap: Bitmap

    private val KEY_URL = "key_url" // todo

    private val diskCacheMaxSize = 50 * 1024 * 1024 // 50MB

    @Before
    fun setUp() {
        mockBitmap = mockk(relaxed = true)
        val cachePath = ApplicationProvider.getApplicationContext<Context>().cacheDir
            .absolutePath + File.separator + "twImageLoader"
        diskCache = DiskCache(diskCacheMaxSize, cachePath)
    }

    @Test
    fun `given not exist url when get cache then return null`() {
        diskCache.put(KEY_URL, mockBitmap)
        val bitmap = diskCache.get("url_key2", 100, 100)
        assertNull(bitmap)
    }

    @Test
    fun `given exist url when get cache then return value`() {
        diskCache.put(KEY_URL, mockBitmap)

        val reqSize = 100
        val cachedBitmap = diskCache.get(KEY_URL, reqSize, reqSize)
        cachedBitmap?.let {
            assertTrue(it.width <= reqSize && it.height <= reqSize)
        }
    }
}