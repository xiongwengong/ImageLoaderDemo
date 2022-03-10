package com.example.imageloader.cache

import android.graphics.Bitmap
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class MemoryCacheTest {
    private lateinit var memoryCache: MemoryCache

    private lateinit var mockBitmap: Bitmap

    private val KEY_URL = "key_url"  // todo

    @Before
    fun setUp() {
        mockBitmap = mockk(relaxed = true)
        val maxMemory = Runtime.getRuntime().maxMemory() / 1024 // unit:kb
        memoryCache = MemoryCache((maxMemory / 10).toInt())
    }

    @Test
    fun `given not exist url when get cache then return null`() {
        memoryCache.put(KEY_URL, mockBitmap)
        val bitmap = memoryCache.get("url_key2", 100, 100)
        assertNull(bitmap)
    }

    @Test
    fun `given exist url when get cache then return value`() {
        memoryCache.put(KEY_URL, mockBitmap)
        val cachedBitmap = memoryCache.get(KEY_URL, 100, 100)
        assertEquals(mockBitmap, cachedBitmap)
    }
}