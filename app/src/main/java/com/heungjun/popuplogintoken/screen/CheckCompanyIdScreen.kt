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
import com.heungjun.popuplogintoken.viewmodel.CompanyVM

@Composable
fun CheckCompanyIdScreen(companyVM: CompanyVM = viewModel()) {
    var companyId by remember { mutableStateOf("") }
    val companyIdCheckResponse by companyVM.companyIdCheckFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
//            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "사업자 번호:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // 사업자 번호 입력 필드 (언더라인)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            BasicTextField(
                value = companyId,
                onValueChange = { companyId = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                decorationBox = { innerTextField ->
                    if (companyId.isEmpty()) {
                        Text(text = "사업자 번호를 입력해주세요", color = Color.Gray)
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
                onClick = { companyVM.checkCompanyId(companyId) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                enabled = companyId.isNotEmpty() // 이메일이 입력되어 있을 때만 버튼 활성화
            ) {
                Text(text = "중복확인", color = Color.White)
            }

            // 메시지 표시
            companyIdCheckResponse?.let {
                Text(
                    text = if (it.result) "사용 가능한 값입니다" else it.message ?: "알 수 없는 오류가 발생했습니다.",
                    color = if (it.result) Color(0xFF1B5E20) else Color(0xFFF44336), // Green or Red color
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CheckCompanyIdScreen()
}