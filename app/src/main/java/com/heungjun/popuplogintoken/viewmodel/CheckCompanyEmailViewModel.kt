package com.heungjun.popuplogintoken.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.api.CheckCompanyEmailRepo
import com.heungjun.popuplogintoken.model.CheckCompanyEmailResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CheckCompanyEmailVM : ViewModel() {

    // 이메일 사용 가능 여부를 저장하는 StateFlow, UI에서 이 데이터를 구독하여 상태 변경을 반영
    val emailCheckFlow = MutableStateFlow<CheckCompanyEmailResponse?>(null)

    // 이메일 사용 가능 여부를 체크하는 함수
    fun checkEmail(email: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                // CheckCompanyEmailRepo의 checkEmailAvailability 함수를 호출하여 이메일 체크 결과를 가져옴
                CheckCompanyEmailRepo.checkEmailAvailability(email)
            }.onSuccess { response ->
                Log.d("CheckCompanyEmailVM", "onSuccess: Email check completed")
                emailCheckFlow.value = response  // 결과를 StateFlow에 설정
            }.onFailure {
                // 데이터 가져오기에 실패한 경우 로그에 실패 메시지 출력
                Log.d("CheckCompanyEmailVM", "onFailure: Failed to check email", it)
                emailCheckFlow.value = null  // 실패 시 null로 설정
            }
        }
    }
}