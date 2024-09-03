package com.heungjun.popuplogintoken.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heungjun.popuplogintoken.viewmodel.CheckCompanyEmailVM

@Composable
fun CheckCompanyEmailScreen(checkCompanyEmailVM: CheckCompanyEmailVM = viewModel()) {
    var email by remember { mutableStateOf("") }
    val emailCheckResponse by checkCompanyEmailVM.emailCheckFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "이메일:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Email 입력 필드
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp) // 동일한 간격을 위해 변경
        ) {
            BasicTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp), // 동일한 간격을 위해 변경
                decorationBox = { innerTextField ->
                    if (email.isEmpty()) {
                        Text(text = "이메일을 입력하세요", color = Color.Gray)
                    }
                    innerTextField()
                }
            )
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .align(Alignment.BottomStart)
            )
        }

        // 중복 확인 버튼과 메시지
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // 동일한 간격을 위해 변경
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { checkCompanyEmailVM.checkEmail(email) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                enabled = email.isNotEmpty() // 이메일이 입력되어 있을 때만 버튼 활성화
            ) {
                Text(text = "중복확인", color = Color.White)
            }

            // 메시지 표시
            emailCheckResponse?.let {
                if (it.result) {
                    Text(
                        text = "사용 가능한 값입니다",
                        color = Color(0xFF1B5E20), // Green color
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = it.message,
                        color = Color(0xFFF44336), // Red color
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckCompanyEmailScreenPreview() {
    CheckCompanyEmailScreen()
}