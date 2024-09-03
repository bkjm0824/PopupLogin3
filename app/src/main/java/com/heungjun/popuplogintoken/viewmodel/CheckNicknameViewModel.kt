package com.heungjun.popuplogintoken.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.api.CheckNicknameRepo
import com.heungjun.popuplogintoken.model.CheckNicknameResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CheckNicknameVM : ViewModel() {

    // 닉네임 사용 가능 여부를 저장하는 StateFlow, UI에서 이 데이터를 구독하여 상태 변경을 반영
    val nicknameCheckFlow = MutableStateFlow<CheckNicknameResponse?>(null)

    // 닉네임 사용 가능 여부를 체크하는 함수
    fun checkNickname(nickname: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                CheckNicknameRepo.checkNicknameAvailability(nickname)
            }.onSuccess { response ->
                Log.d("CheckNicknameVM", "onSuccess: Nickname check completed with response: $response")
                nicknameCheckFlow.value = response
            }.onFailure { throwable ->
                Log.d("CheckNicknameVM", "onFailure: Failed to check nickname", throwable)
                nicknameCheckFlow.value = null
            }
        }
    }
}