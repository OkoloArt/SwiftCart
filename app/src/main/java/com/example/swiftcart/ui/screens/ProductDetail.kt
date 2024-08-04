package com.example.swiftcart.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.swiftcart.R
import com.example.swiftcart.data.model.Product
import com.example.swiftcart.data.model.Review
import com.example.swiftcart.ui.theme.courgetteFontFamily
import com.example.swiftcart.utils.AuthResult
import com.example.swiftcart.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductDetail(
    productViewModel: ProductViewModel
) {

    var product by remember {
        mutableStateOf<Product?>(null)
    }

    var isLoading by remember {
        mutableStateOf(true)
    }
    var numberOfItem by remember {
        mutableIntStateOf(0)
    }

    var isRefreshing by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val productId by productViewModel.productId.observeAsState(initial = "")

    val pullRefreshState = rememberPullRefreshState(
        refreshing = productViewModel.isRefreshing,
        onRefresh = {
            productViewModel.getSwipeChanges(productId)
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp)
            .pullRefresh(pullRefreshState)
    ) {

        LaunchedEffect(key1 = productId) {
            productViewModel.getCurrentProduct(productId)
        }

        val productDetailResult by productViewModel.productDetail.collectAsState()

        when (val result = productDetailResult) {
            is AuthResult.Error -> {
                Toast.makeText(context, result.error.message, Toast.LENGTH_SHORT).show()
                isRefreshing = false
            }

            AuthResult.Loading -> {
                isLoading = true
            }

            is AuthResult.Success -> {
                isLoading = false
                isRefreshing = false
                product = result.data
            }
        }

        if (isLoading) {
            LoadingIndicator(
                loadingVisible = isLoading,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        } else {
            val isClicked by productViewModel.isProductInCart(product!!.id).collectAsState(initial = false)
            ProductListDetail(
                product = product!!,
                numberOfItem,
                decreaseItem = { numberOfItem-- },
                increaseItem = { numberOfItem++ },
            )
            Footer(
                productId = product!!.id,
                isClicked = isClicked ,
                modifier = Modifier.align(alignment = Alignment.BottomCenter),
                productViewModel = productViewModel
            )
        }

        PullRefreshIndicator(
            refreshing = productViewModel.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@Composable
private fun ProductTitleHeading(
    product: Product,
    modifier: Modifier = Modifier,
    numberOfItem: Int,
    increaseItem: () -> Unit,
    decreaseItem: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(0.6f)
        ) {
            Text(
                text = product.name,
                fontFamily = courgetteFontFamily,
                fontWeight = FontWeight(700),
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Text(
                    text = "$${product.price}",
                    fontFamily = courgetteFontFamily,
                    fontWeight = FontWeight(700),
                    fontSize = MaterialTheme.typography.subtitle1.fontSize
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = product.ratings.average.toString(),
                    fontFamily = courgetteFontFamily,
                    fontWeight = FontWeight(450),
                    fontSize = MaterialTheme.typography.body1.fontSize
                )

            }
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(0.4f)
        ) {
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
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = if (product.quantity > 0) "Available in stock" else "Out of stock",
                fontFamily = courgetteFontFamily,
                fontWeight = FontWeight(600),
                fontSize = MaterialTheme.typography.body1.fontSize
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProductListDetail(
    product: Product,
    numberOfItem: Int,
    decreaseItem: () -> Unit,
    increaseItem: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Column(
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(product.images[0])
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.broken),
                    error = painterResource(R.drawable.broken),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(15.dp, bottomEnd = 20.dp, bottomStart = 20.dp)),
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.height(10.dp))
                ProductTitleHeading(
                    product = product,
                    modifier = Modifier.padding(10.dp),
                    numberOfItem,
                    increaseItem,
                    decreaseItem
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Description",
                    fontFamily = courgetteFontFamily,
                    fontWeight = FontWeight(700),
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = product.description,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp, start = 10.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reviews",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                )
                Text(
                    text = if (product.ratings.count == 1) "(${product.ratings.count} review)" else "(${product.ratings.count} reviews)",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            val reviews = if (product.reviews.size > 5) product.reviews.take(5) else product.reviews
            repeat(reviews.size) { index ->
                ReviewItem(reviews[index])
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun ReviewItem(review: Review, modifier: Modifier = Modifier) {
    Card(
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hus),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = review.user,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .weight(1f)
                        .align(alignment = Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(10.dp))
                val numberOfStars = (review.rating / 2).toInt()
                repeat(numberOfStars) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier
                            .size(18.dp)
                            .align(alignment = Alignment.CenterVertically)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = review.comment,
                fontFamily = FontFamily.Serif,
            )
        }
    }
}

@Composable
private fun Footer(
    productId: String,
    isClicked: Boolean,
    modifier: Modifier = Modifier,
    productViewModel: ProductViewModel
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(20.dp)
                )
                .align(alignment = Alignment.BottomEnd)
                .alpha(0.5f)
                .clickable {
                    if (isClicked) productViewModel.removeProductFromCart(productId) else productViewModel.addProductToCart(
                        productId
                    )
                }
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
            )
            Text(
                text = if (isClicked) "Remove from Cart" else "Add to Cart",
                fontFamily = courgetteFontFamily,
                fontWeight = FontWeight(700),
                color = Color.White,
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 20.dp)
            )
        }
    }
}





