package com.heungjun.popuplogintoken.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    @SerialName("data")
    val `data`: Data,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: Boolean,
)

@Serializable
data class Login(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)

@Serializable
data class Data(
    @SerialName("exprTime")
    val exprTime: Int,
    @SerialName("token")
    val token: String
)