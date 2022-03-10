package com.example.imageloader.cache

import android.graphics.Bitmap
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class DoubleCacheTest {
    private lateinit var doubleCache: DoubleCache

    @MockK
    private lateinit var memoryCache: MemoryCache

    @MockK
    private lateinit var diskCache: DiskCache

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        doubleCache = DoubleCache(memoryCache, diskCache)
    }

    @Test
    fun `given there is no cache `() { // todo
        val reqSize = 100
        every { memoryCache.get(any(), any(), any()) } returns null
        every { diskCache.get(any(), any(), any()) } returns null

        val bitmap = doubleCache.get("test.png", reqSize, reqSize)

        verify { memoryCache.get(any(), reqSize, reqSize) }
        verify { diskCache.get(any(), reqSize, reqSize) }

        assertNull(bitmap)
    }

    @Test
    fun `given memory cache first when both of memory an disk cache exist`() {
        val bitmap = mockk<Bitmap>()
        doubleCache.put("test.png", bitmap)

        val reqSize = 100 // todo
        val cachedBitmap = doubleCache.get("test.png", reqSize, reqSize)

        verify(exactly = 1) { memoryCache.get(any(), reqSize, reqSize) }
        verify(exactly = 0) { diskCache.get(any(), reqSize, reqSize) }

        assertNotNull(cachedBitmap)
    }

    @Test
    fun `given disk cache  when memory cache not exist then cache to memory`() {
        val bitmap = mockk<Bitmap>()
        every { diskCache.get(any(), any(), any()) } returns bitmap
        every { memoryCache.get(any(), any(), any()) } returns null

        val reqSize = 100
        val cachedBitmap = doubleCache.get("test.png", reqSize, reqSize)

        verify(exactly = 1) { memoryCache.get(any(), reqSize, reqSize) }
        verify(exactly = 1) { memoryCache.put(any(), bitmap) }
        verify(exactly = 1) { diskCache.get(any(), reqSize, reqSize) }

        assertNotNull(cachedBitmap)
    }
}