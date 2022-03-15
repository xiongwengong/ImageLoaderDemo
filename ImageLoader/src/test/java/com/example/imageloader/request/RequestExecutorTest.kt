package com.example.imageloader.request

import com.example.imageloader.MainCoroutineRule
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class RequestExecutorTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given valid url when load bitmap then return bitmap for network`() = runTest {
        val requestExecutor = RequestExecutor()

        val url = "https://vimsky.com/wp-content/uploads/2019/10/A-23.jpg"
        val bitmap = requestExecutor.getResults(url)
        assertNotNull(bitmap)
    }
}