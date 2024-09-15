//package com.heungjun.popuplogintoken.screen
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.heungjun.popuplogintoken.R
//import com.heungjun.popuplogintoken.viewmodel.HomeViewModel
//
//@Composable
//fun FavoritesScreen(navController: NavController, homeViewModel: HomeViewModel) {
//    val favoriteStores by homeViewModel.favoriteStores.collectAsState()
//
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        item {
//            Text(
//                text = "Favorites",
//                style = MaterialTheme.typography.headlineLarge.copy(
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 24.sp
//                ),
//                modifier = Modifier
//                    .padding(16.dp)
//
//            )
//        }
//
//        if (favoriteStores.isEmpty()) {
//            item {
//                Text(
//                    text = "No favorites yet!",
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier.padding(16.dp)
//                )
//            }
//        } else {
//            items(favoriteStores.size) { index ->
//                val store = favoriteStores[index]
//                // Reuse the card layout from the home screen
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                        .clickable {
//                            navController.navigate("detail/${store.id}")
//                        },
//                    shape = RoundedCornerShape(12.dp),
//                ) {
//                    Box(modifier = Modifier.fillMaxWidth()) {
//                        // Image and Text Row
//                        Row(
//                            modifier = Modifier
//                                .padding(16.dp)
//                                .fillMaxWidth()
//                        ) {
//                            Image(
//                                painter = painterResource(id = R.drawable.poster1), // Placeholder for image
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .size(100.dp)
//                                    .clip(RoundedCornerShape(12.dp)),
//                                contentScale = ContentScale.Crop
//                            )
//                            Spacer(modifier = Modifier.width(16.dp))
//                            Column(
//                                verticalArrangement = Arrangement.Center,
//                                modifier = Modifier.fillMaxWidth()
//                            ) {
//                                Text(
//                                    text = store.title,
//                                    style = MaterialTheme.typography.headlineLarge.copy(
//                                        fontWeight = FontWeight.Bold,
//                                        fontSize = 16.sp
//                                    ),
//                                    modifier = Modifier.padding(bottom = 4.dp)
//                                )
//                                Text(
//                                    text = store.categories.joinToString { it.category },
//                                    style = MaterialTheme.typography.bodyMedium
//                                )
//                                store.address?.let {
//                                    Text(text = it, style = MaterialTheme.typography.bodySmall)
//                                } ?: run {
//                                    Text(text = "주소를 사용할 수 없습니다.", style = MaterialTheme.typography.bodySmall)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
