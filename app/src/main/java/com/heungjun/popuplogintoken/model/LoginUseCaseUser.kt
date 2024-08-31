package com.heungjun.popuplogintoken.model

import com.heungjun.popuplogintoken.dto.AuthRepositoryUser

class LoginUseCaseUser(private val authRepository: AuthRepositoryUser) {

    suspend fun login(id: String, password: String): Result<Unit> {
        return runCatching {
            val response = authRepository.login(Login(id, password))
            response.data?.let { authRepository.saveAccessToken(it.token) }
            authRepository.saveRefreshToken("refreshTokenFromApi")
        }
    }
}