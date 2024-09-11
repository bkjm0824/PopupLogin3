package com.heungjun.popuplogintoken.api

import android.util.Log
import com.heungjun.popuplogintoken.model.CheckCompanyEmailResponse
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// 회사 이메일 중복 확인과 관련된 데이터를 처리하는 저장소 객체
object CheckCompanyEmailRepo {

    // 서버에서 회사 이메일 사용 가능 여부를 체크하는 함수
    suspend fun checkEmailAvailability(email: String): CheckCompanyEmailResponse? {
        val url = "http://10.0.2.2:8080/auth/company/signup/check-email"  // API 엔드포인트 URL

        return withContext(Dispatchers.IO) {
            try {
                // Ktor 클라이언트를 사용해 POST 요청을 보내고, 응답을 CheckCompanyEmailResponse 객체로 파싱
                val response: CheckCompanyEmailResponse = KtorClient.httpClient.post(url) {
                    setBody(mapOf("email" to email))
                }.body()

                Log.d("CheckCompanyEmailRepo", "Response: $response")  // 응답을 로그로 출력

                response  // 서버 응답을 반환
            } catch (e: Exception) {
                // 예외 발생 시 스택 트레이스를 출력하고 null 반환
                e.printStackTrace()
                Log.e("CheckCompanyEmailRepo", "Failed to check email", e)
                null
            }
        }
    }
}