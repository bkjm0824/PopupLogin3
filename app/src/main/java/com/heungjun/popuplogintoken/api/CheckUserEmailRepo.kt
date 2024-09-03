package com.heungjun.popuplogintoken.api

import android.util.Log
import com.heungjun.popuplogintoken.model.CheckUserEmailResponse
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CheckUserEmailRepo {

    // 서버에서 이메일 사용 가능 여부를 체크하는 함수
    suspend fun checkEmailAvailability(email: String): CheckUserEmailResponse? {
        val url = "http://10.0.2.2:8080/auth/user/signup/check-email"  // API 엔드포인트 URL

        return withContext(Dispatchers.IO) {
            try {
                // Ktor 클라이언트를 사용해 POST 요청을 보내고, 응답을 CheckUserEmailResponse 객체로 파싱
                val response: CheckUserEmailResponse = KtorClient.httpClient.post(url) {
                    setBody(CheckUserEmailRequest(email))
                }.body()

                Log.d("CheckUserEmailRepo", "API Response: $response")
                response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("CheckUserEmailRepo", "Failed to check email", e)
                null
            }
        }
    }
}