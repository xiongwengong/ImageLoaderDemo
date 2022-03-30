package com.example.imageloaderdemo.ui.moment.bean

import com.google.gson.annotations.SerializedName

data class UserInfo(
    var nick: String = "",
    var username: String = "",
    var avatar: String = "",
    @SerializedName("profile-image")
    var profileBg: String ="",
)