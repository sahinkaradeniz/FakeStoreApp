package com.skapps.fakestoreapp.data.mapper

import com.skapps.fakestoreapp.data.models.ProductDto
import com.skapps.fakestoreapp.data.models.ProductsResponseDto
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.ProductsListEntity


fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(
        brand = brand,
        category = category,
        description = description,
        discountPercentage = discountPercentage,
        id = id,
        images = images,
        oldPrice = price,
        rating = rating,
        stock = stock,
        thumbnail = thumbnail,
        title = title,
        basketQuantity = 0,
        isFavorite = false
    )
}

fun ProductsResponseDto.toEntity(): ProductsListEntity {
    return ProductsListEntity(
        limit = limit,
        products = products.map { it.toEntity() },
        skip = skip,
        total = total
    )
}