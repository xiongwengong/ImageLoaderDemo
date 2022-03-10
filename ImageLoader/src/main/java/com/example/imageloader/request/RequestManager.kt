package com.example.imageloader.request

import android.graphics.Bitmap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

object RequestManager {
    private const val MAX_THREAD_POOL_SIZE = 4
    private val requestQueue = LinkedBlockingQueue<BitmapRequest>()

    private lateinit var executorService: ExecutorService

    init {
        initThreadExecutor()
    }

    private fun initThreadExecutor() {
        executorService = Executors.newFixedThreadPool(MAX_THREAD_POOL_SIZE)
    }

    fun addRequest(bitmapRequest: BitmapRequest) {
        println("RequestManager# addRequest")
//        executorService.submit()
    }

    fun getBitmap(url: String, requireWidth: Int, requireHeight: Int): Bitmap? {
        // TODO:
        return null
    }
}