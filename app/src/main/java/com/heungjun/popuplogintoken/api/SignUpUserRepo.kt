package com.heungjun.popuplogintoken.api

import android.util.Log
import com.heungjun.popuplogintoken.model.SignUpResponse
import com.heungjun.popuplogintoken.model.SignUpUser
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SignUpUserRepo {

    // 회원가입을 처리하는 함수
    suspend fun signUpUser(signUpUser: SignUpUser): SignUpResponse? {
        val url = "http://10.0.2.2:8080/auth/user/signup"  // API 엔드포인트 URL

        return withContext(Dispatchers.IO) {
            try {
                // Ktor 클라이언트를 사용해 POST 요청을 보내고, 응답을 SignUpResponse 객체로 파싱
                val response: SignUpResponse = KtorClient.httpClient.post(url) {
                    setBody(signUpUser)  // SignUpUser 객체를 요청 바디에 설정
                }.body()

                Log.d("SignUpUserRepo", "API Response: $response")
                response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("SignUpUserRepo", "Failed to sign up user", e)
                null
            }
        }
    }
}
