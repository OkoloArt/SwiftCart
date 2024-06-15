package com.example.swiftcart.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.swiftcart.ui.screens.CartScreen
import com.example.swiftcart.ui.screens.HomeScreen
import com.example.swiftcart.ui.screens.LoginScreen
import com.example.swiftcart.ui.screens.NotificationScreen
import com.example.swiftcart.ui.screens.Onboarding
import com.example.swiftcart.ui.screens.ProfileScreen
import com.example.swiftcart.ui.screens.RegisterScreen
import com.example.swiftcart.ui.screens.SplashScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route){
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Onboarding.route) {
            Onboarding(navController = navController)
        }
        composable(route = Screen.Register.route){
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.Login.route){
            LoginScreen(navController = navController)
        }
        composable(route = BottomNavigationScreens.Home.route) {
            HomeScreen()
        }
        composable(route = BottomNavigationScreens.Cart.route){
            CartScreen()
        }
        composable(route = BottomNavigationScreens.Profile.route){
            ProfileScreen()
        }
        composable(route = BottomNavigationScreens.Notification.route){
            NotificationScreen()
        }
    }
}
