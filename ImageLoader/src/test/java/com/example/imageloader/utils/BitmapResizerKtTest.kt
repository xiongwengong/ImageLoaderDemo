package com.example.imageloader.utils


import android.graphics.BitmapFactory
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class BitmapResizerKtTest {

    @Test
    fun `given reqWith or reqHeight 0 when calculate InSampleSize then return 1`() {
        val options = mockk<BitmapFactory.Options>()
        options.outWidth = MOCKED_BITMAP_SIZE
        options.outHeight = MOCKED_BITMAP_SIZE

        val inSimpleResult = calculateInSampleSize(options, 0, 0)
        assertEquals(1, inSimpleResult)
    }

    @Test
    fun `given reqWith and reqHeight then calculate InSampleSize`() {
        val options = mockk<BitmapFactory.Options>()
        options.outWidth = MOCKED_BITMAP_SIZE
        options.outHeight = MOCKED_BITMAP_SIZE

        val inSimpleResult =
            calculateInSampleSize(options, REQUIRE_BITMAP_SIZE, REQUIRE_BITMAP_SIZE)
        assertEquals(8, inSimpleResult)
    }

    companion object {
        private const val MOCKED_BITMAP_SIZE = 1000
        private const val REQUIRE_BITMAP_SIZE = 100
    }
}