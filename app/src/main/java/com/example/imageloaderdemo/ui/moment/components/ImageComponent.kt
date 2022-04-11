package com.example.imageloaderdemo.ui.moment.components

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import com.example.imageloader.ImageLoader

@Composable
fun ImageComponent(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    placeHolder: Int = 0,
    errorHolder: Int = 0,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop,
    imageLoader: ImageLoader = ImageLoader.get(LocalContext.current)
) {
    var bitmap by remember {
        mutableStateOf(imageLoader.loadBitmap(placeHolder))
    }
    LaunchedEffect(imageUrl) {
        if (imageUrl.isNullOrEmpty()) {
            imageLoader.loadBitmap(errorHolder)?.let { bitmap = it }
        } else {
            imageLoader.loadBitmap(url = imageUrl).also { imageBitmap ->
                bitmap = imageBitmap ?: imageLoader.loadBitmap(errorHolder)
            }
        }
    }
    Layout(
        modifier = bitmap?.let {
            modifier
                .clipToBounds()
                .paint(
                    painter = BitmapPainter(image = it.asImageBitmap()),
                    alignment = alignment,
                    contentScale = contentScale,
                    alpha = 1f,
                )
        } ?: modifier
            .clipToBounds()
    ) { _, constraints ->
        layout(constraints.minWidth, constraints.minHeight) {}
    }
}