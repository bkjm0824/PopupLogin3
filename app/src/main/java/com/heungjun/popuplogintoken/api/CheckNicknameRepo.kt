package com.heungjun.popuplogintoken.api

import android.util.Log
import com.heungjun.popuplogintoken.model.CheckNicknameResponse
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CheckNicknameRepo {

    suspend fun checkNicknameAvailability(nickname: String): CheckNicknameResponse? {
        val url = "http://10.0.2.2:8080/auth/user/signup/check-nickname"  // API 엔드포인트 URL

        return withContext(Dispatchers.IO) {
            try {
                // Ktor 클라이언트를 사용해 POST 요청을 보내고, 응답을 CheckNicknameResponse 객체로 파싱
                val response: CheckNicknameResponse = KtorClient.httpClient.post(url) {
                    setBody(CheckNicknameRequest(nickname))
                }.body()

                Log.d("CheckNicknameRepo", "Response: $response")
                response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("CheckNicknameRepo", "Failed to check nickname", e)
                null
            }
        }
    }
}