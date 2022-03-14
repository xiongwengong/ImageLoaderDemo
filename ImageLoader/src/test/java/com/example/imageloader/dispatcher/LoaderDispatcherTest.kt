package com.example.imageloader.dispatcher

import android.graphics.Bitmap
import android.widget.ImageView
import com.example.imageloader.cache.IImageCache
import com.example.imageloader.cache.ImageCacheManager
import com.example.imageloader.config.DisplayConfig
import com.example.imageloader.request.RequestManager
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class LoaderDispatcherTest {
    private lateinit var displayConfig: DisplayConfig

    @MockK
    lateinit var imageCache: IImageCache

    @MockK
    lateinit var mockImageView: ImageView

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        ImageCacheManager.updateCacheStrategy(imageCache)

        displayConfig = DisplayConfig()
            .load("test.png")
            .errorPlaceholder(1)
            .placeholder(2)
        displayConfig.imageView = mockImageView
    }


    @Test
    fun `given empty url when load bitmap then show error placeholder`() {
        displayConfig.url = ""

        LoaderDispatcher.loadBitmap(displayConfig)

        verify { mockImageView.setImageResource(1) }
        verify(exactly = 0) { imageCache.get(any(), any(), any()) }
    }

    @Test
    fun `given valid url and exist cached bitmap when load bitmap then show cached bitmap`() {
        val mockBitmap = mockk<Bitmap>()
        every { imageCache.get(any(), any(), any()) } returns (mockBitmap)

        LoaderDispatcher.loadBitmap(displayConfig)

        verify { mockImageView.setImageBitmap(mockBitmap) }
    }

    @Test
    fun `given valid url and not exist cached bitmap when load bitmap then show bitmap from network`() {
        mockkObject(RequestManager,)
        every { RequestManager.addRequest(displayConfig) } just runs

        every { imageCache.get(any(), any(), any()) } returns (null)

        LoaderDispatcher.loadBitmap(displayConfig)

        verify(exactly = 0) { mockImageView.setImageBitmap(any()) }
        verify { RequestManager.addRequest(displayConfig) }
    }
}