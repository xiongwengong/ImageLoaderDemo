package com.example.imageloaderdemo.ui.moment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imageloaderdemo.ui.moment.components.TweetInfoLayout
import com.example.imageloaderdemo.ui.moment.components.UserInfoHeader

@Composable
fun MomentComponent(momentViewModel: MomentViewModel = viewModel()) {
    val tweets = momentViewModel.viewState.tweets.filter {
        !it.images.isNullOrEmpty() || !it.content.isNullOrEmpty()
    }

    val userInfo = momentViewModel.viewState.userInfo

    LazyColumn(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        item {
            userInfo?.let {
                UserInfoHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((LocalConfiguration.current.screenHeightDp * 0.4).dp),
                    it
                )
            }
        }

        items(tweets) {
            TweetInfoLayout(tweet = it)
        }
    }
}

//item {
//    MomentGridLayout(itemDataList = getUrlData(), maxShowCount = 11) { subData ->
//        subData.forEach {
//            Box(
//                modifier = Modifier
//                    .background(Color.Black)
//                    .fillMaxHeight(),
//                contentAlignment = Alignment.Center
//            ) {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(it)
//                        .placeholder(R.drawable.photo_architecture)
//                        .error(R.drawable.photo_architecture)
//                        .build(),
//                    contentDescription = it,
//                    contentScale = if (subData.size == 1) ContentScale.Fit else ContentScale.Crop,
//                )
//            }
//
//        }
//    }
//}
//fun getUrlData(): List<String> {
//    val imageUrlList = mutableListOf<String>()
//    imageUrlList.add("https://images.669pic.com/element_banner/97/96/43/73/1c756b0e6d60d9163b7c7cbd16c4de51.jpg")
//    imageUrlList.add("https://ress.zhangu365.com/zhangu/txb/template/pre/20210322/75439ece15454540b1ba721fb34fb872.jpg?v=1616385307&x-oss-process=image/resize,w_400")
//    imageUrlList.add("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg")
//    imageUrlList.add("https://st3.depositphotos.com/2288675/14698/i/1600/depositphotos_146980809-stock-photo-which-way-to-go-road.jpg")
//    imageUrlList.add("https://vimsky.com/wp-content/uploads/2019/10/A-23.jpg")
//    imageUrlList.add("https://img.mp.itc.cn/upload/20170721/2b094839efb54fb18c53bbc0067939cc_th.jpg")
//    imageUrlList.add("https://pica.zhimg.com/v2-d5710017f9ef20e456207f9a02455fef_1440w.jpg?source=172ae18b")
//
//    imageUrlList.addAll(imageUrlList)
//    return imageUrlList
//}
