package com.heungjun.popuplogintoken.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.api.CheckUserEmailRepo
import com.heungjun.popuplogintoken.model.CheckUserEmailResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CheckUserEmailVM : ViewModel() {

    // 이메일 사용 가능 여부를 저장하는 StateFlow, UI에서 이 데이터를 구독하여 상태 변경을 반영
    val emailCheckFlow = MutableStateFlow<CheckUserEmailResponse?>(null)

    // 이메일 사용 가능 여부를 체크하는 함수
    fun checkEmail(email: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                CheckUserEmailRepo.checkEmailAvailability(email)
            }.onSuccess { response ->
                Log.d("CheckUserEmailVM", "onSuccess: Email check completed with response: $response")
                emailCheckFlow.value = response ?: CheckUserEmailResponse(false, "서버 응답 없음")
            }.onFailure { throwable ->
                Log.d("CheckUserEmailVM", "onFailure: Failed to check email", throwable)
                emailCheckFlow.value = CheckUserEmailResponse(false, "네트워크 오류: ${throwable.message}")
            }
        }
    }
}