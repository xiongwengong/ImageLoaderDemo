package com.example.imageloaderdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.imageloader.ImageLoader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ivCover = findViewById<ImageView>(R.id.iv_cover);

//        Glide.with(this).load("")
//            .error()
//            .placeholder()
//            .into(ivCover);

        ImageLoader.with(this).load("").into(ivCover)
    }
}