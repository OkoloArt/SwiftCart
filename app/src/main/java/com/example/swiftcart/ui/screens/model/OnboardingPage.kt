package com.example.swiftcart.ui.screens.model

import androidx.annotation.RawRes
import com.example.swiftcart.R

sealed class OnboardingPage(
    @RawRes
    val image: Int,
    val title: String,
    val desc: String
){
    data object First : OnboardingPage(
        image = R.raw.cart_animation,
        title = "SwiftCart",
        desc = "Dive into a diverse collection of products. Explore the latest trends and shop with ease."
    )

    data object Second : OnboardingPage(
        image = R.raw.deals_animation,
        title = "Exclusive Deals",
        desc = "Access exclusive discounts and special offers tailored to your preferences. Stay informed and save big!."
    )

    data object Third : OnboardingPage(
        image = R.raw.checkout,
        title = "Fast Checkout",
        desc = "Experience a swift, secure checkout process with multiple payment options. Your shopping journey made seamless."
    )
}