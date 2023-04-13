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
