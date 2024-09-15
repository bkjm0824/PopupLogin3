package com.heungjun.popuplogintoken.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.api.NetworkRepository
import com.heungjun.popuplogintoken.model.HeartResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HeartViewModel(private val repository: NetworkRepository) : ViewModel() {

    private val _heartResponse = MutableStateFlow<HeartResponse?>(null)
    val heartResponse: StateFlow<HeartResponse?> = _heartResponse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Function to send heart request
    fun sendHeart(popupStoreId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.sendHeartRequest(popupStoreId)
                _heartResponse.value = response
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}


class HeartViewModelFactory(
    private val repository: NetworkRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HeartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}