package com.skapps.fakestoreapp.coreui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skapps.fakestoreapp.coreui.theme.poppinsFontFamily

@Composable
fun AppTopBar(
    title: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = 12.dp),
            text = title,
            fontSize = 20.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
        )
    }
}