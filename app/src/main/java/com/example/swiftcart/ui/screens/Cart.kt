package com.example.swiftcart.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.swiftcart.R
import com.example.swiftcart.data.model.ProductDto
import com.example.swiftcart.data.model.ProductResponse
import com.example.swiftcart.navigation.Screen
import com.example.swiftcart.ui.component.ShimmerCartList
import com.example.swiftcart.ui.theme.courgetteFontFamily
import com.example.swiftcart.utils.AuthResult
import com.example.swiftcart.viewmodel.ProductViewModel
import com.example.swiftcart.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CartScreen(
    profileViewModel: ProfileViewModel,
    productViewModel: ProductViewModel,
    navController: NavController
) {
    var isLoading by remember { mutableStateOf(true) }
    var productResponse by remember {
        mutableStateOf<ProductResponse?>(null)
    }
    var numberOfItem by remember {
        mutableIntStateOf(0)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Box(
        modifier = Modifier.fillMaxSize()
            .padding(top = 15.dp),
    ) {
        LaunchedEffect(key1 = navBackStackEntry) {
            profileViewModel.getProductsInCart()
            profileViewModel.productsInCart.collectLatest { result ->
                when (result) {
                    is AuthResult.Error -> {}
                    AuthResult.Loading -> {
                        isLoading = true
                    }

                    is AuthResult.Success -> {
                        isLoading = false
                        productResponse = result.data
                    }
                }
            }
        }
        val context = LocalContext.current
        if (isLoading) {
            ShimmerCartList()
        } else {
            if (productResponse!!.itemCount == 0) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.align(alignment = Alignment.Center)) {
                        Text(
                            text = "Your cart is currently empty",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                        )
                    }
                }
            } else {
                CartList(
                    productResponse = productResponse!!,
                    numberOfItem = numberOfItem,
                    increaseItem = { numberOfItem++ },
                    decreaseItem = { numberOfItem-- },
                    productViewModel = productViewModel
                ) {
                    navController.navigate(Screen.ProductDetail.route)
                }
            }
        }
    }
}

@Composable
private fun CartItem(
    product: ProductDto,
    modifier: Modifier = Modifier,
    numberOfItem: Int,
    increaseItem: () -> Unit,
    decreaseItem: () -> Unit,
    productViewModel: ProductViewModel,
    navigateToDetail: () -> Unit
) {
    Card(
        onClick = {
            productViewModel.setProductId(product.id)
            navigateToDetail()
        },
        modifier = modifier
            .padding(4.dp)
            .wrapContentHeight()
    ) {
        val context = LocalContext.current
        Row(
            modifier = Modifier.wrapContentSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(product.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.broken),
                error = painterResource(R.drawable.broken),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = RoundedCornerShape(14.dp)),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .height(100.dp)
                    .padding(top = 5.dp, bottom = 5.dp)
                    .weight(1f)
            ) {
                Column {
                    Text(
                        text = product.name,
                        fontFamily = courgetteFontFamily,
                        fontWeight = FontWeight(700),
                        fontSize = MaterialTheme.typography.subtitle1.fontSize
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(alignment = Alignment.BottomStart)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "$${product.price}",
                        fontFamily = courgetteFontFamily,
                        fontWeight = FontWeight(700),
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        modifier = Modifier.weight(1f)
                    )

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.LightGray
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                start = 13.dp,
                                end = 13.dp,
                                top = 5.dp,
                                bottom = 5.dp
                            )
                        ) {
                            Text(
                                text = "-",
                                fontFamily = courgetteFontFamily,
                                fontWeight = FontWeight(600),
                                color = Color.Black,
                                fontSize = MaterialTheme.typography.body1.fontSize,
                                modifier = Modifier.clickable {
                                    decreaseItem()
                                }
                            )
                            Spacer(modifier = Modifier.width(13.dp))
                            Text(
                                text = numberOfItem.toString(),
                                fontFamily = courgetteFontFamily,
                                color = Color.Black,
                                fontWeight = FontWeight(450),
                                fontSize = MaterialTheme.typography.body1.fontSize
                            )
                            Spacer(modifier = Modifier.width(13.dp))
                            Text(
                                text = "+",
                                fontFamily = courgetteFontFamily,
                                fontWeight = FontWeight(600),
                                color = Color.Black,
                                fontSize = MaterialTheme.typography.body1.fontSize,
                                modifier = Modifier.clickable {
                                    increaseItem()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CartList(
    productResponse: ProductResponse,
    modifier: Modifier = Modifier,
    numberOfItem: Int,
    increaseItem: () -> Unit,
    decreaseItem: () -> Unit,
    productViewModel: ProductViewModel,
    navigateToDetail: () -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy((12.dp)),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxSize(),
        content = {
            items(productResponse.itemCount) { index ->
                CartItem(
                    product = productResponse.productDto[index],
                    numberOfItem = numberOfItem,
                    increaseItem = { increaseItem() },
                    decreaseItem = { decreaseItem() },
                    productViewModel = productViewModel
                ) {
                    navigateToDetail()
                }
            }
        }
    )
}