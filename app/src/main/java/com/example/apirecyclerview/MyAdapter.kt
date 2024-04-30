package com.example.apirecyclerview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter

import coil.request.ImageRequest

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MyAdapterItem(product: Product) {
    val painter: Painter = // You can add options here if needed, such as placeholder, error, etc.
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = product.thumbnail).apply(block = fun ImageRequest.Builder.() {
                // You can add options here if needed, such as placeholder, error, etc.
            }).build()
        )

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = product.title)
    }
}
