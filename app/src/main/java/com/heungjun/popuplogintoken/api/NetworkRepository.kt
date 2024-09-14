package com.heungjun.popuplogintoken.api

import android.util.Log
import com.heungjun.popuplogintoken.model.ApiResponse
import com.heungjun.popuplogintoken.model.ApiResponse2
import com.heungjun.popuplogintoken.model.ApiResponse3
import com.heungjun.popuplogintoken.model.UserReservation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class NetworkRepository {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true })
        }
    }

    @Throws(Exception::class)
    suspend fun getPopupStores(): ApiResponse2 {
        val url = "http://10.0.2.2:8080/search/popup/all"
        val response = client.get(url)
        return response.body()
    }

    @Throws(Exception::class)
    suspend fun searchPopupStores(query: String): ApiResponse2 {
        val url = "http://10.0.2.2:8080/search/popup"
        val response = client.get(url) {
            parameter("query", query)
        }
        return response.body()
    }

    // UserReservationRepo의 makeReservation 메서드를 여기에 통합
    suspend fun makeReservation(userReservation: UserReservation): ApiResponse3<Unit>? {
        val url = "http://10.0.2.2:8080/popup/reservation/user" // 사용자 예약 API 엔드포인트
        return withContext(Dispatchers.IO) {
            try {
                val response: ApiResponse3<Unit> = client.post(url) {
                    setBody(userReservation)
                }.body()
                Log.d("NetworkRepository", "Response: $response")
                response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("NetworkRepository", "Failed to make reservation", e)
                null
            }
        }
    }
}
