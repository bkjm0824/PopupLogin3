package com.heungjun.popuplogintoken.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpUser(
    @SerialName("birth")
    val birth: String,
    @SerialName("categories")
    val categories: List<CategoryX>,
    @SerialName("detailAddress")
    val detailAddress: String,
    @SerialName("email")
    val email: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("mapx")
    val mapx: String,
    @SerialName("mapy")
    val mapy: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("password")
    val password: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("postcode")
    val postcode: String,
    @SerialName("username")
    val username: String
)


@Serializable
data class SignUpResponse(
    @SerialName("data")
    val `data`: List<SignUpUser>?,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: Boolean
)