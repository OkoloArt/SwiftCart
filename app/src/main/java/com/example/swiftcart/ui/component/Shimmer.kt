package com.example.swiftcart.ui.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedProductShimmer(spacerHeight: Dp) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200, easing = FastOutSlowInEasing
            ), repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    ShimmerProductItem(spacerHeight = spacerHeight, brush = brush)
}

@Composable
fun AnimatedCartShimmer() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200, easing = FastOutSlowInEasing
            ), repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    ShimmerCartItem(brush = brush)
}

@Composable
fun ShimmerProductItem(spacerHeight: Dp, brush: Brush) {
    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier.wrapContentSize()
        ) {
            Spacer(
                modifier = Modifier
                    .height(spacerHeight)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(brush)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(brush)
                    .fillMaxWidth(0.7f)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(0.5f)
                    .background(brush)
            )

        }
    }
}

@Composable
fun ShimmerCartItem(brush: Brush) {
    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier.wrapContentSize()
        ) {
            Spacer(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(brush)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier.height(100.dp)
                    .padding(top = 5.dp, bottom = 5.dp)
                    .weight(1f)
            ) {
                Column {
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth(0.7f)
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth(0.5f)
                            .background(brush)
                    )
                }
                Row(
                   modifier = Modifier.align(alignment = Alignment.BottomStart)
                       .fillMaxWidth()
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth(0.7f)
                            .background(brush)
                    )
                }
            }
        }
    }
}

@Composable
fun ShimmerProductList() {
    val imageHeights = listOf(150.dp, 180.dp, 160.dp, 190.dp, 170.dp)
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy((20.dp)),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        content = {
            items(5) { index ->
                val imageHeight = imageHeights[index % imageHeights.size]
                AnimatedProductShimmer(spacerHeight = imageHeight)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ShimmerCartList() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy((12.dp)),
        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
        content = {
            items(5) {
                AnimatedCartShimmer()
            }
        }
    )
}