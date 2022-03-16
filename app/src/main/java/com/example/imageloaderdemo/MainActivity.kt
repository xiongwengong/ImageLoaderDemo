package com.example.imageloaderdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloaderdemo.adpater.ImagesAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvImage = findViewById<RecyclerView>(R.id.rv_image)

        val imagesAdapter = ImagesAdapter()
        rvImage.layoutManager = LinearLayoutManager(this).apply { orientation = VERTICAL }
        rvImage.adapter = imagesAdapter

        findViewById<Button>(R.id.btn_load_pic).setOnClickListener {
            imagesAdapter.imageUrlList = getFakeData()
        }
    }

    fun getFakeData(): List<String> {
        val imageUrlList = mutableListOf<String>()
        imageUrlList.add("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg")
        imageUrlList.add("https://st3.depositphotos.com/2288675/14698/i/1600/depositphotos_146980809-stock-photo-which-way-to-go-road.jpg")
        imageUrlList.add("https://vimsky.com/wp-content/uploads/2019/10/A-23.jpg")
        imageUrlList.add("https://img.mp.itc.cn/upload/20170721/2b094839efb54fb18c53bbc0067939cc_th.jpg")
        imageUrlList.add("https://pica.zhimg.com/v2-d5710017f9ef20e456207f9a02455fef_1440w.jpg?source=172ae18b")

        for (i in 0..2) {
            imageUrlList.addAll(imageUrlList)
        }
        return imageUrlList
    }
}