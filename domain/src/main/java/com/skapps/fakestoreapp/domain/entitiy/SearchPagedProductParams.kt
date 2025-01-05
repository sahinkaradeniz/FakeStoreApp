package com.skapps.fakestoreapp.domain.entitiy

data class SearchPagedProductParams(
    val query: String,
    val sortType: SortType = SortType.NONE,
    val pageSize: Int = 20
)