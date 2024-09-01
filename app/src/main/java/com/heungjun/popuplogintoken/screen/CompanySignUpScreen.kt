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
import com.heungjun.popuplogintoken.viewmodel.CompanySignUpViewModel

@Composable
fun CompanySignUpScreen(
    viewModel: CompanySignUpViewModel = viewModel(),
    onSignUpSuccess: () -> Unit
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val companyName by viewModel.companyName.collectAsState()
    val companyId by viewModel.companyId.collectAsState()
    val managerName by viewModel.managerName.collectAsState()
    val address by viewModel.address.collectAsState()
    val detailAddress by viewModel.detailAddress.collectAsState()
    val postCode by viewModel.postCode.collectAsState()
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
            value = companyName,
            onValueChange = { viewModel.onCompanyNameChange(it) },
            label = { Text("Company Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = companyId,
            onValueChange = { viewModel.onCompanyIdChange(it) },
            label = { Text("Company ID") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = managerName,
            onValueChange = { viewModel.onManagerNameChange(it) },
            label = { Text("Manager Name") }
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
            value = postCode,
            onValueChange = { viewModel.onPostCodeChange(it) },
            label = { Text("Post Code") }
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