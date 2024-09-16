package com.heungjun.popuplogintoken.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.heungjun.popuplogintoken.api.NetworkRepository
import com.heungjun.popuplogintoken.model.PopupStore
import com.heungjun.popuplogintoken.model.UserReservationList
import com.heungjun.popuplogintoken.viewmodel.CombinedViewModel
import com.heungjun.popuplogintoken.viewmodel.DashboardState
import com.heungjun.popuplogintoken.viewmodel.DashboardViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController, viewModel: CombinedViewModel = viewModel(
    factory = DashboardViewModelFactory(NetworkRepository())
)) {
    var selectedCategory by remember { mutableStateOf("팝업스토어의 등록 현황") }
    val categories = listOf("팝업스토어의 등록 현황", "사전예약 현황", "사전예약률", "팝업스토어 관리")
    var expanded by remember { mutableStateOf(false) }

    // Observe the states from the ViewModel
    val popupStoresState by viewModel.popupStoresState.collectAsState()
    val reservationListState by viewModel.reservationListState.collectAsState()

    // Trigger the API calls when the composable is first launched
    LaunchedEffect(Unit) {
        viewModel.getPopupStores()
        viewModel.getUserReservationList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Dropdown menu box
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded } // Toggle expanded state when clicked
        ) {
            // Outlined text field for displaying selected category
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = { /* No need to change since it's read-only */ },
                label = { Text("Select Category") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor() // Set the anchor for the dropdown menu
            )

            // Dropdown items
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(text = category) },
                        onClick = {
                            selectedCategory = category
                            expanded = false // Close the dropdown when item is clicked
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content based on the selected category
        when (selectedCategory) {
            "팝업스토어의 등록 현황" -> {
                // Display the popup store list based on the state
                when (popupStoresState) {
                    is DashboardState.PopupStoreState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    is DashboardState.PopupStoreState.Success -> {
                        val stores = (popupStoresState as DashboardState.PopupStoreState.Success).apiResponse.popupStores
                        StoreList(stores)
                    }
                    is DashboardState.PopupStoreState.Error -> {
                        val errorMessage = (popupStoresState as DashboardState.PopupStoreState.Error).message
                        Text(text = errorMessage, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            }
            "사전예약 현황" -> {
                // Display reservation details based on the state
                when (reservationListState) {
                    is DashboardState.ReservationListState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    is DashboardState.ReservationListState.Success -> {
                        val reservations = (reservationListState as DashboardState.ReservationListState.Success).reservationList.data
                        ReservationScreen(reservations = reservations)
                    }
                    is DashboardState.ReservationListState.Error -> {
                        val errorMessage = (reservationListState as DashboardState.ReservationListState.Error).message
                        Text(text = errorMessage, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }

            }
            "사전예약률" -> ReservationRateScreen()
            "팝업스토어 관리" -> StoreManagementScreen()
        }
    }
}

// 기존 사전예약 현황에서 표시된 화면을 사전예약률로 이동
@Composable
fun ReservationRateScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "사전예약률", fontSize = 24.sp, fontWeight = FontWeight.Bold) // 타이틀 수정
        Spacer(modifier = Modifier.height(16.dp))
        PieChart(value = 72f, description = "카누 쉼터")
        Spacer(modifier = Modifier.height(16.dp))
        PieChart(value = 32f, description = "맥주 카스 팝업스토어")
    }
}

// 사전예약 현황 화면에 새로운 내용 추가

@Composable
fun ReservationScreen(reservations: List<UserReservationList>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "팝업스토어 목록", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Iterate through the reservation list and display each reservation
        reservations.forEach { reservation ->
            ReservationCard(reservation = reservation)
        }
    }
}


@Composable
fun ReservationCard(reservation: UserReservationList) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White)
        ) {
            // Reservation Title
            Text(
                text = "고객 예약내역",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Reservation Details
            ReservationDetail(label = "장소", value = reservation.title)
            ReservationDetail(label = "날짜", value = reservation.date)
            ReservationDetail(label = "시간", value = reservation.startTime)
            ReservationDetail(label = "예약자명", value = reservation.name)
            ReservationDetail(label = "참여인원", value = reservation.numberOfPeople.toString())
            ReservationDetail(label = "예약 Id", value = reservation.id.toString())
        }
    }
}


@Composable
fun ReservationDetail(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$label:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = value, fontSize = 16.sp)
    }
}


// 나머지 코드들은 기존과 동일
@Composable
fun StoreList(stores: List<PopupStore>) {
    LazyColumn {
        itemsIndexed(stores) { index, store ->
            StoreCard(store = store, index = index) // index 값을 전달
        }
    }
}


@Composable
fun StoreCard(store: PopupStore, index: Int) {
    // Define the list of image URLs to be used, same as SearchResultItem
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

    // Get the image URL based on the index
    val imageUrl = imageUrls.getOrElse(index % imageUrls.size) { "" }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Store Image
            AsyncImage(
                model = imageUrl, // Using the image URL from the predefined list
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop // Crop the image to fit the dimensions
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Store Title
            Text(text = store.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}


@Composable
fun PieChart(value: Float, description: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(150.dp)
    ) {
        // Simulating pie chart with a circular progress indicator
        CircularProgressIndicator(
            progress = value / 100,
            modifier = Modifier.size(100.dp),
            color = Color.Blue,
            strokeWidth = 12.dp,
        )
        Text(text = "$value%", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
    Text(text = description, fontSize = 16.sp, textAlign = TextAlign.Center)
}

@Composable
fun StoreManagementScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "팝업스토어 관리 화면", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        // You can add more content related to store management here
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DashboardScreenPreview() {
//    DashboardScreen()
//}
