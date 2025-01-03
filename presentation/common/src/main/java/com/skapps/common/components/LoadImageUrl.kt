package com.skapps.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalInspectionMode
import coil.compose.AsyncImage

@Composable
fun LoadImageFromUrl(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    error: Painter? = null
) {
    // Preview ortamında mı çalıştığımızı kontrol ediyoruz
    val isPreview = LocalInspectionMode.current

    if (isPreview) {
        // Eğer Preview ortamındaysak, ağ isteği yerine bir yer tutucu gösteriyoruz
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Gray) // Yer tutucu olarak gri arka plan
        )
    } else {
        // Normal çalışmada AsyncImage ile ağdan resim yüklüyoruz
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = modifier,
            placeholder = placeholder,
            error = error
        )
    }
}