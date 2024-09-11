package com.heungjun.popuplogintoken.api

import com.heungjun.popuplogintoken.model.CategoriesResponse
import com.heungjun.popuplogintoken.model.Category
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// 카테고리 관련 데이터를 처리하는 저장소 객체
object CategoryRepo {

    // 서버에서 카테고리 목록을 가져오는 함수
    suspend fun fetchCategories(): List<Category>? {
        val url = "http://10.0.2.2:8080/user/categories"  // API 엔드포인트 URL

        return withContext(Dispatchers.IO) {
            try {
                // Ktor 클라이언트를 사용해 GET 요청을 보내고, 응답을 CategoriesResponse 객체로 파싱
                val response: CategoriesResponse = KtorClient.httpClient.get(url).body()

                // 응답이 성공(result가 true)이면 카테고리 데이터를 반환, 그렇지 않으면 null 반환
                if (response.result) {
                    response.data
                } else {
                    null
                }
            } catch (e: Exception) {
                // 예외 발생 시 스택 트레이스를 출력하고 null을 반환
                e.printStackTrace()
                null
            }
        }
    }
}