package com.example.imageloaderdemo.net.bean

import com.google.gson.annotations.SerializedName

data class UserInfo(
    var nick: String = "",
    var username: String = "",
    var avatar: String = "",
    @SerializedName("profile-image")
    var profileBg: String ="",
)