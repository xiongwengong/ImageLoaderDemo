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

    @Before
    fun setUp() {
        mockBitmap = mockk(relaxed = true)
        val cachePath = ApplicationProvider.getApplicationContext<Context>().cacheDir
            .absolutePath + File.separator + "twImageLoader"
        diskCache = DiskCache(DISK_CACHE_MAX_SIZE, cachePath)
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

        val cachedBitmap =
            diskCache.get(KEY_URL, REQUIRE_BITMAP_SIZE, REQUIRE_BITMAP_SIZE)
        cachedBitmap?.let {
            assertTrue(it.width <= REQUIRE_BITMAP_SIZE && it.height <= REQUIRE_BITMAP_SIZE)
        }
    }

    companion object {
        private const val KEY_URL = "KEY_URL"
        private const val REQUIRE_BITMAP_SIZE = 100
        private const val DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024 // 50MB
    }
}