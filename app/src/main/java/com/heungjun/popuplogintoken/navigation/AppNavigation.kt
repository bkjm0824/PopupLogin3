package com.heungjun.popuplogintoken.navigation


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.heungjun.popuplogintoken.api.NetworkRepository
import com.heungjun.popuplogintoken.screen.CorporateMemberLoginScreen
import com.heungjun.popuplogintoken.screen.CorporateMemberSignUpScreen
import com.heungjun.popuplogintoken.screen.DetailScreen
import com.heungjun.popuplogintoken.screen.GeneralMemberLoginScreen
import com.heungjun.popuplogintoken.screen.GeneralMemberSignUpScreen
import com.heungjun.popuplogintoken.screen.MapScreen
import com.heungjun.popuplogintoken.screen.PreOrderConfirmScreen
import com.heungjun.popuplogintoken.screen.PreOrderScreen
import com.heungjun.popuplogintoken.screen.ProfileScreen
import com.heungjun.popuplogintoken.screen.SearchScreen
import com.heungjun.popuplogintoken.screen.SettingsScreen
import com.heungjun.popuplogintoken.screen.HomeScreen
import com.heungjun.popuplogintoken.viewmodel.HomeViewModel

@Composable
fun AppNavigation(homeViewModel: HomeViewModel) {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
                val currentDestination: NavDestination? = navBackStackEntry?.destination

                listOfNavItems.forEach { navItem: NavItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().route!!) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) { paddingValues: PaddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.GeneralMemberLogin.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screens.HomeScreen.route) {
                HomeScreen(navController)
            }
            composable(route = Screens.ProfileScreen.route) {
                ProfileScreen(navController)
            }
            composable(route = Screens.SettingsScreen.route) {
                SettingsScreen()
            }
            composable(route = Screens.SearchScreen.route) {
                SearchScreen(onStoreClicked = { store ->
                    navController.navigate("detail/${store.id}")
                }, repository = NetworkRepository())
            }
            composable(route = Screens.MapScreen.route) {
                MapScreen()
            }
            composable(route = "detail/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                DetailScreen(
                    id = id,
                    navigateBack = { navController.popBackStack() },
                    navController = navController
                )
            }
            composable(route = "preorder") {
                PreOrderScreen(navController = navController)
            }
            composable(
                "PreOrderConfirmScreen/{personCount}/{dateMillis}/{timeSlot}",
                arguments = listOf(
                    navArgument("personCount") { type = NavType.IntType },
                    navArgument("dateMillis") { type = NavType.LongType },
                    navArgument("timeSlot") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val personCount = backStackEntry.arguments?.getInt("personCount") ?: 1
                val dateMillis = backStackEntry.arguments?.getLong("dateMillis") ?: 0L
                val timeSlot = backStackEntry.arguments?.getString("timeSlot") ?: ""

                PreOrderConfirmScreen(
                    navController = navController,
                    personCount = personCount,
                    dateMillis = dateMillis,
                    timeSlot = timeSlot
                )
            }

//            composable("favorites") {
//                FavoritesScreen(navController, homeViewModel)
//            }

            composable(Screens.GeneralMemberLogin.route) {
                GeneralMemberLoginScreen(navController)
            }
            composable(Screens.CorporateMemberLogin.route) {
                CorporateMemberLoginScreen(navController)
            }
            composable(Screens.GeneralMemberSignUp.route) {
                GeneralMemberSignUpScreen(navController)
            }
            composable(Screens.CorporateMemberSignUp.route) {
                CorporateMemberSignUpScreen(navController)
            }
        }
    }
}



data class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

val listOfNavItems = listOf(
    NavItem(
        route = Screens.SearchScreen.route,
        icon = Icons.Default.Search,
        label = "Search"
    ),
    NavItem(
        route = Screens.MapScreen.route,
        icon = Icons.Default.LocationOn,
        label = "Map"
    ),
    NavItem(
        route = Screens.HomeScreen.route,
        icon = Icons.Default.Home,
        label = "Home"
    ),
    NavItem(
        route = Screens.ProfileScreen.route,
        icon = Icons.Default.Person,
        label = "Profile"
    ),
    NavItem(
        route = Screens.SettingsScreen.route,
        icon = Icons.Default.Settings,
        label = "Settings"
    )
)

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home")
    object ProfileScreen : Screens("profile")
    object SettingsScreen : Screens("settings")
    object SearchScreen : Screens("search")
    object MapScreen : Screens("map")
    object PreOrderScreen : Screens("preorder") // Added PreOrderScreen to the list of screens

    object GeneralMemberLogin : Screens("general_member_login")
    object CorporateMemberLogin : Screens("corporate_member_login")
    object GeneralMemberSignUp : Screens("general_member_sign_up")
    object CorporateMemberSignUp : Screens("corporate_member_sign_up")
}
