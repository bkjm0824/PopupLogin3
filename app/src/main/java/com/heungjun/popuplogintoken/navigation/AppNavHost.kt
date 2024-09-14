//package com.heungjun.popuplogintoken.navigation
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.heungjun.popuplogintoken.screen.CorporateMemberLoginScreen
//import com.heungjun.popuplogintoken.screen.CorporateMemberSignUpScreen
//import com.heungjun.popuplogintoken.screen.Footer
//import com.heungjun.popuplogintoken.screen.GeneralMemberLoginScreen
//import com.heungjun.popuplogintoken.screen.GeneralMemberSignUpScreen
//import com.heungjun.popuplogintoken.screen.Header
//import com.heungjun.popuplogintoken.screen.HomeScreen
//
//@Composable
//fun AppNavHost(
//    navController: NavHostController = rememberNavController(),
//    startDestination: String = Screen.GeneralMemberLogin.route
//) {
//    Scaffold(
//        topBar = {
//            Header()
//        },
//        bottomBar = {
//            Footer(
//                selectedIndex = 2,
//                onItemSelected = { index ->
//                    when (index) {
//                        0 -> navController.navigate("search")
//                        1 -> navController.navigate("place")
//                        2 -> navController.navigate("home_screen")
//                        3 -> navController.navigate("favorite")
//                        4 -> navController.navigate("settings")
//                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
//            NavHost(navController = navController, startDestination = startDestination) {
//                composable(Screen.GeneralMemberLogin.route) {
//                    GeneralMemberLoginScreen(navController)
//                }
//                composable(Screen.CorporateMemberLogin.route) {
//                    CorporateMemberLoginScreen(navController)
//                }
//                composable(Screen.GeneralMemberSignUp.route) {
//                    GeneralMemberSignUpScreen(navController)
//                }
//                composable(Screen.CorporateMemberSignUp.route) {
//                    CorporateMemberSignUpScreen(navController)
//                }
//                composable("home_screen") {
//                    HomeScreen(onLogout = {
//                        navController.navigate(Screen.GeneralMemberLogin.route) {
//                            popUpTo("home_screen") { inclusive = true }
//                        }
//                    })
//                }
//            }
//        }
//    }
//}