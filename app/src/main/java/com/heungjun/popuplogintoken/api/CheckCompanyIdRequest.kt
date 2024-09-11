package com.heungjun.popuplogintoken.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckCompanyIdRequest(
    @SerialName("companyId") val companyId: String  // 서버로 전송할 사업자 번호
)