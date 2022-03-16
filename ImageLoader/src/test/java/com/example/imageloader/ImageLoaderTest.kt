package com.example.imageloader

import android.content.Context
import com.example.imageloader.cache.ImageCacheManager
import com.example.imageloader.config.DisplayConfig
import com.example.imageloader.dispatcher.LoaderDispatcher
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ImageLoaderTest {

    @MockK
    private lateinit var context: Context

    @MockK
    private lateinit var loaderDispatcher: LoaderDispatcher

    private lateinit var imageLoader: ImageLoader

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val rule: MainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        ImageCacheManager.updateCacheStrategy(mockk())
        imageLoader = ImageLoader.get(context, loaderDispatcher = loaderDispatcher)
    }

    @Test
    fun `should return new DisplayConfig when invoke createDisplayConfig`() {
        assertNotNull(imageLoader.createDisplayConfig())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should dispatch load event when invoke dispatch`() = runTest {
        coEvery { loaderDispatcher.loadBitmapIntoView(any(), any(), any()) } just runs
        val displayConfig = DisplayConfig(imageLoader)
        imageLoader.dispatch(displayConfig)

        coVerify { loaderDispatcher.loadBitmapIntoView(displayConfig) }
    }
}