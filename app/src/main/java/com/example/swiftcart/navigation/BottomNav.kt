package com.example.swiftcart.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationScreens(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavigationScreens("home", Icons.Default.Home, "Home")
    object Cart : BottomNavigationScreens("cart", Icons.Default.ShoppingCart, "Cart")
    object Profile : BottomNavigationScreens("profile", Icons.Default.Person, "Profile")
    object Notification : BottomNavigationScreens("notification", Icons.Default.Notifications, "Notifications")
}