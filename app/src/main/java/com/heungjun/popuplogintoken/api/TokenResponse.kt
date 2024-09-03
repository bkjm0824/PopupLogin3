package com.heungjun.popuplogintoken.api

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)