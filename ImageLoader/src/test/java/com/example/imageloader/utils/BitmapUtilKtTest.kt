package com.example.imageloader.utils


import android.graphics.BitmapFactory
import com.example.imageloader.BuildConfig
import com.google.common.io.Resources.getResource
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.io.FileInputStream

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class BitmapUtilKtTest {

    @Test
    fun `given FileInputStream when decode bitmap then compress`() {
        val imagePath = "./img/pic.jpeg"
        val file = File(getResource(imagePath)?.toURI()?.path)
        val fileInputStream = FileInputStream(file)
        val reqSize = 100

        val bitmap = fileInputStream.fd.decodeSampleBitmapFromFileDescriptor(reqSize, reqSize)

        bitmap?.let {
            assert(it.width <= reqSize && it.height <= reqSize)
        }
    }

    @Test
    fun `given reqWith or reqHeight 0 when calculate InSampleSize then return 1`() {
        val options = mockk<BitmapFactory.Options>()
        options.outWidth = 1000
        options.outHeight = 1000

        val inSimpleResult = calculateInSampleSize(options, 0, 0)
        assert(inSimpleResult == 1)
    }

    @Test
    fun `given reqWith and reqHeight then calculate InSampleSize`() {
        val options = mockk<BitmapFactory.Options>()
        options.outWidth = 1000
        options.outHeight = 1000

        val inSimpleResult = calculateInSampleSize(options, 100, 100)
        assert(inSimpleResult == 8)
    }
}