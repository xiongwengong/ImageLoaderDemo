package com.example.imageloaderdemo.ui.moment.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.imageloaderdemo.R

@Composable
fun NineGridLayout(
    modifier: Modifier = Modifier,
    itemDataList: List<GridItemData>,
    itemSpacing: Dp = 2.dp,
    maxShowCount: Int = 9,
    content: @Composable (itemData: GridItemData) -> Unit = {
        // todo
        println("=====image url ===${it.url}")
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(it.url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .build(),
            contentDescription = it.dec,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        Text(text = it.dec)
    }
) {

    val itemCount = if (itemDataList.size > maxShowCount) maxShowCount else itemDataList.size
    Layout(
        content = {
            itemDataList.subList(0, itemCount).forEach {
                Box(
                    modifier = Modifier
                        .background(Color.Red)
                        .padding(2.dp)
                ) {
                    content(it)
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
//            .height(IntrinsicSize.Min)
    )
    { measurables, constraints ->
        val defaultItemSize = constraints.maxWidth / 3
        var itemHeight = defaultItemSize
        var itemWidth = defaultItemSize
        val rowCount = kotlin.math.ceil(itemCount / 3.0).toInt()
        val placeables = measurables.map { measurable ->
            // Measure each children
            val itemConstraints =
                constraints.copy(
                    maxWidth = if (itemCount == 1) (constraints.maxWidth*0.8f).toInt() else itemWidth,
                    minWidth = constraints.maxWidth / 3,
                    maxHeight = if (itemCount == 1) (constraints.maxWidth*0.8f).toInt() else itemHeight,
                    minHeight = constraints.maxWidth / 3
                )
            val placeable = measurable.measure(itemConstraints)
            if (itemCount == 1) {
                itemHeight = placeable.height
                itemWidth = placeable.width
            }
            println("=====item size : $itemWidth * $itemHeight")
            placeable
        }

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, itemHeight * rowCount) {
            // Track the y co-ord we have placed children up to
            var yPosition: Int
            var xPosition: Int
            // Place children in the parent layout
            placeables.forEachIndexed { index, placeable ->
                xPosition = calculateXPos(index, itemWidth, itemCount)
                yPosition = calculateYPos(index, itemHeight, itemCount)
                // Position item on the screen
                placeable.place(x = xPosition, y = yPosition)
            }
        }
    }
}

private fun calculateYPos(currentIndex: Int, itemHeight: Int, itemSize: Int): Int {
    if (itemSize == 4) {
        return currentIndex / 2 * itemHeight
    }
    return currentIndex / 3 * itemHeight
}


private fun calculateXPos(currentIndex: Int, itemWidth: Int, itemSize: Int): Int {
    if (itemSize == 4) {
        return currentIndex % 2 * itemWidth
    }
    return currentIndex % 3 * itemWidth
}

data class GridItemData(val url: String, val dec: String = "")