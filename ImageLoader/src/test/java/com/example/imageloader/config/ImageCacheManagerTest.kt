package com.example.imageloader.config

import com.example.imageloader.cache.IImageCache
import com.example.imageloader.cache.ImageCacheManager
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test

class ImageCacheManagerTest {
    @Test
    fun `given valid imageCache instance when update cache strategy then the strategy is updated`() {
        val mockedImageCache = mockk<IImageCache>()
        ImageCacheManager.updateCacheStrategy(mockedImageCache)
        assertEquals(mockedImageCache, ImageCacheManager.imageCache)
    }
}