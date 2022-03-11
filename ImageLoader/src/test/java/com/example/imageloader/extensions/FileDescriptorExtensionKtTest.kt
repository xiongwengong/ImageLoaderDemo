package com.example.imageloader.extensions

import com.google.common.io.Resources.getResource
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class FileDescriptorExtensionKtTest {
    @Test
    fun `given FileInputStream when decode bitmap then compress`() {
        val imagePath = "./img/pic.jpeg"
        val filePath =getResource(imagePath)?.toURI()?.path
        if(filePath.isNullOrEmpty()){
            throw FileNotFoundException("file was not found")
        }

        val file = File(filePath)
        val fileInputStream = FileInputStream(file)

        val bitmap = fileInputStream.fd.decodeSampleBitmap(REQUIRE_BITMAP_SIZE, REQUIRE_BITMAP_SIZE)

        bitmap?.let {
            assertTrue(it.width <= REQUIRE_BITMAP_SIZE && it.height <= REQUIRE_BITMAP_SIZE)
        }
    }

    companion object {
        private const val REQUIRE_BITMAP_SIZE = 100
    }
}