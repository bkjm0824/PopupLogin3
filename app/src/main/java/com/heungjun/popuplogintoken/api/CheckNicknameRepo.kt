package com.heungjun.popuplogintoken.api

import android.util.Log
import com.heungjun.popuplogintoken.model.CheckNicknameResponse
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// 닉네임 중복 확인과 관련된 데이터를 처리하는 저장소 객체
object CheckNicknameRepo {

    // 서버에서 닉네임 사용 가능 여부를 체크하는 함수
    suspend fun checkNicknameAvailability(nickname: String): CheckNicknameResponse? {
        val url = "http://10.0.2.2:8080/auth/user/signup/check-nickname"  // API 엔드포인트 URL

        return withContext(Dispatchers.IO) {
            try {
                // Ktor 클라이언트를 사용해 POST 요청을 보내고, 응답을 CheckNicknameResponse 객체로 파싱
                val response: CheckNicknameResponse = KtorClient.httpClient.post(url) {
                    setBody(CheckNicknameRequest(nickname))  // 닉네임을 요청 바디에 설정
                }.body()

                Log.d("CheckNicknameRepo", "Response: $response")  // 응답을 로그로 출력
                response  // 서버 응답을 반환
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("CheckNicknameRepo", "Failed to check nickname", e)  // 에러 로그 출력
                null  // 예외 발생 시 null 반환
            }
        }
    }
}