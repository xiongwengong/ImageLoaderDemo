package com.example.imageloaderdemo.net.bean

data class Tweet(
    var content: String? = "",
    var sender: Sender? = null,
    var images: List<TweetImage>? = emptyList(),
    var comments: List<Comment>? = null
)
