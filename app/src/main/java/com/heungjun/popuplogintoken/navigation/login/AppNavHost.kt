package com.heungjun.popuplogintoken.navigation.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.heungjun.popuplogintoken.screen.CompanySignUpScreen
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
    }
}