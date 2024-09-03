package com.heungjun.popuplogintoken.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckUserEmailRequest(
    @SerialName("email") val email: String  // 서버로 전송할 이메일 주소
)