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

    @Throws(Exception::class)
    suspend fun sendHeartRequest(popupStoreId: Int): HeartResponse? {
        val url = "http://10.0.2.2:8080/heart"
        return withContext(Dispatchers.IO) {
            try {
                val response: HeartResponse = client.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(mapOf("popupStoreId" to popupStoreId))
                }.body()
                Log.d("NetworkRepository", "Heart Response: $response")
                response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("NetworkRepository", "Failed to send heart request", e)
                null
            }
        }
    }
}
