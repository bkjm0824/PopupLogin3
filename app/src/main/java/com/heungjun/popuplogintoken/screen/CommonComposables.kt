package com.heungjun.popuplogintoken.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.heungjun.popuplogintoken.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_popupstore), // 이미지 리소스 ID
                    contentDescription = "Logo",
                    modifier = Modifier.height(40.dp)
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun InputFieldWithUnderline(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(text = placeholder, color = Color.Gray)
                    }
                    innerTextField()
                }
            )
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .align(Alignment.BottomStart) // 올바른 Alignment 사용
            )
        }
    }
}

@Composable
fun InputFieldWithButton(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp),
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            Text(text = placeholder, color = Color.Gray)
                        }
                        innerTextField()
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))

                val buttonText = if (placeholder == "우편번호를 입력해주세요") {
                    "주소 찾기"
                } else {
                    "중복확인"
                }

                Button(
                    onClick = { /* Handle button click */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF57F17)),
                    modifier = Modifier.wrapContentSize(),
                    shape = RoundedCornerShape(30)
                ) {
                    Text(buttonText, color = Color.Black)
                }
            }
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .align(Alignment.BottomStart) // 올바른 Alignment 사용
            )
        }
    }
}

@Composable
fun SignUpButton(
    onClick: () -> Unit,
    buttonText: String = "Sign Up"
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .width(200.dp)
            .height(48.dp)
            .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(text = buttonText, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTopAppBarPreview() {
    val navController = rememberNavController()
    Surface(modifier = Modifier.fillMaxSize()) {
        CustomTopAppBar(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun InputFieldWithUnderlinePreview() {
    Surface(modifier = Modifier.padding(16.dp)) {
        InputFieldWithUnderline(
            label = "이름",
            value = "",
            onValueChange = {},
            placeholder = "이름을 입력하세요"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputFieldWithButtonPreview() {
    Surface(modifier = Modifier.padding(16.dp)) {
        InputFieldWithButton(
            label = "이메일",
            value = "",
            onValueChange = {},
            placeholder = "이메일을 입력하세요"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpButtonPreview() {
    Surface(modifier = Modifier.padding(16.dp)) {
        SignUpButton(onClick = {}, buttonText = "Sign Up")
    }
}