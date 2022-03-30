package com.example.imageloaderdemo.ui.moment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    itemSpacing: Dp = 5.dp,
    maxShowCount: Int = 9,
    content: @Composable (itemData: GridItemData, contentScale: ContentScale) -> Unit = defaultImageViewFunc
) {

    val itemCount = if (itemDataList.size > maxShowCount) maxShowCount else itemDataList.size
    Layout(
        content = {
            itemDataList.subList(0, itemCount).forEach {
                Box(
                    modifier = Modifier
                        .background(Color.Red)
                        .padding(2.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    content(it, if (itemCount == 1) ContentScale.Fit else ContentScale.Crop)
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.DarkGray)
    ) { measurables, constraints ->

        var (itemHeight, itemWidth, rowCount) = LayoutCalculator(
            constraints.maxWidth, itemSpacing.toPx(), itemCount
        )
        val placeableList = measurables.map { measurable ->
            // Measure each children
            val itemConstraints =
                constraints.copy(
                    maxWidth = if (itemCount == 1) (constraints.maxWidth * 0.6f).toInt() else itemWidth,
                    minWidth = itemWidth,
                    maxHeight = if (itemCount == 1) (constraints.maxWidth * 0.8f).toInt() else itemHeight,
                    minHeight = itemHeight
                )
            val placeable = measurable.measure(itemConstraints)
            if (itemCount == 1) {
                itemHeight = placeable.height
                itemWidth = placeable.width
            }
            println("=====item size : $itemWidth * $itemHeight")
            placeable
        }

        val layoutHeight = (itemHeight * rowCount).plus(itemSpacing.toPx() * (rowCount - 1)).toInt()
        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, layoutHeight) {
            // Place children in the parent layout
            placeableList.forEachIndexed { index, placeable ->
                val xPosition = calculateXPos(index, itemWidth, itemCount, itemSpacing.toPx())
                val yPosition = calculateYPos(index, itemHeight, itemCount, itemSpacing.toPx())
                // Position item on the screen
                placeable.place(x = xPosition, y = yPosition)
            }
        }
    }
}

internal class LayoutCalculator internal constructor(
    maxWidth: Int,
    itemSpacing: Float,
    private val itemCount: Int
) {
    private val spaceCount = if (itemCount == 1) 0 else 2
    private val defaultItemSize =
        (maxWidth.minus(itemSpacing * spaceCount).toInt()) / 3

    operator fun component1(): Int = defaultItemSize

    operator fun component2(): Int = defaultItemSize

    operator fun component3(): Int = kotlin.math.ceil(itemCount / 3.0).toInt()
}

private fun calculateYPos(
    currentIndex: Int,
    itemHeight: Int,
    itemCount: Int,
    itemSpacing: Float
): Int {
    if (itemCount == 4) {
        return currentIndex / 2 * (itemHeight + itemSpacing).toInt()
    }
    return currentIndex / 3 * (itemHeight + itemSpacing).toInt()
}


private fun calculateXPos(
    currentIndex: Int,
    itemWidth: Int,
    itemCount: Int,
    itemSpacing: Float
): Int {
    if (itemCount == 4) {
        return currentIndex % 2 * (itemWidth + itemSpacing).toInt()
    }
    return currentIndex % 3 * (itemWidth + itemSpacing).toInt()
}

private val defaultImageViewFunc: @Composable (gridItemData: GridItemData, contentScale: ContentScale) -> Unit =
    { it, contentScale ->
        println("=====image url ===${it.url}")
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(it.url)
                .placeholder(R.drawable.photo_architecture)
                .error(R.drawable.photo_architecture)
                .build(),
            contentDescription = it.dec,
            contentScale = contentScale,
        )
        Text(text = it.dec)
    }


data class GridItemData(val url: String, val dec: String = "")