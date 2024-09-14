package com.heungjun.popuplogintoken.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.api.NetworkRepository
import com.heungjun.popuplogintoken.model.PopupStore
import com.heungjun.popuplogintoken.model.UserReservation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PreOrderViewModel : ViewModel() {
    private val repository = NetworkRepository()

    // PopupStores 관련 상태 관리
    private val _popupStores = MutableStateFlow<List<PopupStore>>(emptyList())
    val popupStores: StateFlow<List<PopupStore>> = _popupStores

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // 예약 관련 상태 관리
    private val _reservationResult = MutableStateFlow<String?>(null)
    val reservationResult: StateFlow<String?> = _reservationResult

    init {
        fetchPopupStores()
    }

    // 팝업 스토어 목록을 가져오는 함수
    private fun fetchPopupStores() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getPopupStores()
                _popupStores.value = response.popupStores
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    // 사용자 예약을 처리하는 함수
    fun makeReservation(userReservation: UserReservation) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.makeReservation(userReservation)
            }.onSuccess { response ->
                _reservationResult.value = response?.message ?: "Success"
                Log.d("PreOrderViewModel", "Reservation successful")
            }.onFailure {
                _reservationResult.value = "Failed to make reservation"
                Log.d("PreOrderViewModel", "Reservation failed", it)
            }
        }
    }
}
