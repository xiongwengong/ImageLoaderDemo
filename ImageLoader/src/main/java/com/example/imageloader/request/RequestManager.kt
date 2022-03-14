package com.example.imageloader.request

import android.os.Handler
import android.os.Looper
import com.example.imageloader.config.ImageCacheManager
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

object RequestManager {
    private val CORE_COUNT = Runtime.getRuntime().availableProcessors()
    private val MAXIMUM_POOL_SIZE = CORE_COUNT * 2 + 1

    private lateinit var executorService: ExecutorService
    private val mainHandler = Handler(Looper.getMainLooper())

    init {
        initThreadExecutor()
    }

    private fun initThreadExecutor() {
        executorService = ThreadPoolExecutor(
            1, MAXIMUM_POOL_SIZE, 10,
            TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>(), object : ThreadFactory {
                private val count = AtomicInteger(1)
                override fun newThread(r: Runnable?): Thread {
                    return Thread(r, "ImageLoader#${count.getAndIncrement()}")
                }
            }
        )
    }

    fun addRequest(bitmapRequest: BitmapRequest) {
        bitmapRequest.url?.let { url ->
            // todo
            val downloadThread = BitmapDownloader(url) { bitmap ->
                if (bitmap != null) {
                    ImageCacheManager.imageCache?.put(url, bitmap)
                    mainHandler.post {
                        bitmapRequest.imageView.setImageBitmap(bitmap)
                    }
                } else if (bitmapRequest.errorPlaceholder != 0) {
                    mainHandler.post {
                        bitmapRequest.imageView.setImageResource(bitmapRequest.errorPlaceholder)
                    }
                }
            }
            executorService.submit(downloadThread)
        }
    }
}