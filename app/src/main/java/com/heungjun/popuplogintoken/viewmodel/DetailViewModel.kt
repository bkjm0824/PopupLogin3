package com.heungjun.popuplogintoken.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.api.NetworkRepository
import com.heungjun.popuplogintoken.model.PopupStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: NetworkRepository = NetworkRepository()
) : ViewModel() {
    private val _detailState = MutableStateFlow(DetailState())
    val detailState = _detailState.asStateFlow()

    fun fetchPopupStoreById(id: Int) {
        viewModelScope.launch {
            try {
                _detailState.update { it.copy(isLoading = true) }
                val response = repository.getPopupStores()
                val store = response.popupStores.find { it.id == id }
                if (store != null) {
                    _detailState.update {
                        it.copy(isLoading = false, popupStore = store, error = null)
                    }
                } else {
                    _detailState.update {
                        it.copy(isLoading = false, error = "팝업 스토어를 찾을 수 없습니다.")
                    }
                }
            } catch (e: Exception) {
                _detailState.update {
                    it.copy(isLoading = false, error = e.message)
                }
            }
        }
    }
}


data class DetailState(
    val popupStore: PopupStore? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
