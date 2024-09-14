package com.heungjun.popuplogintoken.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heungjun.popuplogintoken.R
import com.heungjun.popuplogintoken.viewmodel.HomeState
import com.heungjun.popuplogintoken.viewmodel.HomeViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = viewModel()
    val homeState by viewModel.homeState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 상단 뷰페이저와 로고를 포함한 박스
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(1.dp),
                contentAlignment = Alignment.Center
            ) {
                LogoSection()
            }
        }
        item {
            ImageCardSlider(navController)
        }
        item {
            RecommendSection(homeState, navController)
        }
    }
}

@Composable
fun LogoSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White) // 하얀색 배경 적용
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            contentScale = ContentScale.Fit, // 이미지가 Box의 크기에 맞게 조정됩니다.
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // 원하는 경우 패딩을 추가하여 여백을 설정합니다.
                .background(Color.White)
        )
    }
}

@Composable
fun ImageCardSlider(navController: NavController) {
    val imagesList = listOf(
        R.drawable.poster1,
        R.drawable.poster2,
        R.drawable.poster3
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "이달의 팝업",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.Start)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(imagesList.size) { index ->
                val image = imagesList[index]

                // 카드 클릭 시 DetailScreen으로 이동하도록 onClick 이벤트 추가
                Card(
                    modifier = Modifier
                        .width(150.dp) // 각 카드의 너비를 설정합니다.
                        .aspectRatio(0.75f) // 3:4 비율 (세로 길이가 더 긴 이미지)
                        .clickable {
                            navController.navigate("detail/${index + 1}") // index + 1로 넘기고, detail 화면에서 이를 처리
                        },
                    shape = RoundedCornerShape(16.dp) // 둥근 모서리 추가
                ) {
                    Image(
                        painter = painterResource(id = image),
                        contentScale = ContentScale.Crop, // 이미지가 카드에 맞게 잘리도록 설정
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun RecommendSection(homeState: HomeState, navController: NavController) {
    val userName = "000님"

    // Define a list of images for the cards
    val imagesList = listOf(
        R.drawable.kakao,
        R.drawable.atom,
        R.drawable.kyobo
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "$userName 을 위한 추천 팝업스토어",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.Start)
        )

        if (homeState.isLoading) {
            Text(text = "Loading...")
        } else if (homeState.error != null) {
            Text(text = "Error: ${homeState.error}")
        } else {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Take the first 3 items or less
                homeState.popupStores.take(3).forEachIndexed { index, store ->
                    val image = imagesList.getOrElse(index) { R.drawable.poster1 } // Fallback to poster1 if index is out of bounds
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                navController.navigate("detail/${store.id}")
                            },
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            // Image and Text Row
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = image),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = store.title,
                                        style = MaterialTheme.typography.headlineLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        ),
                                        modifier = Modifier
                                            .padding(bottom = 4.dp)
                                    )
                                    Text(
                                        text = store.categories.joinToString { it.category },
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    store.address?.let {
                                        Text(text = it, style = MaterialTheme.typography.bodySmall)
                                    } ?: run {
                                        Text(text = "주소를 사용할 수 없습니다.", style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                            // Favorite Button
                            FavoriteButton(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    color: Color = Color(0xffE91E63)
) {
    var isFavorite by remember { mutableStateOf(false) }

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            isFavorite = !isFavorite
        },
        modifier = modifier
    ) {
        Icon(
            tint = color,
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites"
        )
    }
}


@Composable
@Preview
fun HomeScreenView(){
    HomeScreen(navController = rememberNavController())
}