package com.heungjun.popuplogintoken.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.heungjun.popuplogintoken.navigation.Screens
import com.heungjun.popuplogintoken.viewmodel.LoginViewModelCompany
import com.heungjun.popuplogintoken.viewmodel.LoginViewModelUser

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.GeneralMemberLogin.route) {
        composable(Screens.GeneralMemberLogin.route) {
            val userViewModel: LoginViewModelUser = viewModel()
            GeneralMemberLoginScreen(navController, userViewModel)
        }
        composable(Screens.CorporateMemberLogin.route) {
            val companyViewModel: LoginViewModelCompany = viewModel()
            CorporateMemberLoginScreen(navController, companyViewModel)
        }
        composable(Screens.GeneralMemberSignUp.route) { GeneralMemberSignUpScreen(navController) }
        composable(Screens.CorporateMemberSignUp.route) { CorporateMemberSignUpScreen(navController) }
    }
}

// SelectMemberType 컴포저블 함수
@Composable
fun SelectMemberType(navController: NavHostController, selectedButton: MutableState<String>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(50)
            )
    ) {
        Button(
            onClick = {
                selectedButton.value = "일반전용"
                when (navController.currentBackStackEntry?.destination?.route) {
                    Screens.CorporateMemberLogin.route -> navController.navigate(Screens.GeneralMemberLogin.route)
                    Screens.CorporateMemberSignUp.route -> navController.navigate(Screens.GeneralMemberSignUp.route)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedButton.value == "일반전용") Color.Gray else Color.Transparent
            ),
            shape = RoundedCornerShape(
                topStart = 50.dp,
                bottomStart = 50.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp
            ),
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .border(
                    width = 1.dp,
                    color = if (selectedButton.value == "일반전용") Color.Gray else Color.Transparent,
                    shape = RoundedCornerShape(
                        topStart = 50.dp,
                        bottomStart = 50.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
        ) {
            Text(
                text = "일반전용",
                fontSize = 14.sp,
                color = if (selectedButton.value == "일반전용") Color.White else Color.Gray
            )
        }

        Button(
            onClick = {
                selectedButton.value = "기업전용"
                when (navController.currentBackStackEntry?.destination?.route) {
                    Screens.GeneralMemberLogin.route -> navController.navigate(Screens.CorporateMemberLogin.route)
                    Screens.GeneralMemberSignUp.route -> navController.navigate(Screens.CorporateMemberSignUp.route)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedButton.value == "기업전용") Color.Gray else Color.Transparent
            ),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = 50.dp,
                bottomEnd = 50.dp
            ),
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .border(
                    width = 1.dp,
                    color = if (selectedButton.value == "기업전용") Color.Gray else Color.Transparent,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 0.dp,
                        topEnd = 50.dp,
                        bottomEnd = 50.dp
                    )
                )
        ) {
            Text(
                text = "기업전용",
                fontSize = 14.sp,
                color = if (selectedButton.value == "기업전용") Color.White else Color.Gray
            )
        }
    }
}