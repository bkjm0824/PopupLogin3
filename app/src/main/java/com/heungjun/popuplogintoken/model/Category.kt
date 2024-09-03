package com.heungjun.popuplogintoken.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("id") val id: Int,  // 카테고리의 고유 ID
    @SerialName("category") val category: String  // 카테고리 이름
)

@Serializable
data class CategoriesResponse(
    @SerialName("result") val result: Boolean,  // 요청이 성공했는지를 나타내는 플래그
    @SerialName("message") val message: String,  // 서버에서 제공하는 메시지 (성공 또는 오류 메시지)
    @SerialName("data") val data: List<Category>  // 요청에 대한 실제 데이터, 즉 카테고리 목록
)