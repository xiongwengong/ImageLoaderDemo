package com.example.imageloaderdemo.ui.moment.components

import androidx.annotation.IntRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> NineGridLayout(
    modifier: Modifier = Modifier,
    itemDataList: List<T>,
    itemSpacing: Dp = 5.dp,
    @IntRange(from = 1, to = 9) maxShowCount:  Int  = 9,
    content: @Composable (position: Int, subData: List<T>) -> Unit
) {

    val itemCount = if (itemDataList.size > maxShowCount) maxShowCount else itemDataList.size
    Layout(
        content = {
            val subData = itemDataList.subList(0, itemCount)

            subData.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .background(Color.Black),
                    contentAlignment = Alignment.Center,
                ) {
                    content(index, subData)
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
    ) { measurables, constraints ->

        var (itemHeight, itemWidth, rowCount) = LayoutCalculator(
            constraints.maxWidth, itemSpacing.toPx(), itemCount
        )
        val placeableList = measurables.map { measurable ->
            // Measure each children
            val itemConstraints =
                constraints.copy(
                    maxWidth = if (itemCount == 1) (constraints.maxWidth * 0.7f).toInt() else itemWidth,
                    minWidth = itemWidth / 2,
                    maxHeight = if (itemCount == 1) (constraints.maxWidth * 0.8f).toInt() else itemHeight,
                    minHeight = itemHeight / 2
                )
            val placeable = measurable.measure(itemConstraints)
            if (itemCount == 1) {
                itemHeight = placeable.height
                itemWidth = placeable.width
            }
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
