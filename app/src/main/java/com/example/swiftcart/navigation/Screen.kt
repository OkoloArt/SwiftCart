package com.example.swiftcart.navigation

sealed class Screen (val route: String){
    data object Splash: Screen(route = "splash")
    data object Onboarding: Screen(route = "onboarding")
    data object Login: Screen(route = "login")
    data object Register: Screen(route = "register")
}