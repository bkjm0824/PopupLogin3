package com.heungjun.popuplogintoken.dto

import com.heungjun.popuplogintoken.model.Login

class LoginUseCase(private val authRepository: AuthRepository) {

    suspend fun login(id: String, password: String): Result<Unit> {
        return runCatching {
            val response = authRepository.login(Login(id, password))
            response.data?.let { authRepository.saveAccessToken(it.token) }
            authRepository.saveRefreshToken("refreshTokenFromApi")
        }
    }
}