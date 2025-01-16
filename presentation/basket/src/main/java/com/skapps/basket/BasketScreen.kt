package com.skapps.basket

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skapps.fakestoreapp.coreui.components.AppTopBar
import com.skapps.fakestoreapp.coreui.components.LoadImageFromUrl
import com.skapps.fakestoreapp.coreui.theme.CollectSideEffect
import com.skapps.fakestoreapp.coreui.R
@Composable
fun BasketScreen(
    viewModel: BasketViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    CollectSideEffect(
        sideEffect = viewModel.sideEffect,
        onSideEffect = { effect ->
            when (effect) {
                is BasketSideEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                }

                BasketSideEffect.CheckoutSuccess -> {
                    Toast.makeText(context, "Checkout successful!", Toast.LENGTH_LONG).show()
                }
            }
        }
    )


    LaunchedEffect(Unit) {
        viewModel.onAction(BasketUiAction.LoadBasket)
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "BASKET"
            )
        },
        bottomBar = {
            BasketSummary(
                price = uiState.totalPrice,
                discount = uiState.totalDiscount,
                total = uiState.total
            ) {
                viewModel.onAction(BasketUiAction.Checkout)
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            if (uiState.isEmpty) {
                Text(
                    text = "No products in basket",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.items) { item ->
                        BasketItemRow(
                            item = item,
                            onIncrease = {
                                viewModel.onAction(
                                    BasketUiAction.IncreaseQuantity(
                                        itemId = item.id,
                                        loadingMessage = "Quantity is being increased..."
                                    )
                                )
                            },
                            onDecrease = {
                                viewModel.onAction(
                                    BasketUiAction.DecreaseQuantity(
                                        itemId = item.id,
                                        loadingMessage = "Quantity is being decreased..."
                                    )
                                )
                            },
                            onRemove = {
                                viewModel.onAction(
                                    BasketUiAction.RemoveItem(
                                        itemId = item.id,
                                        loadingMessage = "Product is being removed..."
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BasketItemRow(
    item: BasketItemUiModel,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = 0.6f))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ürün resmi
            LoadImageFromUrl(
                imageUrl = item.image,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Ürün başlığı ve fiyat bilgisi
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Row {
                    Text(
                        text = "${item.price} TL",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    if (item.oldPrice > item.price) {
                        Text(
                            text = "${item.oldPrice} TL",
                            fontSize = 12.sp,
                            color = Color.DarkGray,
                            style = TextStyle(textDecoration = TextDecoration.LineThrough)
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onDecrease() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.minus_24),
                        contentDescription = "Decrease quantity"
                    )
                }
                Text(
                    text = "${item.quantity}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                IconButton(onClick = { onIncrease() }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase quantity"
                    )
                }
                IconButton(onClick = { onRemove() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete item",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun BasketSummary(
    price: Double,
    discount: Double,
    total: Double,
    onCheckoutClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Price:")
                Text(text = "Discount:")
                Text(text = "Total:")
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "${price.toInt()} TL")
                Text(text = "${discount.toInt()} TL")
                Text(text = "${total.toInt()} TL")
            }
        }

        // CHECKOUT Butonu
        Button(
            onClick = onCheckoutClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text(text = "CHECKOUT", fontSize = 16.sp, color = Color.Black)
        }
    }
}