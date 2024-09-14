package com.heungjun.popuplogintoken.api

import android.util.Log
import com.heungjun.popuplogintoken.model.ComSignUP
import com.heungjun.popuplogintoken.model.SignUpCompResponse
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SignUpCompRepo {
    suspend fun signUpCompany(comSignUP: ComSignUP): SignUpCompResponse? {
        val url = "http://10.0.2.2:8080/auth/company/signup"

        return withContext(Dispatchers.IO) {
            try {
                // 요청을 보내고 응답을 SignUpCompResponse로 파싱
                val response: SignUpCompResponse = KtorClient.httpClient.post(url) {
                    setBody(comSignUP)
                }.body()

                Log.d("SignUpCompRepo", "API Response: $response")
                response
            } catch (e: JsonConvertException) {
                // JSON 변환 오류 처리
                Log.e("SignUpCompRepo", "JSON Parse Error: ${e.message}")
                null
            } catch (e: Exception) {
                // 기타 예외 처리
                e.printStackTrace()
                Log.e("SignUpCompRepo", "Failed to sign up company", e)
                null
            }
        }
    }
}