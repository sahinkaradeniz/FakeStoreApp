package com.skapps.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text

@Composable
fun DetailScreen(
    productId: String?,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Detail Screen -> productId: $productId",
        modifier = modifier
    )
}