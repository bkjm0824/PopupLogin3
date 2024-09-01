package com.heungjun.popuplogintoken.navigation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.heungjun.popuplogintoken.screen.CompanySignUpScreen
import com.heungjun.popuplogintoken.screen.Footer
import com.heungjun.popuplogintoken.screen.Header
import com.heungjun.popuplogintoken.screen.HomeScreen
import com.heungjun.popuplogintoken.screen.LoginScreenCompany
import com.heungjun.popuplogintoken.screen.LoginScreenUser
import com.heungjun.popuplogintoken.screen.LoginSelectionScreen
import com.heungjun.popuplogintoken.screen.UserSignUpScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "login_selection"
) {
    Scaffold(
        topBar = {
            Header() // 모든 화면 상단에 Header 추가
        },
        bottomBar = {
            Footer(
                selectedIndex = 2, // 초기 선택된 항목 설정
                onItemSelected = { index ->
                    when (index) {
                        0 -> navController.navigate("search") // 검색화면으로 이동 (아직 추가되지 않았으므로 향후 추가 가능)
                        1 -> navController.navigate("place") // 장소화면으로 이동 (아직 추가되지 않았으므로 향후 추가 가능)
                        2 -> navController.navigate("home_screen") // 홈 화면으로 이동
                        3 -> navController.navigate("favorite") // 즐겨찾기 화면으로 이동 (아직 추가되지 않았으므로 향후 추가 가능)
                        4 -> navController.navigate("settings") // 설정 화면으로 이동 (아직 추가되지 않았으므로 향후 추가 가능)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            NavHost(navController = navController, startDestination = startDestination) {
                composable("login_selection") {
                    LoginSelectionScreen(
                        onLoginSelected = { selectedLogin ->
                            when (selectedLogin) {
                                "user" -> navController.navigate("user_login")
                                "company" -> navController.navigate("company_login")
                            }
                        },
                        onSignUpSelected = { selectedSignUp ->
                            when (selectedSignUp) {
                                "user" -> navController.navigate("user_signup")
                                "company" -> navController.navigate("company_signup")
                            }
                        }
                    )
                }
                composable("user_login") {
                    LoginScreenUser(onLoginSuccess = {
                        navController.navigate("home_screen") {
                            popUpTo("user_login") { inclusive = true }
                        }
                    })
                }
                composable("company_login") {
                    LoginScreenCompany(onLoginSuccess = {
                        navController.navigate("home_screen") {
                            popUpTo("company_login") { inclusive = true }
                        }
                    })
                }
                composable("user_signup") {
                    UserSignUpScreen(onSignUpSuccess = {
                        navController.navigate("user_login") {
                            popUpTo("user_signup") { inclusive = true }
                        }
                    })
                }
                composable("company_signup") {
                    CompanySignUpScreen(onSignUpSuccess = {
                        navController.navigate("company_login") {
                            popUpTo("company_signup") { inclusive = true }
                        }
                    })
                }
                composable("home_screen") {
                    HomeScreen(onLogout = {
                        navController.navigate("login_selection") {
                            popUpTo("home_screen") { inclusive = true }
                        }
                    })
                }
                // 추가적인 화면들이 여기에 추가될 수 있습니다.
            }
        }
    }
}