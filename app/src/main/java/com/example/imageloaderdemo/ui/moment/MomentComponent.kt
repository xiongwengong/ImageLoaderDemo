package com.example.imageloaderdemo.ui.moment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.imageloaderdemo.net.MomentRepository
import com.example.imageloaderdemo.ui.moment.components.TweetInfoLayout
import com.example.imageloaderdemo.ui.moment.components.UserInfoHeader

@Composable
fun MomentComponent(momentRepository: MomentRepository = MomentRepository()) {

    val userInfo = momentRepository.getUserInfo()

    val tweets = momentRepository.getTweets().filter {
        !it.images.isNullOrEmpty() || !it.content.isNullOrEmpty()
    }
    LazyColumn(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        item {
            UserInfoHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((LocalConfiguration.current.screenHeightDp * 0.4).dp),
                userInfo
            )
        }

        items(tweets) {
            TweetInfoLayout(tweet = it)
        }
    }
}
