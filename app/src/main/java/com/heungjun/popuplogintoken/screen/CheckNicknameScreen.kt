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
import com.heungjun.popuplogintoken.viewmodel.CheckNicknameVM


@Composable
fun CheckNicknameScreen(checkNicknameVM: CheckNicknameVM = viewModel()) {
    var nickname by remember { mutableStateOf("") }
    val nicknameCheckResponse by checkNicknameVM.nicknameCheckFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
//            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "닉네임:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // 닉네임 입력 필드
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            BasicTextField(
                value = nickname,
                onValueChange = { nickname = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                decorationBox = { innerTextField ->
                    if (nickname.isEmpty()) {
                        Text(text = "닉네임을 입력하세요", color = Color.Gray)
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
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { checkNicknameVM.checkNickname(nickname) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)), // 오렌지색 버튼
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                enabled = nickname.isNotEmpty() // 이메일이 입력되어 있을 때만 버튼 활성화
            ) {
                Text(text = "중복확인", color = Color.White)
            }

            // 메시지 표시
            nicknameCheckResponse?.let {
                val message = when {
                    nickname.isEmpty() -> "닉네임을 입력하세요"
                    it.result -> "사용 가능한 닉네임입니다"
                    else -> it.message ?: "알 수 없는 오류가 발생했습니다."
                }

                val messageColor = when {
                    nickname.isEmpty() -> Color.Gray
                    it.result -> Color(0xFF1B5E20) // Green color
                    else -> Color(0xFFF44336) // Red color
                }

                Text(
                    text = message,
                    color = messageColor,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckNicknameScreen() {
    CheckNicknameScreen()
}