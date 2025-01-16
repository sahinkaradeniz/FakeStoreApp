package com.skapps.fakestoreapp

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.skapps.fakestoreapp.core.base.BaseViewModel
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.core.loading.LoadingType
import com.skapps.fakestoreapp.coreui.theme.logError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val globalLoadingManager: GlobalLoadingManager
) : BaseViewModel<MainViewUiState, MainViewUiState, MainSideEffect>(
    initialState = MainViewUiState(),
    globalLoadingManager = globalLoadingManager
) {
    init {
        observeLoadingStates()
    }

    private fun observeLoadingStates() {
        viewModelScope.launch {
            globalLoadingManager.activeLoadings.collect { activeLoadings ->
                val globalVisible = activeLoadings.contains(LoadingType.Global)
                val partialMessages = activeLoadings
                    .filterIsInstance<LoadingType.Partial>()
                    .map { it.message }
                    .firstOrNull()
                val partialVisible = !partialMessages.isNullOrEmpty()
                updateUiState {
                    copy(
                        isGlobalLoadingVisible = globalVisible,
                        partialLoadingMessages = partialMessages,
                        isPartialLoadingVisible = partialVisible
                    )
                }
            }
        }
    }
    override fun onAction(uiAction: MainViewUiState) {

    }

    private fun test(){
        viewModelScope.launch {
            globalLoadingManager.activeLoadings.collect { activeLoadings ->
                val isMyListLoadingActive = LoadingType.Local("MyList") in activeLoadings

                if (isMyListLoadingActive) {
                    // showMyListLoading()
                } else {
                    // hideMyListLoading()
                }
            }
        }


    }
}
