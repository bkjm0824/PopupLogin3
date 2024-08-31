package com.heungjun.popuplogintoken.model

import com.heungjun.popuplogintoken.dto.AuthRepositoryCompany

class LoginUseCaseCompany(private val authRepository: AuthRepositoryCompany) {

    suspend fun login(id: String, password: String): Result<Unit> {
        return runCatching {
            val response = authRepository.login(Login(id, password))
            response.data?.let { authRepository.saveAccessToken(it.token) }
            authRepository.saveRefreshToken("refreshTokenFromApi")
        }
    }
}