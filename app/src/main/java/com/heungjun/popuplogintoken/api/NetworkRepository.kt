package com.heungjun.popuplogintoken.api

import android.util.Log
import com.heungjun.popuplogintoken.model.ApiResponse2
import com.heungjun.popuplogintoken.model.ApiResponse3
import com.heungjun.popuplogintoken.model.HeartResponse
import com.heungjun.popuplogintoken.model.UserReservation
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class NetworkRepository {

    // Use KtorClient's HttpClient
    private val client = KtorClient.httpClient

    @Throws(Exception::class)
    suspend fun getPopupStores(): ApiResponse2 {
        val url = "http://10.0.2.2:8080/search/popup/all"
        val response: HttpResponse = client.get(url)
        return response.body()
    }

    @Throws(Exception::class)
    suspend fun searchPopupStores(query: String): ApiResponse2 {
        val url = "http://10.0.2.2:8080/search/popup"
        val response: HttpResponse = client.get(url) {
            parameter("query", query)
        }
        return response.body()
    }

    suspend fun makeReservation(userReservation: UserReservation): ApiResponse3<Unit>? {
        val url = "http://10.0.2.2:8080/popup/reservation/user"
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

    // 찜 추가
    suspend fun sendHeartRequest(popupStoreId: Int): HeartResponse? {
        val url = "http://10.0.2.2:8080/heart"
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(mapOf("popupStoreId" to popupStoreId))
                }
                val heartResponse: HeartResponse = response.body()
                heartResponse
            } catch (e: Exception) {
                Log.e("NetworkRepository", "Failed to send heart request", e)
                null
            }
        }
    }

    // 찜 삭제
    suspend fun deleteHeartRequest(popupStoreId: Int): HeartResponse? {
        val url = "http://10.0.2.2:8080/heart"
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.delete(url) {
                    contentType(ContentType.Application.Json)
                    setBody(mapOf("popupStoreId" to popupStoreId))
                }
                val heartResponse: HeartResponse = response.body()
                heartResponse
            } catch (e: Exception) {
                Log.e("NetworkRepository", "Failed to delete heart request", e)
                null
            }
        }
    }

    // 찜 목록 조회
    suspend fun getHeartList(): List<HeartResponse>? {
        val url = "http://10.0.2.2:8080/heart/list"
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.get(url)
                response.body<List<HeartResponse>>()
            } catch (e: Exception) {
                Log.e("NetworkRepository", "Failed to fetch heart list", e)
                null
            }
        }
    }
}
