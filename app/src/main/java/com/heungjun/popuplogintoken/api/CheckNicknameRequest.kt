package com.heungjun.popuplogintoken.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckNicknameRequest(
    @SerialName("nickname") val nickname: String  // 서버로 전송할 닉네임
)