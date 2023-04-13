package com.example.imagepractice

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import coil.request.ImageRequest
import coil.Coil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Coil(
    modifier: Modifier = Modifier,
    imageUri: Uri?,
) {
    val image = remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    val coilImageRequest = ImageRequest
        .Builder(context)
        .data(imageUri)
        .build()

    LaunchedEffect(imageUri) {
        image.value = withContext(Dispatchers.IO) {
            // coilImageResult 변수 선언 없이 그냥 리턴해도 됨됨
           val coilImageResult = Coil
                .imageLoader(context)
                .execute(coilImageRequest)
                .drawable
                ?.toBitmap()

            coilImageResult
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