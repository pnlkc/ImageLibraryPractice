package com.example.imagepractice

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CoilCompose(
    modifier: Modifier = Modifier,
    imageUri: Uri?,
) {
    val context = LocalContext.current

    AsyncImage(
        model = ImageRequest
            .Builder(context)
            .data(imageUri)
            .build(),
        contentDescription = null,
        modifier = modifier
    )
}
