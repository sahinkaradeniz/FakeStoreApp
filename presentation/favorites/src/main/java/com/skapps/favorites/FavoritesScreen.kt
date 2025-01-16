package com.skapps.favorites

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skapps.fakestoreapp.coreui.components.LoadImageFromUrl
import com.skapps.fakestoreapp.coreui.theme.CollectSideEffect
import com.skapps.fakestoreapp.coreui.theme.Purple40
import com.skapps.fakestoreapp.coreui.theme.poppinsFontFamily

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onNavigateToProductDetail: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    CollectSideEffect(
        sideEffect = viewModel.sideEffect,
        onSideEffect = { effect ->
            when (effect) {
                is FavoritesSideEffect.NavigateToProductDetail -> {
                    onNavigateToProductDetail(effect.id)
                }

                is FavoritesSideEffect.ShowErrorGetFavorites -> {
                    Toast.makeText(
                        context,
                        effect.error,
                        Toast.LENGTH_LONG
                    ).show()
                }

                is FavoritesSideEffect.ShowErrorDeleteFavorites -> {
                    Toast.makeText(
                        context,
                        effect.error,
                        Toast.LENGTH_LONG
                    ).show()
                }

                is FavoritesSideEffect.ShowSuccessDeleteFavorites -> {
                    Toast.makeText(
                        context,
                        "Product removed from favorites",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is FavoritesSideEffect.ShowErrorAddToCart -> {
                    Toast.makeText(
                        context,
                        effect.error,
                        Toast.LENGTH_LONG
                    ).show()
                }
                FavoritesSideEffect.ShowSuccessAddToCart -> {
                    Toast.makeText(
                        context,
                        "Product added to cart",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        FavoriteTopBar(
            title = "FAVORITES"
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(alpha = 0.1f))
        ) {
            if (uiState.isEmpty) {
                Text(
                    text = "No favorite products",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            FavoriteList(
                favorites = uiState.favorites,
                onItemClicked = { productId ->
                    viewModel.onAction(FavoritesUiAction.ProductClicked(productId.toString()))
                },
                onFavoriteClicked = { id ->
                    viewModel.onAction(
                        FavoritesUiAction.FavoriteButtonClicked(
                            loadingMessage = "The process of changing favorites...",
                            id = id.toString()
                        )
                    )
                },
                addToCart = { item ->
                    viewModel.onAction(
                        FavoritesUiAction.AddToCartClicked(
                            loadingMessage = "The product is being added to cart...",
                           item = item
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun FavoriteTopBar(
    title: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 12.dp),
            text = title,
            fontSize = 20.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun FavoriteList(
    favorites: List<FavoriteUiModel>,
    onItemClicked: (Int) -> Unit,
    onFavoriteClicked: (Int) -> Unit,
    addToCart: (FavoriteUiModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)
    ) {
        items(favorites.size) { index ->
            val item = favorites[index]
            FavoriteItem(
                favoriteItem = item,
                onItemClicked = { onItemClicked(item.id) },
                onFavoriteClicked = { onFavoriteClicked(item.id) },
                addToCart = { addToCart(item) }
            )
        }
    }
}

@Composable
fun FavoriteItem(
    favoriteItem: FavoriteUiModel,
    onItemClicked: () -> Unit,
    onFavoriteClicked: (Int) -> Unit,
    addToCart: (Int) -> Unit
) {

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onItemClicked() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ) {
            LoadImageFromUrl(
                imageUrl = favoriteItem.thumbnail,
                modifier = Modifier
                    .size(64.dp)
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = favoriteItem.title,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = favoriteItem.description,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row (
                    modifier = Modifier.fillMaxWidth(),
                ){
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "${favoriteItem.price} TL",
                        fontSize = 14.sp,
                        color = Purple40
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = Color.Black,
                        contentDescription = "Add Icon",
                        modifier = Modifier.clickable {
                            addToCart(favoriteItem.id)
                        }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onFavoriteClicked(
                            favoriteItem.id
                        )
                    }
            ) {
                Icon(
                    imageVector = if (favoriteItem.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    tint = if (favoriteItem.isFavorite) Color.Red else Color.Black,
                    contentDescription = "Favorite Icon"
                )
            }
        }
    }
}