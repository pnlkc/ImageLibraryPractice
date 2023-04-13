package com.example.imagepractice

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Picasso(
    modifier: Modifier = Modifier,
    imageUri: Uri?,
) {
    val image = remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(imageUri) {
        image.value = withContext(Dispatchers.IO) {
            Picasso.get()
                .load(imageUri)
                .get()
        }
    }

    image.value?.let { bitmap ->
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = modifier
        )
    }
}