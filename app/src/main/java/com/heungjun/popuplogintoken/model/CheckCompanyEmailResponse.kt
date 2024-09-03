package com.heungjun.popuplogintoken.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckCompanyEmailResponse(
    @SerialName("result") val result: Boolean,  // 요청이 성공했는지를 나타내는 플래그
    @SerialName("message") val message: String,  // 서버에서 제공하는 메시지 (성공 또는 오류 메시지)
    @SerialName("data") val data: String? = null  // JsonElement 타입 사용
)