package com.heungjun.popuplogintoken.api

import com.heungjun.popuplogintoken.model.ApiResponse
import com.heungjun.popuplogintoken.model.Login

// 회사 인증과 관련된 기능을 정의하는 인터페이스
interface AuthRepositoryCompany {
    // 회사 로그인 요청을 보내는 함수
    suspend fun login(login: Login): ApiResponse

    // 액세스 토큰을 저장하는 함수
    suspend fun saveAccessToken(token: String)

    // 리프레시 토큰을 저장하는 함수
    suspend fun saveRefreshToken(refreshToken: String)

    // 저장된 액세스 토큰을 가져오는 함수
    suspend fun fetchAccessToken(): String

    // 저장된 리프레시 토큰을 가져오는 함수
    suspend fun fetchRefreshToken(): String

    // 리프레시 토큰을 사용하여 새로운 액세스 토큰을 요청하는 함수
    suspend fun refreshToken(): TokenResponse?

    // 이메일과 비밀번호를 사용하여 로그인하고 액세스 토큰을 반환하는 함수
    suspend fun loginWithCredentials(email: String, password: String): String?
}