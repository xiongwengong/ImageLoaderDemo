package com.example.imageloaderdemo.net

import com.example.imageloaderdemo.net.bean.Tweet
import com.example.imageloaderdemo.net.bean.UserInfo
import retrofit2.http.GET

interface MomentService {

    /**
     * https://tw-mobile-xian.github.io/moments-data/user.json
     */
    @GET("moments-data/user.json")
    suspend fun getUserInfo(): UserInfo

    /**
     * https://tw-mobile-xian.github.io/moments-data/tweets.json
     */
    @GET("moments-data/tweets.json")
    suspend fun getTweets(): List<Tweet>
}
