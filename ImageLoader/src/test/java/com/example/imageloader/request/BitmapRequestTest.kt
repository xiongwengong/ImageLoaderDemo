package com.example.imageloader.request

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.test.core.app.ApplicationProvider
import com.example.imageloader.cache.IImageCache
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class BitmapRequestTest {
    private lateinit var bitmapRequest: BitmapRequest

    @MockK
    lateinit var imageCache: IImageCache

    @MockK
    lateinit var imageView: ImageView

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        bitmapRequest = BitmapRequest(ApplicationProvider.getApplicationContext())
            .cacheStrategy(imageCache)
            .errorPlaceholder(1)
            .placeholder(2)
    }


    @Test
    fun `when empty url show error placeholder`() {
        bitmapRequest.load("")
            .into(imageView)

        verify { imageView.setImageResource(1) }

        verify(exactly = 0) { imageCache.get(any(), any(), any()) }
    }

    @Test
    fun `given cache bitmap when cache exist`() {
        val mockBitmap = mockk<Bitmap>()
        every { imageCache.get(any(), any(), any()) } returns (mockBitmap)

        bitmapRequest.load("test.png")
            .into(imageView)
        verify { imageView.setImageBitmap(mockBitmap) }
    }

    @Test
    fun `given network bitmap when has no cache`() {
        mockkObject(RequestManager)
        every { RequestManager.addRequest(any()) } just Runs

        every { imageCache.get(any(), any(), any()) } returns (null)
        bitmapRequest.load("test.png")
            .into(imageView)

        verify(exactly = 0) { imageView.setImageBitmap(any()) }
        verify { RequestManager.addRequest(any()) }
    }
}