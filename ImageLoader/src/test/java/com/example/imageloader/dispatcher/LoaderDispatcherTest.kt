package com.example.imageloader.dispatcher

import android.graphics.Bitmap
import android.widget.ImageView
import com.example.imageloader.cache.IImageCache
import com.example.imageloader.cache.ImageCacheManager
import com.example.imageloader.config.DisplayConfig
import com.example.imageloader.request.RequestExecutor
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoaderDispatcherTest {
    private lateinit var displayConfig: DisplayConfig

    @MockK
    lateinit var imageCache: IImageCache

    @MockK
    lateinit var mockImageView: ImageView

    @MockK
    lateinit var mockBitmap: Bitmap

    @MockK
    lateinit var mockRequestExecutor: RequestExecutor

    private lateinit var loaderDispatcher: LoaderDispatcher

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        loaderDispatcher = spyk(LoaderDispatcher(mockRequestExecutor))

        ImageCacheManager.updateCacheStrategy(imageCache)

        displayConfig = DisplayConfig(mockk())
            .load("test.png")
            .errorPlaceholder(1)
            .placeholder(2)
        displayConfig.imageView = mockImageView
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given null when invoke loadBitmap then show error placeholder`() = runTest {
        coEvery { loaderDispatcher.loadBitmap(any()) } returns null
        loaderDispatcher.loadBitmapIntoView(displayConfig)

        verify {
            mockImageView.setImageResource(2)
            mockImageView.setImageResource(1)
        }
        verify(exactly = 0) { imageCache.get(any(), any(), any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given valid bitmap when invoke loadBitmap then show returned bitmap`() = runTest {
        coEvery { loaderDispatcher.loadBitmap(any()) } returns (mockBitmap)
        loaderDispatcher.loadBitmapIntoView(displayConfig)
        verify {
            mockImageView.setImageResource(2)
            mockImageView.setImageBitmap(mockBitmap)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given exist cached bitmap when load bitmap then show cached bitmap`() = runTest {
        every { imageCache.get(any(), any(), any()) } returns (mockBitmap)
        val bitmap = loaderDispatcher.loadBitmap("")
        assertEquals(mockBitmap, bitmap)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given valid url and not exist cached bitmap when load bitmap then show bitmap from network`() =
        runTest {
            every { imageCache.get(any(), any(), any()) } returns (null)
            coEvery { mockRequestExecutor.getBitmapInputStream(any()) } returns mockk()
            coEvery { mockRequestExecutor.convertInputStreamToBitmap(any()) } returns mockBitmap

            val bitmap = loaderDispatcher.loadBitmap("")
            assertEquals(mockBitmap, bitmap)
            coVerify {
                mockRequestExecutor.getBitmapInputStream(any())
                mockRequestExecutor.convertInputStreamToBitmap(any())
            }
        }
}