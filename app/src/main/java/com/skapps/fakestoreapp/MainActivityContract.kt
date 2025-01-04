package com.skapps.fakestoreapp

import androidx.compose.runtime.Immutable


@Immutable
data class MainViewUiState(
    val isGlobalLoadingVisible: Boolean = false,
    val isPartialLoadingVisible: Boolean = false,
    val partialLoadingMessages: String? = null
)


@Immutable
sealed class MainSideEffect {

}

@Immutable
sealed class MainUiActions {

}