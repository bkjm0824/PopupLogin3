package com.heungjun.popuplogintoken.dto

import com.heungjun.popuplogintoken.model.ApiResponse
import com.heungjun.popuplogintoken.model.Login

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)

interface AuthRepository {
    suspend fun login(login: Login): ApiResponse
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun fetchAccessToken(): String
    suspend fun fetchRefreshToken(): String
    suspend fun refreshToken(): TokenResponse?
    suspend fun loginWithCredentials(email: String, password: String): String?
}