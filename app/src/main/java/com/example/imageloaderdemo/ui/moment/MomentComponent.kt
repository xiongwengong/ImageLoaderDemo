package com.example.imageloaderdemo.ui.moment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.imageloaderdemo.ui.moment.bean.UserInfo
import com.example.imageloaderdemo.ui.moment.components.GridItemData
import com.example.imageloaderdemo.ui.moment.components.NineGridLayout
import com.example.imageloaderdemo.ui.moment.components.UserInfoHeader

@Composable
fun MomentComponent() {

    val fakeUserInfo = UserInfo(
        profileBg = "http://img2.findthebest.com/sites/default/files/688/media/images/Mingle_159902_i0.png",
        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png",
        nick = "John Smith",
        username = "Smith"
    )

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
                fakeUserInfo
            )
        }

        item {
            Text(text = "single image content start")
            NineGridLayout(itemDataList = getFakeData().subList(0, 1))
            Text(text = "single image content end")
        }
        item {
            Text(text = "single image content start")
            NineGridLayout(itemDataList = getFakeData().subList(1, 2))
            Text(text = "single image content end")
        }
        item {
            Text(text = "4 pics content start")
            NineGridLayout(itemDataList = getFakeData().subList(0, 4))
            Text(text = "4 pics content end")
        }
        item {
            Text(text = "10 pics content start")
            NineGridLayout(itemDataList = getFakeData())
            Text(text = "10 pics content end")
        }
    }
}


@Preview
@Composable
fun NineGridPreview() {
}

fun getFakeData(): List<GridItemData> {
    return getUrlData().mapIndexed { index,it->
        GridItemData(it,"index = $index")
    }
}

fun getUrlData(): List<String> {
    val imageUrlList = mutableListOf<String>()
    imageUrlList.add("https://images.669pic.com/element_banner/97/96/43/73/1c756b0e6d60d9163b7c7cbd16c4de51.jpg")
    imageUrlList.add("https://ress.zhangu365.com/zhangu/txb/template/pre/20210322/75439ece15454540b1ba721fb34fb872.jpg?v=1616385307&x-oss-process=image/resize,w_400")
    imageUrlList.add("https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg")
    imageUrlList.add("https://st3.depositphotos.com/2288675/14698/i/1600/depositphotos_146980809-stock-photo-which-way-to-go-road.jpg")
    imageUrlList.add("https://vimsky.com/wp-content/uploads/2019/10/A-23.jpg")
    imageUrlList.add("https://img.mp.itc.cn/upload/20170721/2b094839efb54fb18c53bbc0067939cc_th.jpg")
    imageUrlList.add("https://pica.zhimg.com/v2-d5710017f9ef20e456207f9a02455fef_1440w.jpg?source=172ae18b")

    imageUrlList.addAll(imageUrlList)
    return imageUrlList
}
