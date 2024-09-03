package com.heungjun.popuplogintoken.api

import android.util.Log
import com.heungjun.popuplogintoken.model.CheckCompanyEmailResponse
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CheckCompanyEmailRepo {

    suspend fun checkEmailAvailability(email: String): CheckCompanyEmailResponse? {
        val url = "http://10.0.2.2:8080/auth/company/signup/check-email"

        return withContext(Dispatchers.IO) {
            try {
                val response: CheckCompanyEmailResponse = KtorClient.httpClient.post(url) {
                    setBody(mapOf("email" to email))
                }.body()

                Log.d("CheckCompanyEmailRepo", "Response: $response")  // 응답을 로그로 출력

                response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("CheckCompanyEmailRepo", "Failed to check email", e)  // 예외 발생 시 로그 출력
                null
            }
        }
    }
}