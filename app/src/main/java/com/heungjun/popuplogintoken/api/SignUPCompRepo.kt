package com.heungjun.popuplogintoken.api

import android.util.Log
import com.heungjun.popuplogintoken.model.ComSignUP
import com.heungjun.popuplogintoken.model.SignUpCompResponse
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SignUpCompRepo {

    // 기업 회원가입을 처리하는 함수
    suspend fun signUpCompany(comSignUP: ComSignUP): SignUpCompResponse? {
        val url = "http://10.0.2.2:8080/auth/company/signup"  // API 엔드포인트 URL

        return withContext(Dispatchers.IO) {
            try {
                // Ktor 클라이언트를 사용해 POST 요청을 보내고, 응답을 SignUpCompResponse 객체로 파싱
                val response: SignUpCompResponse = KtorClient.httpClient.post(url) {
                    setBody(comSignUP)  // ComSignUP 객체를 요청 바디에 설정
                }.body()

                Log.d("SignUpCompRepo", "API Response: $response")
                response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("SignUpCompRepo", "Failed to sign up company", e)
                null
            }
        }
    }
}
