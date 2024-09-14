package com.heungjun.popuplogintoken.screen


import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heungjun.popuplogintoken.R
import com.heungjun.popuplogintoken.model.PopupStore
import com.heungjun.popuplogintoken.model.UserReservation
import com.heungjun.popuplogintoken.viewmodel.PreOrderViewModel
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreOrderScreen(
    navController: NavController
) {
    val viewModel: PreOrderViewModel = viewModel()
    val popupStores by viewModel.popupStores.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val reservationResult by viewModel.reservationResult.collectAsState()

    var selectedStoreIndex by remember { mutableStateOf(0) }
    var selectedPersonCount by remember { mutableStateOf(1) }
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    var selectedSlot by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            TopAppBar(
                title = { Text("사전예약") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else if (error != null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = error.orEmpty())
                }
            }
        } else {
            if (popupStores.isNotEmpty()) {
                item {
                    PopupStoreDropdown(
                        stores = popupStores,
                        selectedIndex = selectedStoreIndex,
                        onStoreSelected = { selectedStoreIndex = it }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    PopupStoreCard(store = popupStores[selectedStoreIndex])

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_person_24),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "인원을 선택해 주세요",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Text(
                        text = "4명까지 선택 가능합니다.",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)),
                        modifier = Modifier.padding(start = 48.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    PersonSelectionRow(
                        selectedPersonCount = selectedPersonCount,
                        onPersonSelected = { selectedPersonCount = it }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CustomCalendarView(
                        selectedDate = selectedDate,
                        onDateChange = { selectedDate = it }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TimeSlotSection(
                        label = "오전",
                        timeSlots = listOf("10:30", "11:00", "11:30"),
                        columns = 3,
                        selectedSlot = selectedSlot,
                        onSlotSelected = { selectedSlot = it }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TimeSlotSection(
                        label = "오후",
                        timeSlots = listOf(
                            "12:00", "12:30", "13:00", "13:30",
                            "14:00", "14:30", "15:00", "15:30",
                            "16:00", "16:30", "17:00", "17:30",
                            "18:00", "18:30", "19:00", "19:30",
                            "20:00", "20:30", "21:00", "21:30",
                            "22:00", "22:30"
                        ),
                        columns = 3,
                        selectedSlot = selectedSlot,
                        onSlotSelected = { selectedSlot = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    Button(
                        onClick = {
                            // Create a UserReservation object
                            val reservation = UserReservation(
                                id = popupStores[selectedStoreIndex].id,
                                reservationCount = selectedPersonCount,
                                day = selectedDate.time.toString(), // Convert to Date or desired format
                                reservationTime = selectedSlot ?: ""
                            )
                            // Make reservation
                            viewModel.makeReservation(reservation)

                            // Navigate to PreOrderConfirmScreen with selected details
                            navController.navigate(
                                "PreOrderConfirmScreen/${selectedPersonCount}/${selectedDate.timeInMillis}/${selectedSlot ?: ""}"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2196F3)
                        )
                    ) {
                        Text(
                            text = "사전예약",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }


                    Spacer(modifier = Modifier.height(16.dp))

                    reservationResult?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center,
                            color = if (it.contains("Failed")) Color.Red else Color.Green
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopupStoreDropdown(
    stores: List<PopupStore>,
    selectedIndex: Int,
    onStoreSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedStore = stores[selectedIndex]

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = selectedStore.title,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Store") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.clickable { expanded = true }
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            stores.forEachIndexed { index, store ->
                DropdownMenuItem(
                    onClick = {
                        onStoreSelected(index)
                        expanded = false
                    },
                    text = { Text(store.title) }
                )
            }
        }
    }
}

@Composable
fun PopupStoreCard(
    store: PopupStore
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.kakao),
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
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        store.description?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PersonSelectionRow(
    selectedPersonCount: Int,
    onPersonSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        (1..4).forEach { count ->
            Button(
                onClick = { onPersonSelected(count) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (count == selectedPersonCount) Color(0xFF2196F3) else Color.Gray
                ),
                modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
            ) {
                Text(
                    text = "$count 명",
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun CustomCalendarView(
    selectedDate: Calendar,
    onDateChange: (Calendar) -> Unit
) {
    // 현재 날짜
    val currentDate = Calendar.getInstance()

    // 현재 선택된 연도와 월
    val year = selectedDate.get(Calendar.YEAR)
    val month = selectedDate.get(Calendar.MONTH)

    // 선택된 달의 첫 번째 날짜 가져오기
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, 1)
    }

    // 그 달의 첫 번째 날의 요일 가져오기 (1: 일요일, 2: 월요일, ...)
    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    // 해당 월의 마지막 날짜 가져오기
    val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    // 전체 그리드의 총 날짜 수
    val totalDays = (firstDayOfWeek - 1) + maxDay

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                // 이전 달로 이동
                calendar.add(Calendar.MONTH, -1)
                onDateChange(calendar)
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous Month")
            }

            Text(
                text = "${calendar.get(Calendar.YEAR)}.${calendar.get(Calendar.MONTH) + 1}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )

            IconButton(onClick = {
                // 다음 달로 이동
                calendar.add(Calendar.MONTH, 1)
                onDateChange(calendar)
            }) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next Month")
            }
        }

        // 요일 표시 (일, 월, 화, 수, 목, 금, 토)
        val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // 날짜 표시
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // Make the grid square to properly display dates
        ) {
            // 첫 주의 공백 추가
            items((firstDayOfWeek - 1).coerceAtLeast(0)) {
                Spacer(modifier = Modifier.size(40.dp))
            }

            // 날짜 추가
            items(maxDay) { day ->
                val isSelected = (day + 1 == selectedDate.get(Calendar.DAY_OF_MONTH)
                        && month == selectedDate.get(Calendar.MONTH)
                        && year == selectedDate.get(Calendar.YEAR))

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) Color(0xFF357EDD) else Color.Transparent)
                        .clickable {
                            val newDate = Calendar
                                .getInstance()
                                .apply {
                                    set(Calendar.YEAR, year)
                                    set(Calendar.MONTH, month)
                                    set(Calendar.DAY_OF_MONTH, day + 1)
                                }
                            onDateChange(newDate)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${day + 1}",
                        color = if (isSelected) Color.White else Color.Black
                    )
                }
            }
        }
    }
}


@Composable
fun TimeSlotSection(
    label: String,
    timeSlots: List<String>,
    columns: Int,
    selectedSlot: String?,
    onSlotSelected: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(200.dp)
        ) {
            items(timeSlots) { slot ->
                Button(
                    onClick = { onSlotSelected(slot) },
                    modifier = Modifier.padding(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (slot == selectedSlot) Color(0xFF2196F3) else Color.Gray
                    )
                ) {
                    Text(text = slot, color = Color.White)
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun Preview() {
//    PreOrderScreen(navController = rememberNavController())
//}