package com.example.imageloader.request

import android.widget.ImageView
import com.example.imageloader.config.DisplayConfig
import com.example.imageloader.dispatcher.LoaderDispatcher
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class DisplayConfigTest {
    private lateinit var displayConfig: DisplayConfig
    private lateinit var mockedImageView: ImageView

    @Before
    fun setUp() {
        mockedImageView = mockk()
        displayConfig = DisplayConfig()
    }


    @Test
    fun `given configured DisplayConfig when load bitmap then do dispatch`() {
        mockkObject(LoaderDispatcher)
        every { LoaderDispatcher.loadBitmap(any(),any(),any()) } just runs

        displayConfig.load(TEST_URL)
            .placeholder(PLACEHOLDER)
            .errorPlaceholder(ERROR_PLACEHOLDER)
            .into(mockedImageView)

        val slot = slot<DisplayConfig>()
        verify { LoaderDispatcher.loadBitmap(capture(slot)) }

        val argumentCapture = slot.captured
        assertEquals(ERROR_PLACEHOLDER, argumentCapture.errorPlaceholder)
        assertEquals(PLACEHOLDER, argumentCapture.placeholder)
        assertEquals(TEST_URL, argumentCapture.url)
    }

    companion object {
        private const val ERROR_PLACEHOLDER = 1
        private const val PLACEHOLDER = 2
        private const val TEST_URL = "test.png"
    }
}