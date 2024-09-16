package com.heungjun.popuplogintoken.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.api.NetworkRepository
import com.heungjun.popuplogintoken.model.ApiResponse2
import com.heungjun.popuplogintoken.model.ReservationList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Combined state classes
sealed class DashboardState {
    // Popup Stores States
    sealed class PopupStoreState : DashboardState() {
        object Loading : PopupStoreState()
        data class Success(val apiResponse: ApiResponse2) : PopupStoreState()
        data class Error(val message: String) : PopupStoreState()
    }

    // Reservation List States
    sealed class ReservationListState : DashboardState() {
        object Loading : ReservationListState()
        data class Success(val reservationList: ReservationList) : ReservationListState()
        data class Error(val message: String) : ReservationListState()
    }
}

class CombinedViewModel(
    private val repository: NetworkRepository
) : ViewModel() {

    // Popup Stores State
    private val _popupStoresState = MutableStateFlow<DashboardState.PopupStoreState>(DashboardState.PopupStoreState.Loading)
    val popupStoresState: StateFlow<DashboardState.PopupStoreState> = _popupStoresState

    // Reservation List State
    private val _reservationListState = MutableStateFlow<DashboardState.ReservationListState>(DashboardState.ReservationListState.Loading)
    val reservationListState: StateFlow<DashboardState.ReservationListState> = _reservationListState

    // Function to fetch popup stores
    fun getPopupStores() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getPopupStores()
                _popupStoresState.value = DashboardState.PopupStoreState.Success(response)
            } catch (e: Exception) {
                _popupStoresState.value = DashboardState.PopupStoreState.Error(e.message ?: "Unknown error")
            }
        }
    }

    // Function to fetch user reservation list
    fun getUserReservationList() {
        viewModelScope.launch(Dispatchers.IO) {
            _reservationListState.value = DashboardState.ReservationListState.Loading
            try {
                val reservationList = repository.getUserReservationList()
                if (reservationList != null) {
                    _reservationListState.value = DashboardState.ReservationListState.Success(reservationList)
                } else {
                    _reservationListState.value = DashboardState.ReservationListState.Error("Failed to fetch data")
                }
            } catch (e: Exception) {
                _reservationListState.value = DashboardState.ReservationListState.Error(e.message ?: "Unknown error")
            }
        }
    }
}



class DashboardViewModelFactory(
    private val repository: NetworkRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CombinedViewModel::class.java)) {
            return CombinedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
