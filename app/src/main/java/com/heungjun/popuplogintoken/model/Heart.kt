package com.heungjun.popuplogintoken.model

import kotlinx.serialization.Serializable

@Serializable
data class HeartResponse(
    val result: Boolean,
    val message: String?,
    val data: HeartData?
)

@Serializable
data class HeartData(
    val id: Int,
    val userId: Int,
    val popupStoreId: Int
)