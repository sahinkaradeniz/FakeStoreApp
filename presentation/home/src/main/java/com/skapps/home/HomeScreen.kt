package com.skapps.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.skapps.common.components.LoadImageFromUrl
import com.skapps.fakestoreapp.core.theme.Purple40
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxSize(),
    onNavigateToDetail: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is HomeSideEffect.ShowError -> {
                    Log.e("HomeScreen", "Error: ${effect.message}")
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    val lazyPagingItems : LazyPagingItems<ProductEntity>? = uiState.products?.collectAsLazyPagingItems()

    if (lazyPagingItems != null) {
        LazyColumn(modifier = modifier) {

            items(lazyPagingItems.itemCount) { index ->
                lazyPagingItems[index]?.let { item ->
                    ProductItem(
                        productEntity = item,
                        onItemClicked = { id ->
                            onNavigateToDetail(item.id.toString())
                        }
                    )
                }
            }

            when (val refreshState = lazyPagingItems.loadState.refresh) {
                is LoadState.Loading -> item { Text("Loading...") }
                is LoadState.Error -> item { Text("Error: ${refreshState.error.message}") }
                else -> {}
            }

            when (val appendState = lazyPagingItems.loadState.append) {
                is LoadState.Loading -> item { Text("Loading more...") }
                is LoadState.Error -> item { Text("Error: ${appendState.error.message}") }
                else -> {}
            }
        }
    } else {
        Text("No data available", modifier = Modifier.padding(16.dp))
    }

}

@Preview(showBackground = true)
@Composable
fun LazyColumnPreview(){
    LazyColumn {
        items(10) {
            ProductItem(
                productEntity = ProductEntity(
                    id = 1,
                    title = "Product Title",
                    description = "Product Description Product Description Product Description Product Description",
                    oldPrice = 100.0,
                    newPrice = 50.0,
                    category = "Category",
                    brand = "Brand",
                    rating = 4.5,
                    stock = 10,
                    discountPercentage = 50.0,
                    thumbnail = "https://picsum.photos/200/300",
                    basketQuantity = 0,
                    isFavorite = false,
                    images = listOf("https://picsum.photos/200/300")
                ),
                onItemClicked = {}
            )
        }
    }
}
@Composable
fun ProductItem(
    productEntity: ProductEntity,
    onItemClicked: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .background(
                MaterialTheme.colorScheme.background,
                RoundedCornerShape(8.dp)
            )
            .padding(end = 16.dp, start = 8.dp)
            .clickable { onItemClicked(productEntity.id)},
        verticalAlignment = Alignment.CenterVertically,

    ) {
        LoadImageFromUrl(
            imageUrl = productEntity.thumbnail,
            modifier = Modifier
                .padding(start = 8.dp, bottom = 12.dp, top = 12.dp)
                .size(52.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = productEntity.title,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = productEntity.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        PriceRow(
            oldPrice = productEntity.oldPrice.toString(),
            price = productEntity.newPrice.toString(),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun PriceRow(
    oldPrice: String,
    price: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End,
    ) {
        Text(text = price, fontSize = 16.sp, color = Purple40)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = oldPrice)
    }
}

