package com.example.imageloader.request

import android.widget.ImageView
import com.example.imageloader.ImageLoader
import com.example.imageloader.config.DisplayConfig
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DisplayConfigTest {
    private lateinit var displayConfig: DisplayConfig

    @MockK
    private lateinit var mockedImageView: ImageView

    @MockK
    private lateinit var mockedImageLoader: ImageLoader

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        displayConfig = DisplayConfig(mockedImageLoader)
    }


    @Test
    fun `given configured DisplayConfig when load bitmap then do dispatch`() {
        every { mockedImageLoader.dispatch(any(),any(),any()) } just runs

        displayConfig.load(TEST_URL)
            .placeholder(PLACEHOLDER)
            .errorPlaceholder(ERROR_PLACEHOLDER)
            .into(mockedImageView)

        val slot = slot<DisplayConfig>()
        verify { mockedImageLoader.dispatch(capture(slot),any(),any()) }

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