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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.heungjun.popuplogintoken.R
import com.heungjun.popuplogintoken.navigation.Screen
import com.heungjun.popuplogintoken.viewmodel.LoginViewModelCompany
import com.heungjun.popuplogintoken.viewmodel.LoginViewModelUser


@Composable
fun GeneralMemberLoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModelUser = viewModel()  // 뷰모델 인스턴스 추가
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginError by viewModel.errorMessage.collectAsState()
    val token by viewModel.token.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        SelectMemberType(navController, selectedButton = remember { mutableStateOf("일반전용") })
        Spacer(modifier = Modifier.height(16.dp))

        // 이메일 입력 필드
        InputFieldWithUnderline(
            label = "이메일",
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            placeholder = "이메일을 입력하세요"
        )

        // 비밀번호 입력 필드
        InputFieldWithUnderline(
            label = "비밀번호",
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            placeholder = "비밀번호를 입력하세요",
            //keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )

        // 로그인 버튼
        Button(
            onClick = { viewModel.login() },  // 로그인 기능 호출
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(4.dp))
        ) {
            Text(text = "Login", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }

        // 로그인 오류 메시지 표시
        if (loginError != null) {
            Text(text = loginError ?: "", color = Color.Red)
        }

        // 로그인 성공 시 홈스크린으로 이동
        if (token != null) {
            LaunchedEffect(token) {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.GeneralMemberLogin.route) { inclusive = true }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SignUpButton(onClick = { navController.navigate(Screen.GeneralMemberSignUp.route) }, buttonText = "회원가입하기")
    }
}

@Composable
fun CorporateMemberLoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModelCompany = viewModel()  // 뷰모델 인스턴스 추가
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginError by viewModel.errorMessage.collectAsState()
    val token by viewModel.token.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        SelectMemberType(navController, selectedButton = remember { mutableStateOf("기업전용") })
        Spacer(modifier = Modifier.height(16.dp))

        // 이메일 입력 필드
        InputFieldWithUnderline(
            label = "이메일",
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            placeholder = "이메일을 입력하세요"
        )

        // 비밀번호 입력 필드
        InputFieldWithUnderline(
            label = "비밀번호",
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            placeholder = "비밀번호를 입력하세요",
            //keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )

        // 로그인 버튼
        Button(
            onClick = { viewModel.login() },  // 로그인 기능 호출
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(4.dp))
        ) {
            Text(text = "Login", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }

        // 로그인 오류 메시지 표시
        if (loginError != null) {
            Text(text = loginError ?: "", color = Color.Red)
        }

        // 로그인 성공 시 홈스크린으로 이동
        if (token != null) {
            LaunchedEffect(token) {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.CorporateMemberLogin.route) { inclusive = true }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SignUpButton(onClick = { navController.navigate(Screen.CorporateMemberSignUp.route) }, buttonText = "회원가입하기")
    }
}

@Composable
fun LoginTitleSection() {
    Text(
        text = "Login",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun InputFieldsSection() {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "이메일:")
        BasicTextField(
            value = email.value,
            onValueChange = { email.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RectangleShape)
                .border(BorderStroke(1.dp, Color.Gray))
                .padding(8.dp),
            decorationBox = { innerTextField ->
                if (email.value.isEmpty()) {
                    Text(text = "이메일을 입력하세요", color = Color.Gray)
                }
                innerTextField()
            }
        )
        Text(text = "비밀번호:")
        BasicTextField(
            value = password.value,
            onValueChange = { password.value = it },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RectangleShape)
                .border(BorderStroke(1.dp, Color.Gray))
                .padding(8.dp),
            decorationBox = { innerTextField ->
                if (password.value.isEmpty()) {
                    Text(text = "비밀번호를 입력하세요", color = Color.Gray)
                }
                innerTextField()
            }
        )
    }
}

@Composable
fun SaveIdSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Use an appropriate checkbox or switch as per your design
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "아이디 저장하기")
        }
        Text(text = "ID/PW 찾기")
    }
}

@Composable
fun LoginButton() {
    Button(
        onClick = { /* Handle login */ },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(4.dp))
    ) {
        Text(text = "Login", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

@Composable
fun MainSocialLoginSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_naver),
            contentDescription = "Naver",
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_kakao),
            contentDescription = "KakaoTalk",
            modifier = Modifier.size(48.dp)
        )
    }
}