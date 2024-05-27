package com.example.swiftcart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.swiftcart.ui.SwiftCartApp
import com.example.swiftcart.ui.theme.SwiftCartTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SwiftCartTheme {
                SwiftCartApp()
            }
        }
    }
}