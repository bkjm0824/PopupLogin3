package com.heungjun.popuplogintoken.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.heungjun.popuplogintoken.R
import com.heungjun.popuplogintoken.navigation.Screen

@Composable
fun GeneralMemberSignUpScreen(navController: NavHostController) {
    val selectedButton = remember { mutableStateOf("일반전용") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        CustomTopAppBar(navController = navController)
//        Divider(color = Color.Gray, thickness = 1.dp)
//        Spacer(modifier = Modifier.height(16.dp))
        SelectMemberType(navController, selectedButton)
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SignUpTitleSection()
                GMSocialLoginSection()
                GMInputFieldSection()
                CategorySelectionScreen()
                SignUpButton(onClick = { /* Handle sign up */ })
                Text(
                    text = "로그인으로 돌아가기",
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.CorporateMemberLogin.route)
                    }
                )
            }
        }
    }
}

@Composable
fun CorporateMemberSignUpScreen(navController: NavHostController) {
    val selectedButton = remember { mutableStateOf("기업전용") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        CustomTopAppBar(navController = navController)
//        Divider(color = Color.Gray, thickness = 1.dp)
//        Spacer(modifier = Modifier.height(16.dp))
        SelectMemberType(navController, selectedButton)
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SignUpTitleSection()
                CMInputFieldSection()
                SignUpButton(onClick = { /* Handle sign up */ })
                Text(
                    text = "로그인으로 돌아가기",
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.CorporateMemberLogin.route)
                    }
                )
            }
        }
    }
}

@Composable
fun SignUpTitleSection() {
    Text(
        text = "Sign Up",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun GMSocialLoginSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_naver),
            contentDescription = "Naver",
            modifier = Modifier.size(48.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_kakao),
            contentDescription = "KakaoTalk",
            modifier = Modifier.size(48.dp)
        )
        Text(text = "소셜로 회원가입하기")
    }
}

@Composable
fun GMInputFieldSection() {
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val nickname = remember { mutableStateOf("") }
    val birthdate = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val isBirthdateValid = remember { mutableStateOf(true) }
    val isPasswordMatched = remember { mutableStateOf(true) }
    val isMale = remember { mutableStateOf(true) }
    val isFemale = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CheckUserEmailScreen()
        InputFieldWithUnderline(
            label = "비밀번호:",
            value = password.value,
            onValueChange = {
                password.value = it
                if (it.isNotEmpty() && confirmPassword.value.isNotEmpty()) {
                    isPasswordMatched.value = it == confirmPassword.value
                }
            },
            placeholder = "비밀번호를 입력하세요"
        )
        InputFieldWithUnderline(
            label = "비밀번호 확인:",
            value = confirmPassword.value,
            onValueChange = {
                confirmPassword.value = it
                if (it.isNotEmpty() && password.value.isNotEmpty()) {
                    isPasswordMatched.value = it == password.value
                }
            },
            placeholder = "비밀번호를 한 번 더 입력하세요"
        )
        if (password.value.isNotEmpty() && confirmPassword.value.isNotEmpty()) {
            if (!isPasswordMatched.value) {
                Text(text = "비밀번호가 일치하지 않습니다.", color = Color.Red, fontSize = 12.sp)
            } else {
                Text(text = "비밀번호가 일치합니다.", color = Color.Green, fontSize = 12.sp)
            }
        }
        InputFieldWithUnderline(
            label = "이름:",
            value = name.value,
            onValueChange = { name.value = it },
            placeholder = "이름을 입력하세요"
        )
        CheckNicknameScreen()
        InputFieldWithUnderline(
            label = "생년월일 (YYYYMMDD):",
            value = birthdate.value,
            onValueChange = {
                birthdate.value = it
                isBirthdateValid.value = it.length == 8
            },
            placeholder = "생년월일을 입력하세요"
        )
        if (!isBirthdateValid.value) {
            Text(text = "생년월일은 8자여야 합니다.", color = Color.Red, fontSize = 12.sp)
        }
        InputFieldWithUnderline(
            label = "전화번호:",
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            placeholder = "전화번호를 입력하세요"
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "성별: ")
                Checkbox(
                    checked = isMale.value,
                    onCheckedChange = {
                        isMale.value = it
                        if (it) isFemale.value = false
                    },
                    colors = CheckboxDefaults.colors(checkmarkColor = Color.White)
                )
                Text(text = "남자")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isFemale.value,
                    onCheckedChange = {
                        isFemale.value = it
                        if (it) isMale.value = false
                    },
                    colors = CheckboxDefaults.colors(checkmarkColor = Color.White)
                )
                Text(text = "여자")
            }
        }
    }
}

@Composable
fun CMInputFieldSection() {
    val companyName = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val businessNumber = remember { mutableStateOf("") }
    val representativeName = remember { mutableStateOf("") }
    val zipCode = remember { mutableStateOf("") }
    val preciseAddress = remember { mutableStateOf("") }
    val detailAddress = remember { mutableStateOf("") }
    val isPasswordMatched = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InputFieldWithUnderline(
            label = "기업명:",
            value = companyName.value,
            onValueChange = { companyName.value = it },
            placeholder = "기업명을 입력해주세요"
        )
        CheckCompanyEmailScreen()
        InputFieldWithUnderline(
            label = "비밀번호:",
            value = password.value,
            onValueChange = {
                password.value = it
                if (it.isNotEmpty() && confirmPassword.value.isNotEmpty()) {
                    isPasswordMatched.value = it == confirmPassword.value
                }
            },
            placeholder = "비밀번호를 입력해주세요"
        )
        InputFieldWithUnderline(
            label = "비밀번호 확인:",
            value = confirmPassword.value,
            onValueChange = {
                confirmPassword.value = it
                if (it.isNotEmpty() && password.value.isNotEmpty()) {
                    isPasswordMatched.value = it == password.value
                }
            },
            placeholder = "비밀번호를 한 번 더 입력해주세요"
        )
        if (password.value.isNotEmpty() && confirmPassword.value.isNotEmpty()) {
            if (!isPasswordMatched.value) {
                Text(
                    text = "비밀번호가 일치하지 않습니다.",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            } else {
                Text(
                    text = "비밀번호가 일치합니다.",
                    color = Color.Green,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
        CheckCompanyIdScreen()
        InputFieldWithUnderline(
            label = "대표자명:",
            value = representativeName.value,
            onValueChange = { representativeName.value = it },
            placeholder = "대표자명을 입력해주세요"
        )
        CheckZipcodeScreen()
        InputFieldWithUnderline(
            label = "주소:",
            value = preciseAddress.value,
            onValueChange = { preciseAddress.value = it },
            placeholder = "주소를 입력해주세요"
        )
        InputFieldWithUnderline(
            label = "상세주소:",
            value = detailAddress.value,
            onValueChange = { detailAddress.value = it },
            placeholder = "상세주소를 입력해주세요"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CorporateMemberSignUpScreenPreview() {
    val navController = rememberNavController()
    CorporateMemberSignUpScreen(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun GeneralMemberSignUpScreenPreview() {
    val navController = rememberNavController()
    GeneralMemberSignUpScreen(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun CMInputFieldSectionPreview() {
    CMInputFieldSection()
}

@Preview(showBackground = true)
@Composable
fun GMInputFieldSectionPreview() {
    GMInputFieldSection()
}