package com.example.imagepractice

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GlideCompose(
    modifier: Modifier = Modifier,
    imageUri: Uri?,
) {
    // GlideImage의 model은 Any?로 선언되어 있지만
    // 실제로는 Drawable 객체를 전달해 주어야 하는 것으로 추정
    imageUri?.let {
        val inputStream = LocalContext.current.contentResolver.openInputStream(imageUri)
        val drawable = Drawable.createFromStream(inputStream, null)

        GlideImage(
            model = drawable,
            contentDescription = null,
            modifier = modifier
        )
    }
}