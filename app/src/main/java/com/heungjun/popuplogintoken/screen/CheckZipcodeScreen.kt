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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.heungjun.popuplogintoken.viewmodel.ZipCodeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun CheckZipcodeScreen(viewModel: ZipCodeViewModel = viewModel()) {
    var showPostCodeDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "우편번호:",
            fontSize = 16.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // 우편번호 입력 필드
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            BasicTextField(
                value = viewModel.zipCode.value,
                onValueChange = { viewModel.updateZipCode(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                decorationBox = { innerTextField ->
                    if (viewModel.zipCode.value.isEmpty()) {
                        Text(text = "주소찾기 버튼을 눌러 우편번호를 찾아주세요", color = Color.Gray)
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

        // "우편번호 찾기" 버튼과 주소 메시지
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { showPostCodeDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)), // 오렌지색 버튼
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
            ) {
                Text(text = "우편번호 찾기", color = Color.White)
            }

            // 주소 메시지 표시
            val addressMessage = if (viewModel.zipCode.value.isEmpty()) {
                "우편번호를 입력해주세요"
            } else {
                viewModel.address.value
            }

            val messageColor = if (viewModel.zipCode.value.isEmpty()) {
                Color.Gray
            } else {
                Color(0xFF1B5E20) // Green color
            }

            Text(
                text = addressMessage,
                color = messageColor,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        if (showPostCodeDialog) {
            DaumPostCodeDialog(
                onDismissRequest = { showPostCodeDialog = false },
                onPostCodeSelected = { postCode, address ->
                    viewModel.updateZipCode(postCode)
                    viewModel.updateAddress(address)
                    showPostCodeDialog = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckZipcodeScreen() {
    CheckZipcodeScreen()
}