package com.example.imageloader.request

import android.os.Handler
import android.os.Looper
import com.example.imageloader.cache.ImageCacheManager
import com.example.imageloader.config.DisplayConfig
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

    fun addRequest(displayConfig: DisplayConfig) {
        displayConfig.url?.let { url ->
            val downloader = BitmapDownloader(url) { bitmap ->
                if (bitmap != null) {
                    ImageCacheManager.imageCache?.put(url, bitmap)
                    mainHandler.post {
                        displayConfig.imageView.setImageBitmap(bitmap)
                    }
                } else if (displayConfig.errorPlaceholder != 0) {
                    mainHandler.post {
                        displayConfig.imageView.setImageResource(displayConfig.errorPlaceholder)
                    }
                }
            }
            executorService.submit(downloader)
        }
    }
}