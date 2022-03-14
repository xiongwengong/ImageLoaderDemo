package com.example.imageloader.cache

import android.graphics.Bitmap
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class DoubleCacheTest {
    @InjectMockKs
    private lateinit var doubleCache: DoubleCache

    @MockK
    private lateinit var memoryCache: MemoryCache

    @MockK
    private lateinit var diskCache: DiskCache

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
//        doubleCache = DoubleCache(memoryCache, diskCache)
    }

    @Test
    fun `given there is no cache when try to get cache then return null`() {
        every { memoryCache.get(any(), any(), any()) } returns null
        every { diskCache.get(any(), any(), any()) } returns null

        val bitmap = doubleCache.get("test.png", REQUIRE_BITMAP_SIZE, REQUIRE_BITMAP_SIZE)

        verify { memoryCache.get(any(), REQUIRE_BITMAP_SIZE, REQUIRE_BITMAP_SIZE) }
        verify { diskCache.get(any(), REQUIRE_BITMAP_SIZE, REQUIRE_BITMAP_SIZE) }

        assertNull(bitmap)
    }

    @Test
    fun `given memory cache first when both of memory an disk cache exist`() {
        val bitmap = mockk<Bitmap>()
        doubleCache.put("test.png", bitmap)

        val cachedBitmap = doubleCache.get("test.png", REQUIRE_BITMAP_SIZE, REQUIRE_BITMAP_SIZE)

        verify(exactly = 1) { memoryCache.get(any(), REQUIRE_BITMAP_SIZE, REQUIRE_BITMAP_SIZE) }
        verify(exactly = 0) { diskCache.get(any(), REQUIRE_BITMAP_SIZE, REQUIRE_BITMAP_SIZE) }

        assertNotNull(cachedBitmap)
    }

    @Test
    fun `given disk cache  when memory cache not exist then cache to memory`() {
        val bitmap = mockk<Bitmap>()
        every { diskCache.get(any(), any(), any()) } returns bitmap
        every { memoryCache.get(any(), any(), any()) } returns null

        val cachedBitmap = doubleCache.get("test.png", REQUIRE_BITMAP_SIZE, REQUIRE_BITMAP_SIZE)

        verify(exactly = 1) { memoryCache.get(any(), REQUIRE_BITMAP_SIZE, REQUIRE_BITMAP_SIZE) }
        verify(exactly = 1) { memoryCache.put(any(), bitmap) }
        verify(exactly = 1) { diskCache.get(any(), REQUIRE_BITMAP_SIZE, REQUIRE_BITMAP_SIZE) }

        assertNotNull(cachedBitmap)
    }

    companion object {
        private const val REQUIRE_BITMAP_SIZE = 100
    }
}