package com.heungjun.popuplogintoken.screen

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.heungjun.popuplogintoken.R
import com.heungjun.popuplogintoken.model.PopupStore
import com.heungjun.popuplogintoken.viewmodel.DetailViewModel
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    id: String?,
    navigateBack: () -> Unit,
    navController: NavHostController // NavController를 매개변수로 전달
) {
    // 이미지 URL 리스트 추가
    val imageUrls = listOf(
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_VKXdGNcIYX1P_y7gHycdiJOnFhb_1vpokg&s",
        "https://news.nateimg.co.kr/orgImg/se/2024/03/28/2D6T7LN09P_1.jpg",
        "https://cdn.dailygrid.net/news/photo/201908/267517_166557_4755.jpg",
        "https://newsroom.smilegate.com/s3/board/220330_00.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQoSbqiTeB9z2Kb2MSbxiVmLZZHPCqWdQuSw&s",
        "https://images.munto.kr/production-socialing/1716729560828-photo-aiixc-384151-0",
        "https://cdn.econovill.com/news/photo/202401/641224_585187_359.jpg",
        "https://mblogthumb-phinf.pstatic.net/MjAyMzAzMTZfMTQ1/MDAxNjc4OTMxNTI4ODU3.tOpsEXnN7Byr6aQTMUNrH-5x2fizSxzT_JJSbjcgbGgg.ag4EINpirLxT8gXVv0u5SshzDGi9nFbT5qn_oruF1wwg.PNG.toun28/image.png?type=w800"
    )

    val detailViewModel: DetailViewModel = viewModel()
    val detailState by detailViewModel.detailState.collectAsState()

    LaunchedEffect(id) {
        id?.toIntOrNull()?.let { intId ->
            detailViewModel.fetchPopupStoreById(intId)
        }
    }

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("상세정보", "지도", "후기")

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        IconButton(onClick = navigateBack) {
            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Go Back")
        }
        if (detailState.isLoading) {
            CircularProgressIndicator()
        }
        if (detailState.error != null) {
            Text(detailState.error.orEmpty())
        }
        detailState.popupStore?.let { popupStore ->
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Store Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                )

                Spacer(Modifier.height(8.dp))

                // 이미지 슬라이더에 imageUrls를 전달
                ImageSliderScreen(imageUrls)

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Placeholder for additional content if needed
                }

                Spacer(Modifier.height(8.dp))

                Divider(color = Color.Gray, thickness = 1.dp)

                Spacer(Modifier.height(8.dp))

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index }
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                when (selectedTabIndex) {
                    0 -> {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            item {
                                DetailInfoExpress(popupStore = popupStore, navController = navController)
                            }
                        }
                    }
                    1 -> Box(Modifier.fillMaxSize()) {
                        MapView()
                    }
                    2 -> ReviewsSection()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSliderScreen(imageUrls: List<String>) {
    val pageState = rememberPagerState(pageCount = { imageUrls.size })

    val coroutineScope = rememberCoroutineScope()

    // 자동 스크롤을 위한 코드
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            val nextPage = (pageState.currentPage + 1) % imageUrls.size
            coroutineScope.launch {
                pageState.animateScrollToPage(nextPage)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(state = pageState) { page ->
            val actualPage = page % imageUrls.size

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrls[actualPage]),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(imageUrls.size) { iteration ->
                val color =
                    if ((pageState.currentPage % imageUrls.size) == iteration) Color.Black else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }
}



@Composable
fun DetailInfoExpress(popupStore: PopupStore, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = popupStore.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            CategoryBox(category = popupStore.categories.joinToString { it.category })

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ShareButton(popupStore = popupStore)
                Spacer(modifier = Modifier.height(4.dp))
                StoreSaveButtonWithCount()
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${popupStore.startDate} ~ ${popupStore.endDate}",
            fontSize = 10.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(4.dp))
            popupStore.address?.let {
                Text(
                    text = it,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider(color = Color.Gray, thickness = 1.dp)

        Spacer(modifier = Modifier.height(16.dp))

        popupStore.description?.let {
            Text(
                text = it,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AdditionalDetailRow(label = "Post Code", value = popupStore.postCode)
            AdditionalDetailRow(label = "Detail Address", value = popupStore.detailAddress)
            AdditionalDetailRow(label = "Telephone", value = popupStore.telephone)
            AdditionalDetailRow(label = "Subway", value = popupStore.subway)
            AdditionalDetailRow(label = "Map X", value = popupStore.mapx)
            AdditionalDetailRow(label = "Map Y", value = popupStore.mapy)
            AdditionalDetailRow(label = "Link", value = popupStore.link)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // PreOrderButton에 navController를 전달
        PreOrderButton(navController = navController)
    }
}


@Composable
fun AdditionalDetailRow(label: String, value: String?) {
    if (!value.isNullOrEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = "$label:",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = value,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun PreOrderButton(navController: NavController) {
    Button(
        onClick = {
            navController.navigate("preorder") // Navigate to PreOrderScreen
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = "사전예약",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}



@Composable
fun CategoryBox(category: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xffb0d4ff),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = category,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun MapView() {
    val context = LocalContext.current
    var mapView by remember { mutableStateOf<MapView?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                MapView(context).also {
                    mapView = it
                    it.getMapAsync { naverMap ->
                        setupNaverMap(context, naverMap)
                    }
                }
            },
            update = {
                mapView = it
            }
        )
    }
}

private fun setupNaverMap(context: Context, naverMap: NaverMap) {
    // 네이버 지도 초기 설정을 여기서 합니다.
    // 예: 마커 추가, 지도 중심 이동 등

    // 현재 위치를 중심으로 지도 이동
    naverMap.moveCamera(com.naver.maps.map.CameraUpdate.scrollTo(com.naver.maps.geometry.LatLng(37.5666102, 126.9783881)))

    // 기타 설정 (예: UI 설정 등)
    naverMap.uiSettings.isLocationButtonEnabled = true
}

@Composable
fun ReviewsSection() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "후기 화면", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ShareButton(popupStore: PopupStore) {
    val clipboardManager = LocalClipboardManager.current
    val shareText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("${popupStore.title}\n")
        }
        append("${popupStore.description}\n")
        append("${popupStore.link}")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = {
                // Copy text to clipboard
                clipboardManager.setText(AnnotatedString(shareText.toString()))
                // Optionally show a toast or snackbar to inform the user
            }
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.Blue,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun StoreSaveButtonWithCount(
    initialCount: Int = 52,
    color: Color = Color(0xffE91E63)
) {
    var isFavorite by remember { mutableStateOf(false) }
    var favoriteCount by remember { mutableStateOf(initialCount) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconToggleButton(
            checked = isFavorite,
            onCheckedChange = {
                isFavorite = !isFavorite
                favoriteCount = if (isFavorite) favoriteCount + 1 else favoriteCount - 1
            }
        ) {
            Icon(
                tint = color,
                modifier = Modifier.graphicsLayer {
                    scaleX = 1.5f
                    scaleY = 1.5f
                },
                imageVector = if (isFavorite) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(1.dp))
        Text(
            text = "$favoriteCount",
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}
