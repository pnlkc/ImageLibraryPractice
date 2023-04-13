package com.example.imagepractice

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.imagepractice.ui.theme.ImagePracticeTheme

/**
 * 이미지 처리 라이브러리 연습 프로젝트
 * 1. Picasso
 * 2. Glide
 * 3. Coil
 * 4. Fresco (불확실)
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImagePracticeTheme {
                MainScreen()
            }
        }
    }
}


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val imageUrl = remember { mutableStateOf<Uri?>(null) }
    val pickMedia = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            imageUrl.value = uri
        } else {
            Log.d("로그", "이미지 로드 안됨")
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // 이미지 불러오기 버튼
        Button(
            onClick = {
                pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            }
        ) {
            Text(text = "이미지 불러오기")
        }

        LazyVerticalGrid(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            columns = GridCells.Fixed(2)
        ) {
            item {
                // Picasso 라이브러리 사용
                Picasso(imageUri = imageUrl.value)
            }

            item {
                // Glide 라이브러리 사용
                Glide(imageUri = imageUrl.value)
            }

            item {
                // Glide 라이브러리의 Compose 전용 GlideImage 사용
                GlideCompose(imageUri = imageUrl.value)
            }

            item {
                // Coil 라이브러리 사용
                Coil(imageUri = imageUrl.value)
            }

            item {
                // Coil 라이브러리의 Compose 전용 AsyncImage 사용
                CoilCompose(imageUri = imageUrl.value)
            }
        }
    }
}
