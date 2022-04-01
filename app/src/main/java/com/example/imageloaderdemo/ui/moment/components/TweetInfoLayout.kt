package com.example.imageloaderdemo.ui.moment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.imageloaderdemo.R
import com.example.imageloaderdemo.net.MomentRepository
import com.example.imageloaderdemo.net.bean.Comment
import com.example.imageloaderdemo.net.bean.Tweet
import com.example.imageloaderdemo.theme.tweetSenderNameColor

@Composable
fun TweetInfoLayout(tweet: Tweet, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 5.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(tweet.sender?.avatar)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .build(),
            contentDescription = "sender profile icon",
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(5.dp))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Tweet(tweet)
    }
}

@Preview
@Composable
private fun TweetInfoLayoutPreview() {
    val tweets = MomentRepository().getLocalTweets()
    TweetInfoLayout(tweet = tweets.first())
}

@Preview
@Composable
private fun TweetPreview() {
    val tweets = MomentRepository().getLocalTweets()
    Tweet(tweet = tweets.first())
}

@Composable
fun Tweet(tweet: Tweet, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(5.dp)) {
        Text(
            text = tweet.sender?.nick?:"",
            style = TextStyle(color = tweetSenderNameColor, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(10.dp))
        tweet.content?.also {
            Text(text = it)
            Spacer(modifier = Modifier.height(10.dp))
        }
        tweet.images?.also {
            MomentGridLayout(itemDataList = it) { subData ->
                subData.forEach{ tweetImage->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(tweetImage.url)
                            .placeholder(R.drawable.photo_architecture)
                            .error(R.drawable.photo_architecture)
                            .build(),
                        contentDescription = tweetImage.url,
                        contentScale = if (subData.size == 1) ContentScale.Fit else ContentScale.Crop,
                    )
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        if (!tweet.comments.isNullOrEmpty()) {
            Comments(tweet.comments!!)
            Spacer(modifier = Modifier.height(10.dp))
        }
        Divider()
    }
}

@Composable
fun Comments(commentList: List<Comment>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(Color.Gray)
            .padding(5.dp)
    ) {
        commentList.forEach { commentBean ->
            val annotationStr = buildAnnotatedString {
                withStyle(style = SpanStyle(color = tweetSenderNameColor)) {
                    append(commentBean.sender?.nick ?: "")
                }
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append(": ${commentBean.content}")
                }
            }
            Text(text = annotationStr)
        }
    }
}
