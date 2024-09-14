package com.heungjun.popuplogintoken.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.api.NetworkRepository
import com.heungjun.popuplogintoken.model.PopupStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: NetworkRepository = NetworkRepository()
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        fetchPopupStores()
    }

    private fun fetchPopupStores() {
        viewModelScope.launch {
            try {
                val response = repository.getPopupStores()
                _homeState.update {
                    it.copy(
                        isLoading = false,
                        popupStores = response.popupStores,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _homeState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }
}

data class HomeState(
    val popupStores: List<PopupStore> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
