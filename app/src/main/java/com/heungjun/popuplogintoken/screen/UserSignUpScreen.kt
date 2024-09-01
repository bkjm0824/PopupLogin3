package com.heungjun.popuplogintoken.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heungjun.popuplogintoken.viewmodel.UserSignUpViewModel

@Composable
fun UserSignUpScreen(
    viewModel: UserSignUpViewModel = viewModel(),
    onSignUpSuccess: () -> Unit
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val detailAddress by viewModel.detailAddress.collectAsState()
    val username by viewModel.username.collectAsState()
    val gender by viewModel.gender.collectAsState()
    val birth by viewModel.birth.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val postcode by viewModel.postcode.collectAsState()
    val address by viewModel.address.collectAsState()
    val nickname by viewModel.nickname.collectAsState()
    val mapx by viewModel.mapx.collectAsState()
    val mapy by viewModel.mapy.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val signUpSuccess by viewModel.signUpSuccess.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // 스크롤 가능하게 수정
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = username,
            onValueChange = { viewModel.onUsernameChange(it) },
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = phone,
            onValueChange = { viewModel.onPhoneChange(it) },
            label = { Text("Phone") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = gender,
            onValueChange = { viewModel.onGenderChange(it) },
            label = { Text("Gender") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = birth,
            onValueChange = { viewModel.onBirthChange(it) },
            label = { Text("Birth") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = postcode,
            onValueChange = { viewModel.onPostcodeChange(it) },
            label = { Text("Postcode") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = address,
            onValueChange = { viewModel.onAddressChange(it) },
            label = { Text("Address") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = detailAddress,
            onValueChange = { viewModel.onDetailAddressChange(it) },
            label = { Text("Detail Address") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = nickname,
            onValueChange = { viewModel.onNicknameChange(it) },
            label = { Text("Nickname") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = mapx,
            onValueChange = { viewModel.onMapxChange(it) },
            label = { Text("Map X Coordinate") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = mapy,
            onValueChange = { viewModel.onMapyChange(it) },
            label = { Text("Map Y Coordinate") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = categories.joinToString(", "),
            onValueChange = { viewModel.onCategoriesChange(it.split(", ").map { it.trim() }) },
            label = { Text("Categories (comma separated)") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.signUp() }) {
            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (signUpSuccess) {
            onSignUpSuccess()
        }

        errorMessage?.let {
            Text("Error: $it", color = androidx.compose.ui.graphics.Color.Red)
        }
    }
}