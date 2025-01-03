package com.skapps.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skapps.common.components.LoadImageFromUrl
import com.skapps.fakestoreapp.core.theme.Purple40

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    onNavigateToDetail: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to Home!",
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = { onNavigateToDetail("1234") },
            modifier = Modifier.padding(16.dp) // Butona biraz boşluk ekledik
        ) {
            Text(text = "Go to Detail")
        }
    }
}

@Preview(showBackground = true, heightDp = 300, widthDp = 400,)
@Composable
fun ProductItemPreview() {
    ProductItem(
        name = "Sample Product",
        description = "This is a sample product",
        oldPrice = "$200",
        price = "$100"
    )
}

@Composable
fun ProductItem(
    name: String,
    description: String,
    oldPrice: String,
    price: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp)) // Arka plan ve köşe yarıçapı birlikte
            .padding(end = 16.dp, start = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LoadImageFromUrl(
            imageUrl = "https://picsum.photos/200/300",
            contentDescription = "Product Image",
            modifier = Modifier
                .padding(start = 8.dp, bottom = 12.dp, top = 12.dp)
                .size(52.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = name, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = description)
        }
        Spacer(modifier = Modifier.width(12.dp))
        PriceRow(
            oldPrice = oldPrice,
            price = price
        )
    }
}


@Composable
fun PriceRow(
    oldPrice: String,
    price: String,
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End,
    ){
        Text(text = oldPrice, fontSize = 16.sp, color = Purple40 )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = price)
    }
}



@Preview(showBackground = true, heightDp = 300, widthDp = 400)
@Composable
fun previewLazyColumn() {
    LazyColumn{
        items(10) {
            ProductItem(
                name = "Sample Product",
                description = "This is a sample product",
                oldPrice = "$200",
                price = "$100"
            )

        }
    }
}