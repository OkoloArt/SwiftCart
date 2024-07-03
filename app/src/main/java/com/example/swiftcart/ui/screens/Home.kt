package com.example.swiftcart.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.swiftcart.R
import com.example.swiftcart.data.model.ProductDto
import com.example.swiftcart.data.model.ProductResponse
import com.example.swiftcart.ui.component.ShimmerGridList
import com.example.swiftcart.ui.theme.bodyFontFamily
import com.example.swiftcart.ui.theme.courgetteFontFamily
import com.example.swiftcart.utils.AuthResult
import com.example.swiftcart.viewmodel.ProductViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    productViewModel: ProductViewModel = hiltViewModel()
) {
    var isLoading by remember { mutableStateOf(true) }
    var productResponse by remember {
        mutableStateOf<ProductResponse?>(null)
    }
    var isFavourite by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(all = 12.dp)
    ) {

        LaunchedEffect(key1 = null) {
            productViewModel.products.collectLatest { result ->
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

        HomeHeading()
        Spacer(modifier = Modifier.height(5.dp))
        SearchLayout()
        Spacer(modifier = Modifier.height(25.dp))
        FilterChips(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(20.dp))

        if (isLoading) {
            ShimmerGridList()
        } else {
            if (productResponse!!.itemCount == 0) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.align(alignment = Alignment.Center)) {
                        Text(
                            text = "No product to display. our database is currently empty",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = {
                                isLoading = true
                                productViewModel.getAllProducts(forceRefresh = true) },
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.reset), contentDescription = "try again")
                        }
                    }
                }
            } else {
                ProductList(productResponse = productResponse!!, setFavourite = {
                    isFavourite = !isFavourite
                }, isFavourite = isFavourite)
            }
        }
    }
}

@Composable
private fun HomeHeading(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Good, Morning",
                fontFamily = bodyFontFamily,
                fontSize = 24.sp
            )
            Text(
                text = "Arthur",
                fontSize = 18.sp
            )
        }
//        Image(
//            imageVector = Icons.Filled.Person, contentDescription = null,
//            modifier = Modifier
//                .size(40.dp)
//                .border(
//                    width = 1.dp,
//                    shape = CircleShape,
//                    color = Color.LightGray
//                )
//                .padding(5.dp)
//        )
    }
}

@Composable
private fun SearchLayout(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            maxLines = 1,
            label = {
                Text(
                    text = "search product",
                    color = Color.DarkGray,
                    modifier = Modifier.align(alignment = Alignment.CenterVertically)
                )
            },
            shape = RoundedCornerShape(30.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "search",
                    tint = Color.DarkGray
                )
            },
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        )
        Spacer(
            modifier = Modifier
                .width(15.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.filter),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .border(
                    width = 1.dp,
                    shape = CircleShape,
                    color = Color.LightGray
                )
                .padding(5.dp)
        )
    }
}

@Composable
private fun FilterChips(modifier: Modifier = Modifier) {
    val filterList =
        listOf("Electronics", "Clothing", "Home & Kitchen", "Beauty & Health", "Sports", "Books")

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(count = filterList.size) { index ->
            Box(
                modifier = Modifier
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                Text(
                    text = filterList[index],
                    modifier = Modifier.padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 4.dp,
                        bottom = 4.dp
                    ),
                    fontWeight = FontWeight(500)
                )
            }
        }
    }
}

@Composable
private fun ProductItem(
    product: ProductDto,
    modifier: Modifier = Modifier,
    imageHeight: Dp,
    iconSize: Dp,
    setFavourite: (Boolean) -> Unit,
    isFavourite: Boolean
) {
    Card(
        onClick = { },
        modifier = modifier
            .padding(4.dp)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier.wrapContentSize()
        ) {
            val context = LocalContext.current
            Box(
                modifier = Modifier.background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(20.dp)
                )
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
                        .fillMaxWidth()
                        .height(imageHeight)
                        .clip(shape = RoundedCornerShape(14.dp)),
                    contentScale = ContentScale.FillBounds
                )
                IconToggleButton(
                    checked = false, onCheckedChange = setFavourite,
                    modifier = Modifier
                        .align(alignment = Alignment.TopEnd)
                        .padding(10.dp)
                        .size(iconSize)
                        .border(
                            width = 1.dp,
                            shape = CircleShape,
                            color = Color.DarkGray,
                        )
                        .padding(6.dp)

                ) {
                    Icon(
                        imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "bookmark"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            ProductText(
                product = product,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 8.dp)
            )
        }
    }
}

@Composable
private fun ProductText(product: ProductDto, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = product.name,
                fontFamily = courgetteFontFamily,
                fontWeight = FontWeight(700),
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )
            Text(
                text = "$${product.price}",
                fontFamily = courgetteFontFamily,
                fontWeight = FontWeight(450),
                fontSize = MaterialTheme.typography.body1.fontSize
            )
        }
        Row {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "4.6",
                fontFamily = courgetteFontFamily,
                fontWeight = FontWeight(450),
                fontSize = MaterialTheme.typography.body1.fontSize
            )
        }
    }
}

@Composable
private fun ProductList(
    productResponse: ProductResponse,
    modifier: Modifier = Modifier,
    setFavourite: (Boolean) -> Unit,
    isFavourite: Boolean
) {
    val imageHeights = listOf(150.dp, 180.dp, 160.dp, 190.dp, 170.dp)
    val iconSizes = listOf(24.dp, 30.dp, 26.dp, 34.dp, 28.dp)

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 20.dp,
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        content = {
            items(productResponse.itemCount) { index ->
                val imageHeight = imageHeights[index % imageHeights.size]
                val iconSize = iconSizes[index % iconSizes.size]
                ProductItem(
                    product = productResponse.productDto[index],
                    imageHeight = imageHeight,
                    iconSize = iconSize,
                    setFavourite = setFavourite,
                    isFavourite = isFavourite
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(alignment = Alignment.Center)) {
            Text(
                text = "No product to display. our database is currently empty",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Icon(painter = painterResource(id = R.drawable.reset), contentDescription = "try again")
            }
        }
    }
}