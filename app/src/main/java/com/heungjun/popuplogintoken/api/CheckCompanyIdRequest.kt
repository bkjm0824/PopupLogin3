package com.heungjun.popuplogintoken.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckCompanyIdRequest(
    @SerialName("companyId") val companyId: String  // 요청에 포함될 사업자 번호
)