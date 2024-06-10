package com.example.swiftcart.navigation

sealed class Screen (val route: String){
    data object Splash: Screen(route = "splash")
    data object Onboarding: Screen(route = "onboarding")
    data object Home: Screen(route = "home")
    data object Login: Screen(route = "login")
    data object Register: Screen(route = "register")
    data object Profile: Screen(route = "profile")
    data object Cart: Screen(route = "cart")
    data object Settings: Screen(route = "settings")
}