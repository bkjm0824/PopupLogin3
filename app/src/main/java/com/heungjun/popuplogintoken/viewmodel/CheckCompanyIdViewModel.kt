package com.heungjun.popuplogintoken.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.api.CheckCompanyIdRepo
import com.heungjun.popuplogintoken.model.CheckCompanyIdResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CompanyVM : ViewModel() {

    // 사업자 번호 사용 가능 여부를 저장하는 StateFlow, UI에서 이 데이터를 구독하여 상태 변경을 반영
    val companyIdCheckFlow = MutableStateFlow<CheckCompanyIdResponse?>(null)

    // 사업자 번호 사용 가능 여부를 체크하는 함수
    fun checkCompanyId(companyId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                // CompanyRepo의 checkCompanyIdAvailability 함수를 호출하여 사업자 번호 체크 결과를 가져옴
                CheckCompanyIdRepo.checkCompanyIdAvailability(companyId)
            }.onSuccess { response ->
                Log.d("CompanyVM", "onSuccess: Company ID check completed with response: $response")
                companyIdCheckFlow.value = response ?: CheckCompanyIdResponse(false, "서버 응답 없음")
            }.onFailure { throwable ->
                Log.d("CompanyVM", "onFailure: Failed to check company ID", throwable)
                companyIdCheckFlow.value = CheckCompanyIdResponse(false, "네트워크 오류: ${throwable.message}")
            }
        }
    }
}