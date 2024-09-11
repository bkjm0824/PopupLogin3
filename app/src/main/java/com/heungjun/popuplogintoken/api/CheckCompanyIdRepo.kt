package com.heungjun.popuplogintoken.api

import android.util.Log
import com.heungjun.popuplogintoken.model.CheckCompanyIdResponse
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// 회사 ID 중복 확인과 관련된 데이터를 처리하는 저장소 객체
object CheckCompanyIdRepo {

    // 서버에서 사업자 번호 사용 가능 여부를 체크하는 함수
    suspend fun checkCompanyIdAvailability(companyId: String): CheckCompanyIdResponse? {
        val url = "http://10.0.2.2:8080/auth/company/signup/check-companyid"  // API 엔드포인트 URL

        return withContext(Dispatchers.IO) {
            try {
                // Ktor 클라이언트를 사용해 POST 요청을 보내고, 응답을 CheckCompanyIdResponse 객체로 파싱
                val response: CheckCompanyIdResponse = KtorClient.httpClient.post(url) {
                    setBody(CheckCompanyIdRequest(companyId))
                }.body()

                Log.d("CompanyRepo", "API Response: $response")
                response
            } catch (e: Exception) {
                // 예외 발생 시 스택 트레이스를 출력하고 null 반환
                e.printStackTrace()
                Log.e("CompanyRepo", "Failed to check company ID", e)
                null
            }
        }
    }
}