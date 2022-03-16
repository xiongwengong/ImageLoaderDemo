package com.example.imageloader.request

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.InputStream

class RequestExecutorTest {

    @MockK
    private lateinit var downloadInterface: DownloadInterface

    @MockK
    private lateinit var inputStream: InputStream

    @InjectMockKs
    private lateinit var requestExecutor: RequestExecutor

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given empty inputStream when try to download bitmap then return null `() = runTest {
        every { downloadInterface.getInputStreamByUrl(any()) } returns null

        assertNull(requestExecutor.getBitmapInputStream(""))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given inputStream when try to download bitmap then return valid inputStream `() = runTest {
        every { downloadInterface.getInputStreamByUrl(any()) } returns inputStream
        assertNotNull(requestExecutor.getBitmapInputStream(""))
        verify {
            downloadInterface.getInputStreamByUrl(any())
        }
    }
}