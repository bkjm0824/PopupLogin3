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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.heungjun.popuplogintoken.model.SignUpUser
import com.heungjun.popuplogintoken.navigation.Screen
import com.heungjun.popuplogintoken.viewmodel.UserSignUpViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heungjun.popuplogintoken.viewmodel.CategoryVM
import com.heungjun.popuplogintoken.viewmodel.CompanySignUpViewModel


@Composable
fun GeneralMemberSignUpScreen(
    navController: NavHostController,
    viewModel: UserSignUpViewModel = viewModel(),
    categoryVM: CategoryVM = viewModel() // Add CategoryVM here
) {
    val signUpUser = remember { mutableStateOf(
        SignUpUser(
            birth = "", categories = emptyList(), detailAddress = "", email = "",
            gender = "", mapx = "", mapy = "", nickname = "", password = "",
            phone = "", postcode = "", username = ""
        )
    ) }

    val signUpSuccess by viewModel.signUpSuccess.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    if (signUpSuccess) {
        // Navigate to login screen or show a success message
        navController.navigate(Screen.CorporateMemberLogin.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectMemberType(navController, selectedButton = remember { mutableStateOf("일반전용") })
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
                GMInputFieldSection(
                    signUpUser = signUpUser.value,
                    onSignUpUserChange = { updatedUser ->
                        signUpUser.value = updatedUser
                        viewModel.onSignUpUserChange(updatedUser)
                    }
                )
                CategorySelectionScreen(categoryVM = categoryVM) // Add CategorySelectionScreen here
                SignUpButton(onClick = { viewModel.signUp() })
                if (errorMessage != null) {
                    Text(text = errorMessage ?: "", color = Color.Red, fontSize = 14.sp)
                }
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
fun CorporateMemberSignUpScreen(navController: NavHostController, viewModel: CompanySignUpViewModel = viewModel()) {
    val companyName by viewModel.companyName.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val managerName by viewModel.managerName.collectAsState()
    val address by viewModel.address.collectAsState()

    val signUpSuccess by viewModel.signUpSuccess.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    if (signUpSuccess) {
        // Navigate to login screen or show a success message
        navController.navigate(Screen.CorporateMemberLogin.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectMemberType(navController, selectedButton = remember { mutableStateOf("기업전용") })
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
                CMInputFieldSection(
                    companyName = companyName,
                    email = email,
                    password = password,
                    managerName = managerName,
                    address = address,
                    onCompanyNameChange = viewModel::onCompanyNameChange,
                    onEmailChange = viewModel::onEmailChange,
                    onPasswordChange = viewModel::onPasswordChange,
                    onManagerNameChange = viewModel::onManagerNameChange,
                    onAddressChange = viewModel::onAddressChange
                )
                SignUpButton(onClick = { viewModel.signUp() })
                if (errorMessage != null) {
                    Text(text = errorMessage ?: "", color = Color.Red, fontSize = 14.sp)
                }
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
fun GMInputFieldSection(
    signUpUser: SignUpUser,
    onSignUpUserChange: (SignUpUser) -> Unit
) {
    val password = remember { mutableStateOf(signUpUser.password) }
    val confirmPassword = remember { mutableStateOf("") }
    val isPasswordMatched = remember { mutableStateOf(true) }
    val isBirthdateValid = remember { mutableStateOf(true) }
    val isMale = remember { mutableStateOf(signUpUser.gender == "남자") }
    val isFemale = remember { mutableStateOf(signUpUser.gender == "여자") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InputFieldWithUnderline(
            label = "이메일:",
            value = signUpUser.email,
            onValueChange = {
                onSignUpUserChange(signUpUser.copy(email = it))
            },
            placeholder = "이메일을 입력하세요"
        )
        InputFieldWithUnderline(
            label = "비밀번호:",
            value = password.value,
            onValueChange = {
                password.value = it
                onSignUpUserChange(signUpUser.copy(password = it))
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
            value = signUpUser.username,
            onValueChange = {
                onSignUpUserChange(signUpUser.copy(username = it))
            },
            placeholder = "이름을 입력하세요"
        )
        InputFieldWithUnderline(
            label = "닉네임:",
            value = signUpUser.nickname,
            onValueChange = {
                onSignUpUserChange(signUpUser.copy(nickname = it))
            },
            placeholder = "닉네임을 입력하세요"
        )
        InputFieldWithUnderline(
            label = "생년월일 (YYYYMMDD):",
            value = signUpUser.birth,
            onValueChange = {
                onSignUpUserChange(signUpUser.copy(birth = it))
                isBirthdateValid.value = it.length == 8
            },
            placeholder = "생년월일을 입력하세요"
        )
        if (!isBirthdateValid.value) {
            Text(text = "생년월일은 8자여야 합니다.", color = Color.Red, fontSize = 12.sp)
        }
        InputFieldWithUnderline(
            label = "전화번호:",
            value = signUpUser.phone,
            onValueChange = {
                onSignUpUserChange(signUpUser.copy(phone = it))
            },
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
                        if (it) {
                            onSignUpUserChange(signUpUser.copy(gender = "남자"))
                            isFemale.value = false
                        }
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
                        if (it) {
                            onSignUpUserChange(signUpUser.copy(gender = "여자"))
                            isMale.value = false
                        }
                    },
                    colors = CheckboxDefaults.colors(checkmarkColor = Color.White)
                )
                Text(text = "여자")
            }
        }
    }
}

@Composable
fun CMInputFieldSection(
    companyName: String,
    email: String,
    password: String,
    managerName: String,
    address: String,
    onCompanyNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onManagerNameChange: (String) -> Unit,
    onAddressChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InputFieldWithUnderline(
            label = "기업명:",
            value = companyName,
            onValueChange = onCompanyNameChange,
            placeholder = "기업명을 입력해주세요"
        )
        InputFieldWithUnderline(
            label = "이메일:",
            value = email,
            onValueChange = onEmailChange,
            placeholder = "이메일을 입력하세요"
        )
        InputFieldWithUnderline(
            label = "비밀번호:",
            value = password,
            onValueChange = onPasswordChange,
            placeholder = "비밀번호를 입력해주세요"
        )
        InputFieldWithUnderline(
            label = "대표자명:",
            value = managerName,
            onValueChange = onManagerNameChange,
            placeholder = "대표자명을 입력해주세요"
        )
        InputFieldWithUnderline(
            label = "주소:",
            value = address,
            onValueChange = onAddressChange,
            placeholder = "주소를 입력해주세요"
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

//@Preview(showBackground = true)
//@Composable
//fun CMInputFieldSectionPreview() {
//    CMInputFieldSection()
//}

//@Preview(showBackground = true)
//@Composable
//fun GMInputFieldSectionPreview() {
//    GMInputFieldSection()
//}