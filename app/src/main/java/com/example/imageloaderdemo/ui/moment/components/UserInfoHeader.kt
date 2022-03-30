package com.example.imageloaderdemo.ui.moment.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.imageloaderdemo.R.*
import com.example.imageloaderdemo.ui.moment.bean.UserInfo


@Composable
fun UserInfoHeader(
    modifier: Modifier = Modifier,
    userInfo: UserInfo
) {
    ConstraintLayout(modifier) {
        val (profileBg, profileIcon, text) = createRefs()
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(userInfo.profileBg)
                .crossfade(true)
                .size(Size.ORIGINAL)
                .placeholder(drawable.ic_launcher_background)
                .error(drawable.ic_launcher_background)
                .build(),
            contentDescription = "header background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 24.dp)
                .constrainAs(profileBg) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(userInfo.avatar)
                .placeholder(drawable.ic_launcher_background)
                .error(drawable.ic_launcher_background)
                .build(),
            contentDescription = "profile icon",
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(5.dp))
                .constrainAs(profileIcon) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, 12.dp)
                }
        )


        Text(
            text = userInfo.nick, fontSize = 16.sp, color = Color.White,
            modifier = Modifier.constrainAs(text) {
                end.linkTo(profileIcon.start, 8.dp)
                top.linkTo(profileIcon.top)
                bottom.linkTo(profileIcon.bottom, 8.dp)
            }
        )
    }
}