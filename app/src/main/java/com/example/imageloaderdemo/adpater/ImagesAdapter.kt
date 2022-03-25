package com.example.imageloaderdemo.adpater

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloader.ImageLoader
import com.example.imageloaderdemo.R

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ImageHolder>() {
    private var onImageClick: ((AppCompatImageView) -> Unit)? = null

    var imageUrlList: List<String>? = null
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val imageView = AppCompatImageView(parent.context).apply {
            this.scaleType = ImageView.ScaleType.CENTER_CROP
            this.layoutParams =
                RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            setOnClickListener {
                onImageClick?.invoke(it as AppCompatImageView)
            }
        }
        return ImageHolder(imageView)
    }

    override fun getItemCount(): Int {
        return imageUrlList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        (holder.itemView as AppCompatImageView).let {
            val url = imageUrlList!![position]
            it.tag = url
            ImageLoader.with(it.context)
                .load(url)
                .errorPlaceholder(R.mipmap.error)
                .placeholder(R.mipmap.placeholder)
                .into(it)

//            Glide.with(it.context)
//                .load(url)
//                .into(it)
        }
    }

    inner class ImageHolder(view: View) : RecyclerView.ViewHolder(view)
}
