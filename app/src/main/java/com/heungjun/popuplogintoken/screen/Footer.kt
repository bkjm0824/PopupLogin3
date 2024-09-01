package com.heungjun.popuplogintoken.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.heungjun.popuplogintoken.screen.navigation.NavItem

@Composable
fun Footer(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val navItemList = listOf(
        NavItem("Search", Icons.Default.Search, 0),
        NavItem("Place", Icons.Default.Place, 0),
        NavItem("Home", Icons.Default.Home, 0),
        NavItem("Favorite", Icons.Default.FavoriteBorder, 0),
        NavItem("Settings", Icons.Default.Settings, 0),
    )

    NavigationBar {
        navItemList.forEachIndexed { index, navItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    onItemSelected(index)
                },
                icon = {
                    BadgedBox(badge = {
                        if (navItem.badgeCount > 0)
                            Badge() {
                                Text(text = navItem.badgeCount.toString())
                            }
                    }) {
                        Icon(imageVector = navItem.icon, contentDescription = "Icon")
                    }
                },
                label = {
                    Text(text = navItem.label)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FooterPreview() {
    Footer(
        selectedIndex = 2, // 예: "Home"이 선택된 상태로 설정
        onItemSelected = {}
    )
}
