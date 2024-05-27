package com.example.swiftcart.ui.screens


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.swiftcart.R
import com.example.swiftcart.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        val startDestination by remember { splashViewModel.startDestination }
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cart_animation))
        val logoAnimationState =
            animateLottieCompositionAsState(composition = composition)

        val scaleAnimation by rememberInfiniteTransition(label = "").animateFloat(
            initialValue = 1f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )

        Box(
            modifier = Modifier
                .size(200.dp) // Increase container size to avoid visible edges during animation
                .padding(top = 20.dp)
                .graphicsLayer(scaleX = scaleAnimation, scaleY = scaleAnimation)
                .align(alignment = Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.shopping_icon),
                contentDescription = "Pager Image",
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(10.dp)) // Clip the image with rounded corners
                    .align(alignment = Alignment.Center)
            )
        }
        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
            if (!splashViewModel.isLoading.value) {
                navController.popBackStack()
                navController.navigate(startDestination)
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewSplash() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.shopping_icon),
            contentDescription = "Pager Image",
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .padding(top = 20.dp)
                .height(180.dp)
                .width(180.dp)
                .align(alignment = Alignment.Center)
        )

    }
}