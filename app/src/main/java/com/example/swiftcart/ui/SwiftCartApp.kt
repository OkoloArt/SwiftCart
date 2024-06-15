package com.example.swiftcart.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.swiftcart.R
import com.example.swiftcart.navigation.BottomNavigationScreens
import com.example.swiftcart.navigation.Screen
import com.example.swiftcart.navigation.SetupNavGraph
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun SwiftCartApp(){

    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Cart,
        BottomNavigationScreens.Notification,
        BottomNavigationScreens.Profile,
    )

    var showBottomBar by rememberSaveable { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cart_animation))
    val logoAnimationState = animateLottieCompositionAsState(composition = composition)

    LaunchedEffect(navBackStackEntry, logoAnimationState.isAtEnd) {
        val currentRoute = navBackStackEntry?.destination?.route
        Log.d("SwiftCartApp", "Current Route: $currentRoute")

        showBottomBar = when (currentRoute) {
            "splash" -> false
            "home" -> logoAnimationState.isAtEnd
            "onboarding", "login", "register" -> false
            else -> true
        }
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            // No top bar for splash screen
            Spacer(modifier = Modifier.height(0.dp))
        },
        bottomBar = {
            AnimatedVisibility(visible = showBottomBar) {
                SwiftCartBottomNavigation(navController, bottomNavigationItems)
            }
        },

    ) {innerPadding ->
        SetupNavGraph(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
private fun SwiftCartBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    BottomNavigation(
        backgroundColor = colorScheme.primary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(text = screen.label) },
                selected = currentRoute == screen.route,
                alwaysShowLabel = false, // This hides the title for the unselected items
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}
