package com.heungjun.popuplogintoken.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.api.NetworkRepository
import com.heungjun.popuplogintoken.model.PopupStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SearchUiState(
    val isLoading: Boolean = false,
    val popupStores: List<PopupStore> = emptyList(),
    val error: String? = null
)

class SearchViewModel(private val repository: NetworkRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    fun search(query: String) {
        _uiState.value = SearchUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val response = repository.searchPopupStores(query)
                _uiState.value = SearchUiState(popupStores = response.popupStores)
            } catch (e: Exception) {
                _uiState.value = SearchUiState(error = e.message)
            }
        }
    }
}


class SearchViewModelFactory(
    private val repository: NetworkRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
