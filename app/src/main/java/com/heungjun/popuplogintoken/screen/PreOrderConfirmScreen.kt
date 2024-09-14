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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heungjun.popuplogintoken.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun PreOrderConfirmScreen(
    navController: NavController,
    personCount: Int,
    dateMillis: Long,
    timeSlot: String
) {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = dateMillis
    }
    val formattedDate = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(calendar.time)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Add the header with the logo
        SimpleHeader()

        // Spacer to push content to the middle
        Spacer(modifier = Modifier.weight(1f))

        // Main content area centered
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFEFEF), RoundedCornerShape(16.dp))
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Close button (X)
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Text(
                        text = "X",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                }

                Text(
                    text = "사전예약이 완료되었습니다.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                // Reservation details
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    ReservationDetailRow("예약자", "김유저")
                    ReservationDetailRow("예약인원", "${personCount}명")
                    ReservationDetailRow("예약일시", "$formattedDate $timeSlot")
                }

                // Notice text
                Text(
                    text = "예약한 시간 10분 초과시 자동 취소됩니다.\n" +
                            "현장 상황에 따라 대기가 발생할 수 있습니다.\n" +
                            "일부 상품은 소진 시 채워지지 않습니다.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                // Confirmation Button
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB2D3C2)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "확인",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Spacer to push footer to the bottom
        Spacer(modifier = Modifier.weight(1f))

        // New Footer with a simple button
        SimpleFooter(
            onButtonClick = { navController.popBackStack() }
        )
    }
}

@Composable
fun ReservationDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(text = value, fontSize = 16.sp)
    }
}

@Composable
fun SimpleHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2196F3))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Ensure this matches your drawable resource name
            contentDescription = "PopSpot Logo",
            modifier = Modifier
                .size(80.dp) // Set the size you need
                .aspectRatio(1f), // Keep aspect ratio
            contentScale = ContentScale.Fit
        )
    }
}


@Composable
fun SimpleFooter(onButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            onClick = onButtonClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB2D3C2)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "확인",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreOrderConfirmScreenPreview() {
    PreOrderConfirmScreen(
        navController = rememberNavController(),
        personCount = 2,
        dateMillis = Calendar.getInstance().timeInMillis,
        timeSlot = "15:30"
    )
}
