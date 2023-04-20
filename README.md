# ImageLibraryPractice
안드로이드에서 이미지 처리 라이브러리 연습용 프로젝트입니다.
Picasso, Glide, Coil 라이브러를 사용해 스마트폰 내부의 이미지를 로드하고 Compose UI로 보여주는 앱입니다.
<br>

### 230413
1. Picasso
- 자체에서 별도로 Compose와 관련된 기능을 제공하지 않음

- 이미지 uri를 bitmap으로 변환 후 기본 Image Composable에 전달
<br>

2. Glide
- 자체적으로 Compose 전용인 GlideImage Composable을 제공

- 학습도중 겪은 문제 : GlideImage Composable에 uri를 직접 제공하면 이미지가 불러와지지 않는 문제 발생
- ~~원인 : GlideImage가 ExperimentalGlideComposeApi여서 불안정한 것으로 보임~~
- ~~해결 방법 : 비트맵으로 변환 후 GlideImage Composable에 전달하거나, 다른 이미지를 로드 후 재로드 시 잘 작동함~~
- 원인 : GlideImage의 model에 들어가는 imageUri의 Null 처리를 제대로 안해서 생기는 문제였음
- 해결방법 : GlideImage의 model에 imageUri를 전달하기 전에 Null 체크 후 전달
<br>

3. Coil
- 자체적으로 Compose 전용인 AsyncImage Composable을 제공
<br>

### 240419
- Picasso 사용법 정리

1. xml 형식의 view에 이미지 추가 
``` kotlin
Picasso
    .get()
    .load(이미지 URI)
    .into(이미지를 추가할 View)
```
<br>

2. compose에 이미지 추가
``` kotlin
// 1. MutableState 비트맵 객체 생성
val image = remember { mutableStateOf<Bitmap?>(null) }

// 2. 코루틴 IO 디스패쳐에서 Picasso를 통해 이미지를 Bitmap으로 가져오기
LaunchedEffect(imageUri) {
    image.value = withContext(Dispatchers.IO) {
        Picasso
            .get()
            .load(imageUri)
            .get()
    }
}

// 3. 이미지 컴포저블에 비트맵 이미지 전달하기 
image.value?.let { bitmap ->
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        modifier = modifier
    )
}
```
<br>

### 230420
- Glide 사용법 정리

1. xml 형식의 view에 이미지 추가
``` kotlin
Glide
    .with(Context)
    .load(이미지 URI)
    .into(이미지를 추가할 View);
```
<br>

2. compose에 이미지 추가1 : 비트맵 객체 생성 후 이미지 컴포저블에 전달
``` kotlin
// 1. MutableState 비트맵 객체 생성
val image = remember { mutableStateOf<Bitmap?>(null) }

// 2. 컴포저블에서 context 가져오기
val context = LocalContext.current

// 3. 코루틴 Dispatchers.IO에서 Glide를 통해 이미지를 Bitmap으로 가져오기
LaunchedEffect(imageUri) {
    withContext(Dispatchers.IO) {
        Glide
            .with(context)
            .asBitmap()
            .load(imageUri)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?,
                ) {
                    image.value = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}

// 4. 이미지 컴포저블에 비트맵 이미지 전달하기 
image.value?.let { bitmap ->
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        modifier = modifier
    )
}
```
<br>

3. compose에 이미지 추가2 : GlideImage 컴포저블 사용
``` kotlin
// 1. 이미지 URI가 null인지 체크
if (imageUri != null) {

    // 2. GlideImage 컴포저블의 model 파라미터에 imageUri 
    GlideImage(
        model = imageUri,
        contentDescription = null,
        modifier = modifier
    )
}
```
<br>
