package com.heungjun.popuplogintoken.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)