package com.example.swiftcart.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.swiftcart.navigation.SetupNavGraph
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun SwiftCartApp(){
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier,
        topBar = {
            // No top bar for splash screen
            Spacer(modifier = Modifier.height(0.dp))
        }
    ) {innerPadding ->
        SetupNavGraph(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}
